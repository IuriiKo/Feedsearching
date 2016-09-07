package com.example.xaocu.feedsearching.net;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public interface QuandlService{
  @GET("api/v3/datasets/WIKI/{name}.csv?api_key=mi2CL7WxcRmtvaYdURCx")
  Observable<ResponseBody> getData(@Path("name") String name) ;
}
