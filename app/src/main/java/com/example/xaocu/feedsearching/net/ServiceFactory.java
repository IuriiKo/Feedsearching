package com.example.xaocu.feedsearching.net;

import android.support.annotation.NonNull;

import com.example.xaocu.feedsearching.net.wrappers.BaseServiceWrapper;
import com.example.xaocu.feedsearching.net.wrappers.GoogleServiceWrapper;
import com.example.xaocu.feedsearching.net.wrappers.QuandlServiceWrapper;
import com.example.xaocu.feedsearching.net.wrappers.QuotemediaServiceWrapper;
import com.example.xaocu.feedsearching.net.wrappers.YahooServiceWrapper;

import okhttp3.OkHttpClient;

/**
 * Created by Iurii Kushyk on 07.09.2016.
 */
public class ServiceFactory {
  private final OkHttpClient client;

  public ServiceFactory(@NonNull OkHttpClient client) {
    this.client = client;
  }
  private GoogleServiceWrapper googleServiceWrapper;
  private YahooServiceWrapper yahooServiceWrapper;
  private QuotemediaServiceWrapper quotemediaServiceWrapper;
  private QuandlServiceWrapper quandlServiceWrapper;

  public BaseServiceWrapper getServiceWrapper(@ServiceType int serviceType) {
    switch (serviceType) {
      case ServiceType.QUANDL:

        if (quandlServiceWrapper == null) {
          quandlServiceWrapper = new QuandlServiceWrapper(QuandlServiceWrapper.createServise(client));
        }
        return quandlServiceWrapper;
      case ServiceType.QUOTEMEDIA:
        if (quotemediaServiceWrapper == null) {
          quotemediaServiceWrapper = new QuotemediaServiceWrapper(QuotemediaServiceWrapper.createServise(client));
        }
        return quotemediaServiceWrapper;
      case ServiceType.GOOGLE:

        if (googleServiceWrapper == null) {
          googleServiceWrapper = new GoogleServiceWrapper(GoogleServiceWrapper.createServise(client));
        }
        return googleServiceWrapper;
      case ServiceType.YAHOO:
        if (yahooServiceWrapper == null) {
          yahooServiceWrapper = new YahooServiceWrapper(YahooServiceWrapper.createServise(client));
        }
        return yahooServiceWrapper;
      default:
        throw new IllegalArgumentException("Wrong ServiceType = " + serviceType);
    }
  }
}
