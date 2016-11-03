package com.garfield.weishu.news.head;

import android.content.Context;
import android.view.ViewGroup;

import com.garfield.weishu.base.viewpager.TPagerAdapter;

import java.util.List;

/**
 * Created by gaowei3 on 2016/10/31.
 */

public class InfinitePagerAdapter extends TPagerAdapter<String> {

    public InfinitePagerAdapter(Context context, List<String> items) {
        super(context, items);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        getViewPager().setCurrentItem(getItems().size() * 100, false);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public Class getViewHolderClassAtPosition(int position) {
        return NewsViewHolder.class;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = getRealPosition(position);
        return super.instantiateItem(container, realPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition = getRealPosition(position);
        super.destroyItem(container, realPosition, object);
    }

    public int getRealPosition(int position) {
        return position % getItems().size();
    }

}