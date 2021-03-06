package com.garfield.weishu.base.viewpager;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by gaowei3 on 2016/10/31.
 */

public abstract class TPagerViewHolder<T> {
    protected View mRootView;
    protected TPagerAdapter mAdapter;
    protected int mPosition;

    /**
     * 入口
     */
    View bindViews(LayoutInflater inflater, TPagerAdapter adapter) {
        mRootView = inflater.inflate(getResId(), null);
        mAdapter = adapter;
        inflateView();
        setView();
        setEventListener();
        return mRootView;
    }

    private void setEventListener() {
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getItemEventListener() != null) {
                    mAdapter.getItemEventListener().onItemClick(mAdapter.getItems().get(mPosition), mPosition);
                }
            }
        });
        mRootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mAdapter.getItemEventListener() != null) {
                    mAdapter.getItemEventListener().onItemLongPressed(mAdapter.getItems().get(mPosition), mPosition);
                }
                return false;
            }
        });
    }

    void refresh(T item, int position) {
        mPosition = position;
        refresh(item);
    }

    protected abstract int getResId();

    protected abstract void inflateView();

    protected abstract void setView();

    protected abstract void refresh(T item);

    protected abstract TPagerAdapter getAdapter();

    protected <M extends View> M findView(int resId) {
        return (M) (mRootView.findViewById(resId));
    }




}
