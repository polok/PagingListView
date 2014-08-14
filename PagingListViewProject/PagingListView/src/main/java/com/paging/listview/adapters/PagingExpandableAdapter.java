package com.paging.listview.adapters;

import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class PagingExpandableAdapter<T> extends BaseExpandableListAdapter {

    protected List<T> items;

    public PagingExpandableAdapter() {
        this.items = new ArrayList<T>();
    }

    public PagingExpandableAdapter(List<T> items) {
        this.items = items;
    }

    public void addMoreItems(List<T> newItems) {
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void removeAllItems() {
        this.items.clear();
        notifyDataSetChanged();
    }


}
