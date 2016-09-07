package com.example.xaocu.feedsearching.net;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 06.09.2016.
 */
public interface QuotemediaService {
  @GET("quotetools/getHistoryDownload.csv?&webmasterId=501&isRanged=false")
  Observable<ResponseBody> getData(@Query("symbol") String name) ;
}
