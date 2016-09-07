package com.example.xaocu.feedsearching.net.wrappers;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 07.09.2016.
 */
public abstract class BaseServiceWrapper<T> {
  protected T service;

  public BaseServiceWrapper(T service) {
    this.service = service;
  }

  public T getService() {
    return service;
  }

  public abstract Observable<ResponseBody> getData(String constraint);
}
