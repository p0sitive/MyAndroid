package com.garfiled.weixin2.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garfield.baselib.base.BaseFragment;
import com.garfiled.weixin2.R;

/**
 * Created by gwball on 2016/8/2.
 */
public class SettingFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
    }

}
