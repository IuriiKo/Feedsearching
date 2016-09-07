package com.example.xaocu.feedsearching.net;

import android.support.annotation.IntDef;

/**
 * Created by Iurii Kushyk on 06.09.2016.
 */
@IntDef({ServiceType.QUANDL, ServiceType.QUOTEMEDIA, ServiceType.YAHOO, ServiceType.GOOGLE, ServiceType.RAW})
public @interface ServiceType{
  int QUANDL = 0;
  int QUOTEMEDIA = 1;
  int YAHOO = 2;
  int GOOGLE = 3;
  int RAW = 4;
}
