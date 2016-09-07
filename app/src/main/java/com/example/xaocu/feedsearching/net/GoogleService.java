package com.example.xaocu.feedsearching.net;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 06.09.2016.
 */
public interface GoogleService{
  @GET("finance/historical?output=csv")
  Observable<ResponseBody> getData(@Query("q") String name) ;
}
