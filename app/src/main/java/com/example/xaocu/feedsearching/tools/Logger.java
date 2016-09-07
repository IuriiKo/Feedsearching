package com.example.xaocu.feedsearching.tools;

import android.util.Log;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class Logger {
  public static String createTag(Class c) {
    return c.getSimpleName();
  }

  public static void d(String tag, String msg) {
    Log.d(tag, msg);
  }
  public static void e(String tag, String msg) {
    Log.e(tag, msg);
  }
}
