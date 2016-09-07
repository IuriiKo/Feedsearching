package com.example.xaocu.feedsearching.net.wrappers;

import com.example.xaocu.feedsearching.R;
import com.example.xaocu.feedsearching.SearchApp;
import com.example.xaocu.feedsearching.net.QuotemediaService;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 07.09.2016.
 */
public class QuotemediaServiceWrapper extends BaseServiceWrapper<QuotemediaService> {

  public static QuotemediaService createService(OkHttpClient client) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(SearchApp.getContext().getResources().getString(R.string.url_quotemedia))
        .client(client)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return retrofit.create(QuotemediaService.class);
  }

  public QuotemediaServiceWrapper(QuotemediaService service) {
    super(service);
  }

  @Override
  public Observable<ResponseBody> getData(String constraint) {
    return service.getData(constraint);
  }
}
