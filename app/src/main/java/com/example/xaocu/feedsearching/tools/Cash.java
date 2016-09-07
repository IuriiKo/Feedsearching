package com.example.xaocu.feedsearching.tools;

import android.support.annotation.NonNull;
import android.util.LruCache;

import com.example.xaocu.feedsearching.adapters.items.OnlineFeedItem;
import com.example.xaocu.feedsearching.net.ServiceType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Iurii Kushyk on 07.09.2016.
 */
public class Cash implements Serializable{
  public static final int MAX_CASH_SIZE = 40 * 1024;
  private LruCache<String, List<OnlineFeedItem>> googleCash = new LruCache<>(MAX_CASH_SIZE);
  private LruCache<String, List<OnlineFeedItem>> quandlCash = new LruCache<>(MAX_CASH_SIZE);
  private LruCache<String, List<OnlineFeedItem>> yahooCash = new LruCache<>(MAX_CASH_SIZE);
  private LruCache<String, List<OnlineFeedItem>> qutemediaCash = new LruCache<>(MAX_CASH_SIZE);

  public void addToCash(String name, List<OnlineFeedItem> items, @ServiceType int serviceType) {
    switch (serviceType) {
      case ServiceType.GOOGLE:
        googleCash.put(name, items);
        break;
      case ServiceType.QUANDL:
        quandlCash.put(name, items);
        break;
      case ServiceType.QUOTEMEDIA:
        qutemediaCash.put(name, items);
        break;
      case ServiceType.YAHOO:
        yahooCash.put(name, items);
        break;
    }
  }

  public List<OnlineFeedItem> getFromCash(@NonNull String name, @ServiceType int serviceType) {
    switch (serviceType) {
      case ServiceType.GOOGLE:
        return googleCash.get(name);
      case ServiceType.QUANDL:
        return quandlCash.get(name);
      case ServiceType.QUOTEMEDIA:
        return qutemediaCash.get(name);
      case ServiceType.YAHOO:
        return yahooCash.get(name);
      default:
        throw  new IllegalArgumentException("Wrong serviceType = " + serviceType);
    }
  }
}
