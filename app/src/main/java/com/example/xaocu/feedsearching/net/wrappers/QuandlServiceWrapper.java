package com.example.xaocu.feedsearching.net.wrappers;

import com.example.xaocu.feedsearching.net.QuandlService;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 07.09.2016.
 */
public class QuandlServiceWrapper extends BaseServiceWrapper<QuandlService> {
  public static QuandlService createServise(OkHttpClient client) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://www.quandl.com/")
        .client(client)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return retrofit.create(QuandlService.class);
  }
  public QuandlServiceWrapper(QuandlService service) {
    super(service);
  }

  @Override
  public Observable<ResponseBody> getData(String constraint) {
    return service.getData(constraint);
  }
}
