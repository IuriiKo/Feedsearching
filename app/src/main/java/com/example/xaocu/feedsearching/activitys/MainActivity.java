package com.example.xaocu.feedsearching.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.xaocu.feedsearching.R;
import com.example.xaocu.feedsearching.SearchApp;
import com.example.xaocu.feedsearching.adapters.BaseAdapter;
import com.example.xaocu.feedsearching.adapters.SuggestAdapter;
import com.example.xaocu.feedsearching.adapters.items.OnlineFeedItem;
import com.example.xaocu.feedsearching.delegates.BaseDelegate;
import com.example.xaocu.feedsearching.delegates.OnDelegateClickListener;
import com.example.xaocu.feedsearching.model.BaseFeed;
import com.example.xaocu.feedsearching.model.RawFeed;
import com.example.xaocu.feedsearching.net.ServiceType;
import com.example.xaocu.feedsearching.tools.CSVFile;
import com.example.xaocu.feedsearching.tools.Cash;
import com.example.xaocu.feedsearching.tools.Logger;
import com.example.xaocu.feedsearching.tools.NetUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements OnDelegateClickListener{
  private static final String LOG_TAG = Logger.createTag(MainActivity.class);
  public static final String CASH_KEY = "cash";
  public static final int MIN_SEARCH_LENGTH = 4;
  public static final String TEXT_KEY = "text";
  public static final String SERVICE_TYPE_KEY = "serviceType";

  private RecyclerView rvView;
  private AutoCompleteTextView etSearch;
  private View progressLayout;
  private Spinner spinnerView;
  private BaseAdapter<OnlineFeedItem> adapter;
  private SuggestAdapter suggestAdapter;
  private Map<Integer, Integer> serviceDependence;
  private Cash cash = new Cash();
  private CompositeSubscription compositeSubscription = new CompositeSubscription();
  private Subscription subscriptionGetCVCode;
  private int cashServiceType;
  private String oldConstraint;
  private Subscription subscription;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViews();
    initServiceDependence();
    initSuggestView();
    initRecyclerView();
    initSpinner();
    if (savedInstanceState != null) {
      final int serviceType = savedInstanceState.getInt(SERVICE_TYPE_KEY);
      final String text = savedInstanceState.getString(TEXT_KEY);
      onRestoreState(savedInstanceState);
      spinnerView.setSelection(getSelectPosition(serviceType));
      etSearch.setText(text);
      search(text);
    } else {
      findCsvCode();
    }
  }

  private void findViews() {
    rvView = (RecyclerView) findViewById(R.id.rvView);
    etSearch = (AutoCompleteTextView) findViewById(R.id.etSearch);
    progressLayout = findViewById(R.id.progressLayout);
    spinnerView = (Spinner) findViewById(R.id.spinnerView);
    findViewById(R.id.btnSearch).setOnClickListener(view -> {
      String text = etSearch.getText().toString();
      if (text.length() < MIN_SEARCH_LENGTH) {
        return;
      }
      search(text);
    }
    );
  }

  private void initSpinner() {
    String[] data = getResources().getStringArray(R.array.feeds);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    spinnerView.setAdapter(adapter);
    spinnerView.setSelection(0);
    spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view,
                                 int position, long id) {
        SearchApp.getManager().setServiceType(serviceDependence.get(position));
      }
      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
      }
    });
  }

  private void initRecyclerView() {
    rvView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new BaseAdapter<>(this);
    rvView.setAdapter(adapter);
  }

  private void initServiceDependence() {
    serviceDependence = new HashMap<>();
    serviceDependence.put(0, ServiceType.QUANDL);
    serviceDependence.put(1, ServiceType.QUOTEMEDIA);
    serviceDependence.put(2, ServiceType.GOOGLE);
    serviceDependence.put(3, ServiceType.YAHOO);
  }

  private void initSuggestView() {
    etSearch.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final String abbr = ((RawFeed) suggestAdapter.getItem(i)).getAbbreviation();
        search(abbr);
        etSearch.setText(abbr);
      }
    });
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(CASH_KEY, cash);
    outState.putString(TEXT_KEY, etSearch.getText().toString());
    outState.putInt(SERVICE_TYPE_KEY, SearchApp.getManager().getServiceType());
  }

  private void search(@NonNull String text) {
    hideKeyboard();
    if (!NetUtils.isConnected()) {
      final String connectionLost = getString(R.string.connection_lost);
      showToast(connectionLost);
      setTitle(connectionLost);
      return;
    }
    setTitle("");
    showProgress();
    adapter.clear();
    getData(text);
  }

  public void getData(String datasetCode) {
    if (datasetCode == null ||
        (datasetCode.equalsIgnoreCase(oldConstraint) && cashServiceType == SearchApp.getManager().getServiceType()) ||
        datasetCode.isEmpty()) {
      return;
    }
    if (subscription != null) {
      subscription.unsubscribe();
    }
    oldConstraint = datasetCode;
    cashServiceType = SearchApp.getManager().getServiceType();
    List<OnlineFeedItem> listFromCash = cash.getFromCash(oldConstraint, cashServiceType);
    if (listFromCash != null) {
      onSuccessGetData(listFromCash);
      return;
    }
    subscription = SearchApp.getManager().getData(datasetCode)
        .map(responseBody -> CSVFile.read(responseBody.byteStream(), cashServiceType))
        .flatMap(Observable::from)
        .map(OnlineFeedItem::new)
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onSuccessGetData, this::onErrorGetData);
    addSubscription(subscription);
  }

  private void onSuccessGetData(List<OnlineFeedItem> data) {
    List<OnlineFeedItem> listFromCash = cash.getFromCash(oldConstraint, SearchApp.getManager().getServiceType());
    if (listFromCash == null) {
      cash.addToCash(oldConstraint, data, cashServiceType);
    }
    successDataLoading(data);
  }

  private void onErrorGetData(Throwable t) {
    Logger.e(LOG_TAG, t.getMessage());
    showContent();
    showToast(getString(R.string.cant_connect_to_server));
  }


  public void findCsvCode() {
    if (subscriptionGetCVCode != null) {
      subscriptionGetCVCode.unsubscribe();
    }
    subscriptionGetCVCode = Observable.defer(() -> Observable.just(CSVFile.readRaw()))
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onSuccessFindCvCode, this::onErrorFindCvCode);
    addSubscription(subscriptionGetCVCode);
  }

  public void onSuccessFindCvCode(List<BaseFeed> csvList) {
    suggestAdapter = new SuggestAdapter();
    suggestAdapter.setTickers(csvList);
    etSearch.setAdapter(suggestAdapter);
    showContent();
  }

  private void onErrorFindCvCode(Throwable t) {
    Logger.e(LOG_TAG, t.getMessage());
  }

  public void onRestoreState(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      cash = (Cash) savedInstanceState.getSerializable(CASH_KEY);
    }
  }

  public void successDataLoading(List<OnlineFeedItem> data) {
    adapter.clear();
    adapter.notifyDataSetChanged();
    adapter.addAll(data);
    adapter.notifyDataSetChanged();
    showToast(getString(R.string.connection_success));
    showContent();
  }

  private void showProgress() {
    progressLayout.setVisibility(View.VISIBLE);
    rvView.setVisibility(View.GONE);
  }

  private void hideKeyboard() {
    View view = this.getCurrentFocus();
    if (view != null) {
      InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }
  private void showContent() {
    progressLayout.setVisibility(View.GONE);
    rvView.setVisibility(View.VISIBLE);
  }

  public void onClick(BaseDelegate.BaseViewHolder holder, View view, int position, @OnDelegateClickListener.ClickType int clickType) {
  }

  private void showToast(@NonNull String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  public void addSubscription(Subscription subscription) {
    compositeSubscription.add(subscription);
  }

  private int getSelectPosition(int serviceType) {
    for (int i = 0; i < serviceDependence.size(); i++) {
      if (serviceDependence.get(i) == serviceType) {
        return i;
      }
    }
    return 0;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    compositeSubscription.unsubscribe();
  }
}
