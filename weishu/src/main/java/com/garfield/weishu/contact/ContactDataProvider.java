package com.garfield.weishu.contact;

import com.garfield.weishu.R;
import com.garfield.weishu.contact.item.AbsContactItem;
import com.garfield.weishu.contact.item.FuncItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaowei3 on 2016/9/8.
 */
public class ContactDataProvider {
    private int[] itemTypes;

    public ContactDataProvider(int... itemTypes) {
        this.itemTypes = itemTypes;
    }

    public List<AbsContactItem> provide() {
        List<AbsContactItem> data = new ArrayList<>();

        for (int itemType : itemTypes) {
            data.addAll(provide(itemType));
        }

        return data;
    }

    private List<AbsContactItem> provide(int itemType) {
        switch (itemType) {
            case ItemTypes.FUNC:

            case ItemTypes.FRIEND:
            case ItemTypes.TEAM:
            case ItemTypes.TEAMS.ADVANCED_TEAM:
            case ItemTypes.TEAMS.NORMAL_TEAM:
            case ItemTypes.MSG:
            default:
                return new ArrayList<>();
        }
    }

    private List<AbsContactItem> generateFuncData() {
        List<AbsContactItem> data = new ArrayList<>();
        data.add(new FuncItem(R.drawable.ic_friend, "朋友"));
        data.add(new FuncItem(R.drawable.ic_friend, "朋友"));
        data.add(new FuncItem(R.drawable.ic_friend, "朋友"));
        data.add(new FuncItem(R.drawable.ic_friend, "朋友"));
        return data;
    }
}
