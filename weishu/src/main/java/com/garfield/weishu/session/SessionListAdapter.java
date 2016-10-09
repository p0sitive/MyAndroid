package com.garfield.weishu.session;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.garfield.weishu.R;
import com.garfield.weishu.base.adapter.OnItemClickListener;
import com.garfield.weishu.base.adapter.OnItemLongClickListener;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gwball on 2016/8/3.
 */
public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.MyViewHolder> {
    private Context mContext;
    private List<RecentContact> mData = new ArrayList<>();
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;

    public SessionListAdapter(Context context, List<RecentContact> data) {
        mContext = context;
        mData = data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public final TextView mNameTextView;
        public final TextView mContentTextView;
        public String mAccount;

        public MyViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.item_msglist_head);
            mNameTextView = (TextView) view.findViewById(R.id.item_msglist_name);
            mContentTextView = (TextView) view.findViewById(R.id.item_msglist_content);
            mAccount = null;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msglist, parent, false);

        view.setBackgroundResource(R.drawable.bg_press_gray);
//        TypedValue typedValue = new TypedValue();
//        mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
//        mView.setBackgroundResource(typedValue.resourceId);

        final MyViewHolder holder = new MyViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder.mAccount);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                if (mLongClickListener != null) {
                    mLongClickListener.onItemLongPressed(position, v);
                }
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mImageView.setImageResource(R.drawable.default_avatar);
        holder.mNameTextView.setText(mData.get(position).getFromAccount());
        holder.mContentTextView.setText(mData.get(position).getContent());
        holder.itemView.setClickable(true);
        holder.mAccount = mData.get(position).getContactId();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<RecentContact> data) {
        mData = data;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public void setOnLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.mLongClickListener = itemLongClickListener;
    }
}