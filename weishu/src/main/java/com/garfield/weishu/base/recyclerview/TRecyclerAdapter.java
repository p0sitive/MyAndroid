package com.garfield.weishu.base.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garfield.weishu.session.sessionlist.SessionListAdapter;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by gaowei3 on 2016/10/27.
 */

public abstract class TRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private final Context mContext;
    private final List<T> mItems;
    private final Map<Class<?>, Integer> mViewTypes;
    private final LayoutInflater mInflater;

    private int HEAD_TYPE = 1000;
    private View mHeadView;
    private ItemEventListener mItemEventListener;

    public TRecyclerAdapter(Context context, List<T> items) {
        mContext = context;
        mItems = items;
        mViewTypes = new HashMap<>();
        mInflater = LayoutInflater.from(context);
    }

    public void setHeadView(View headView) {
        mHeadView = headView;
    }

    public View getHeadView() {
        return mHeadView;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 第二步，根据type，创建Holder
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_TYPE) {
            return new RecyclerViewHolder(mHeadView);
        }
        TRecyclerViewHolder viewHolder = null;
        Class viewHolderClass = getClassByType(viewType);
        try {
            viewHolder = (TRecyclerViewHolder) viewHolderClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RecyclerViewHolder(mInflater, parent, this, viewHolder);
    }

    private Class getClassByType(int viewType) {
        Set<Class<?>> set = mViewTypes.keySet();
        for (Class c : set) {
            if (mViewTypes.get(c) == viewType) {
                return c;
            }
        }
        return null;
    }

    /**
     * 第三步，绑定数据
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (mHeadView != null) {
            if (position == 0) {
                return;
            } else {
                position--;
            }
        }
        holder.getTViewHolder().refresh(position);
    }

    public List<T> getItems() {
        return mItems;
    }

    /**
     * 小心
     */
    @Override
    public int getItemCount() {
        return mHeadView == null ? mItems.size() : mItems.size() + 1;
    }

    /**
     * 第一步，根据position确定type
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeadView != null) {
            if (position == 0) {
                return HEAD_TYPE;
            } else {
                position--;
            }
        }
        Class<?> clazz = getViewHolderClassAtPosition(position);
        if (mViewTypes.containsKey(clazz)) {
            return mViewTypes.get(clazz);
        } else {
            int next = mViewTypes.size();
            mViewTypes.put(clazz, next);
            return next;
        }
    }

    public abstract Class getViewHolderClassAtPosition(int position);


    public void setItemEventListener(ItemEventListener eventListener) {
        this.mItemEventListener = eventListener;
    }

    public ItemEventListener getItemEventListener() {
        return mItemEventListener;
    }

    public interface ItemEventListener<T> {
        void onItemClick(T item);
        void onItemLongPressed(T item);
    }
}
