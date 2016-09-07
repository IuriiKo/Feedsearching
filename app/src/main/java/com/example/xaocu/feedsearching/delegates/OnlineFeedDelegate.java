package com.example.xaocu.feedsearching.delegates;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xaocu.feedsearching.adapters.items.BaseItem;
import com.example.xaocu.feedsearching.R;
import com.example.xaocu.feedsearching.adapters.items.OnlineFeedItem;
import com.example.xaocu.feedsearching.model.OnlineFeed;

import java.util.List;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public class OnlineFeedDelegate extends BaseDelegate {


  public OnlineFeedDelegate(OnDelegateClickListener listener) {
    super(listener);
  }

  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent) {
    return new SmallItemViewHolder(getLayoutInflater(parent).inflate(R.layout.small_item_view, parent, false));
  }

  @Override
  public void onBindViewHolder(Object object, RecyclerView.ViewHolder holder, int position, List<? extends BaseItem> items) {
    SmallItemViewHolder vh = (SmallItemViewHolder) holder;
    OnlineFeed feed = ((OnlineFeed)((OnlineFeedItem)items.get(position)).getFeeds());
    vh.tvOpen.setText(feed.getOpen());
    vh.tvHigh.setText(feed.getHigh());
    vh.tvLow.setText(feed.getLow());
    vh.tvClose.setText(feed.getClose());
  }

  public class SmallItemViewHolder extends BaseViewHolder{
    TextView tvOpen;
    TextView tvHigh;
    TextView tvLow;
    TextView tvClose;
    public SmallItemViewHolder(View itemView) {
      super(itemView);
      tvOpen = (TextView) itemView.findViewById(R.id.tvOpen);
      tvHigh = (TextView) itemView.findViewById(R.id.tvHigh);
      tvLow = (TextView) itemView.findViewById(R.id.tvLow);
      tvClose = (TextView) itemView.findViewById(R.id.tvClose);
      itemView.setOnClickListener(view -> onClick(this, view, getAdapterPosition(), OnDelegateClickListener.ClickType.SIMPLE_CLICK));
    }
  }
}
