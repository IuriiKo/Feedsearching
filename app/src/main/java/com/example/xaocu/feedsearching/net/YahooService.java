package com.example.xaocu.feedsearching.net;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 06.09.2016.
 */
public interface YahooService {
  @GET("table.csv?d=6&e=1&g=d&a=7&b=19&ignore=.csv")
  Observable<ResponseBody> getData(@Query("s") String name) ;
}
