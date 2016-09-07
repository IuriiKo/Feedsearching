package com.example.xaocu.feedsearching.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.xaocu.feedsearching.adapters.items.BaseItem;
import com.example.xaocu.feedsearching.delegates.DelegateFactory;
import com.example.xaocu.feedsearching.delegates.OnDelegateClickListener;
import com.example.xaocu.feedsearching.delegates.BaseDelegate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Iurii Kushyk on 03.09.2016.
 */
public class BaseAdapter<T extends BaseItem> extends RecyclerView.Adapter {
  private Object object;
  private List<T> originList = new ArrayList<>();
  private DelegateFactory delegateFactory = new DelegateFactory();
  private OnDelegateClickListener listener;

  public BaseAdapter(OnDelegateClickListener listener) {
    this.listener = listener;
  }

  private BaseDelegate getDelegate(int viewType) {
    return delegateFactory.getDelegate(viewType, listener);
  }

  public void setBindObject(Object object) {
    this.object = object;
  }

  @Override
  public int getItemViewType(int position) {
    return originList.get(position).getItemType();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return getDelegate(viewType).onCreateViewHolder(parent);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    getDelegate(getItemViewType(position)).onBindViewHolder(object, holder, position, originList);
  }

  @Override
  public int getItemCount() {
    return originList.size();
  }

  public void add(T item) {
    originList.add(item);
  }

  public void add(int position, T item) {
    originList.add(position, item);
  }

  public boolean addAll(Collection<T> items) {
    return originList.addAll(items);
  }

  public boolean addAll(int position, Collection<T> items) {
    return originList.addAll(position, items);
  }

  public void remove(T item) {
    originList.remove(item);
  }

  public void remove(int position) {
    originList.remove(position);
  }

  public void removeAll(Collection<T> items) {
    originList.removeAll(items);
  }

  public boolean contains(T item) {
    return originList.contains(item);
  }

  public boolean containsAll(Collection<T> items) {
    return originList.containsAll(items);
  }

  public void clear() {
    originList.clear();
  }

  public T getItem(int position) {
    return originList.get(position);
  }
}
