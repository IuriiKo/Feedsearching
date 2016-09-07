package com.example.xaocu.feedsearching.adapters.items;

import com.example.xaocu.feedsearching.delegates.DelegateType;
import com.example.xaocu.feedsearching.model.BaseFeed;

import java.io.Serializable;

/**
 * Created by Iurii Kushyk on 04.09.2016.
 */
public class OnlineFeedItem extends BaseItem implements Serializable {
  BaseFeed feeds;

  public OnlineFeedItem(BaseFeed feeds) {
    super(DelegateType.onlineFeedItemType);
    this.feeds = feeds;
  }

  public BaseFeed getFeeds() {
    return feeds;
  }
}
