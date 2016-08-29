package com.garfield.study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.garfield.baselib.swipeback.SwipeBackActivity;

/**
 * Created by gaowei3 on 2016/8/10.
 */
public class LauncherActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_viewdraghelper:
                startActivity(new Intent(this, com.garfield.study.viewdraghelper.activity.MainActivity.class));
                break;
            case R.id.main_touch:
                startActivity(new Intent(this, com.garfield.study.touch.MainActivity.class));
                break;
            case R.id.main_speed_progress:
                startActivity(new Intent(this, com.garfield.study.ui.ContainerActivity.class));
                break;

        }
    }
}
