package com.example.xaocu.feedsearching.net.wrappers;


import com.example.xaocu.feedsearching.net.YahooService;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 07.09.2016.
 */
public class YahooServiceWrapper extends BaseServiceWrapper<YahooService> {
  public static YahooService createServise(OkHttpClient client) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://ichart.finance.yahoo.com/")
        .client(client)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return retrofit.create(YahooService.class);
  }
  public YahooServiceWrapper(YahooService service) {
    super(service);
  }

  @Override
  public Observable<ResponseBody> getData(String constraint) {
    return service.getData(constraint);
  }
}
