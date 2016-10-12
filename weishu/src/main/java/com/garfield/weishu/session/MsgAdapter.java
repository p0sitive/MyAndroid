package com.garfield.weishu.session;

import android.content.Context;

import com.garfield.weishu.base.adapter.TAdapter;
import com.garfield.weishu.base.adapter.TAdapterDelegate;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gwball on 2016/9/28.
 */

public class MsgAdapter extends TAdapter {

    private String messageId;
    private Set<String> timedItems; // 需要显示消息时间的消息ID
    private IMMessage lastShowTimeItem; // 用于消息时间显示,判断和上条消息间的时间间隔

    public MsgAdapter(Context context, List items, TAdapterDelegate delegate) {
        super(context, items, delegate);
        timedItems = new HashSet<>();
    }

    public boolean needShowTime(IMMessage message) {
        return timedItems.contains(message.getUuid());
    }


    public void setUuid(String messageId) {
        this.messageId = messageId;
    }

    public String getUuid() {
        return messageId;
    }
}
