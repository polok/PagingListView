package com.paging.listview.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.paging.listview.ui.components.DefaultLoadingView;
import com.paging.listview.interfaces.Pagingable;
import com.paging.listview.adapters.PagingBaseAdapter;
import com.paging.listview.adapters.PagingExpandableAdapter;

import java.util.List;


public class PagingExpandableListView extends ExpandableListView {

	private boolean isLoading;
	private boolean hasMoreItems;
	private Pagingable pagingableListener;
	private DefaultLoadingView loadinView;

    private OnScrollListener onScrollListener;

	public PagingExpandableListView(Context context) {
		super(context);
		init();
	}

	public PagingExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PagingExpandableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public boolean isLoading() {
		return this.isLoading;
	}

	public void setIsLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

	public void setPagingableListener(Pagingable pagingableListener) {
		this.pagingableListener = pagingableListener;
	}

	public void setHasMoreItems(boolean hasMoreItems) {
		this.hasMoreItems = hasMoreItems;
		if(!this.hasMoreItems) {
			removeFooterView(loadinView);
		}
	}

	public boolean hasMoreItems() {
		return this.hasMoreItems;
	}


	public void onFinishLoading(boolean hasMoreItems, List<? extends Object> newItems) {
		setHasMoreItems(hasMoreItems);
		setIsLoading(false);
		if(newItems != null && newItems.size() > 0) {
			ExpandableListAdapter adapter = getExpandableListAdapter();
			if(adapter instanceof PagingExpandableAdapter) {
				((PagingExpandableAdapter)adapter).addMoreItems(newItems);
			}
		}
	}


	private void init() {
		isLoading = false;
		loadinView = new DefaultLoadingView(getContext());
		addFooterView(loadinView);
		super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }

                if (totalItemCount > 0) {
                    int lastVisibleItem = firstVisibleItem + visibleItemCount;
                    if (!isLoading && hasMoreItems && (lastVisibleItem == totalItemCount)) {
                        if (pagingableListener != null) {
                            isLoading = true;
                            pagingableListener.onLoadMoreItems();
                        }

                    }
                }
            }
        });
	}

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        onScrollListener = listener;
    }
}
