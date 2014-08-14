package com.paging.listview.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.paging.listview.R;

public class DefaultLoadingView extends LinearLayout {

	public DefaultLoadingView(Context context) {
		super(context);
		init();
	}

	public DefaultLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.loading_view, this);
	}


}
