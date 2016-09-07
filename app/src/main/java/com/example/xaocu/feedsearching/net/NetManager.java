package com.example.xaocu.feedsearching.net;

import android.support.annotation.NonNull;

import com.example.xaocu.feedsearching.tools.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class NetManager {
  private static final String LOG_TAG = Logger.createTag(NetManager.class);
  private final OkHttpClient client;
  private final ServiceFactory serviceFactory;
  private @ServiceType int serviceType = ServiceType.QUANDL;

  public NetManager() {
    client = new OkHttpClient.Builder()
        .addInterceptor(new NetInterceptor())
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS).build();
    serviceFactory = new ServiceFactory(client);
  }

  public Observable<ResponseBody> getData(@NonNull String name) {
    return serviceFactory.getServiceWrapper(serviceType).getData(name);
  }

  @ServiceType
  public int getServiceType() {
    return serviceType;
  }

  public void setServiceType(int serviceType) {
    this.serviceType = serviceType;
  }

  private class NetInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      Logger.d(LOG_TAG,request.url().toString());
      return chain.proceed(request);
    }
  }
}
