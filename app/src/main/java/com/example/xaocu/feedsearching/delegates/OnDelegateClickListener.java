package com.example.xaocu.feedsearching.delegates;

import android.support.annotation.IntDef;
import android.view.View;

import com.example.xaocu.feedsearching.delegates.BaseDelegate;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public interface OnDelegateClickListener {
  @IntDef({ClickType.SIMPLE_CLICK, ClickType.LONG_CLICK})
   @interface ClickType {
    int SIMPLE_CLICK = 0;
    int LONG_CLICK = 0;

  }
  void onClick(BaseDelegate.BaseViewHolder holder, View view, int position, @ClickType int clickType);
}
