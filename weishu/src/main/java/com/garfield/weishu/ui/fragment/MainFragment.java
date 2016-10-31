package com.garfield.weishu.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.garfield.baselib.fragmentation.SupportFragment;
import com.garfield.baselib.fragmentation.anim.DefaultHorizontalAnimator;
import com.garfield.baselib.fragmentation.anim.FragmentAnimator;
import com.garfield.baselib.ui.widget.BottomBar;
import com.garfield.weishu.R;
import com.garfield.weishu.contact.ContactFragment;
import com.garfield.weishu.news.NewsFragment;
import com.garfield.weishu.nim.NimConfig;
import com.garfield.weishu.session.sessionlist.SessionListFragment;
import com.garfield.weishu.setting.SettingFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by gaowei3 on 2016/7/31.
 */

/**
 * 不把BottomBar放在Activity中的原因是，要启动一个覆盖BottomBar的MsgFragment，否则无法覆盖
 * MainFragment包含1个BottomBar和3个Tab页Fragment
 */
public class MainFragment extends AppBaseFragment implements BottomBar.OnTabSelectedListener {

    private SupportFragment[] mFragments = new SupportFragment[4];
    private int mCurrentPosition = 0;
    private BottomBar mBottomBar;

    @Override
    protected int onGetFragmentLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void onInitViewAndData(View rootView, Bundle savedInstanceState) {
        if (mToolbarControl != null) {
            mToolbarControl.setVisibility(View.VISIBLE);
        }
        if (savedInstanceState == null) {
            mFragments[0] = new SessionListFragment();
            mFragments[1] = new ContactFragment();
            mFragments[2] = new NewsFragment();
            mFragments[3] = new SettingFragment();
            loadMultiRootFragment(R.id.main_fragment_container, 0, mFragments[0], mFragments[1], mFragments[2], mFragments[3]);
        } else {
            mFragments[0] = findFragment(SessionListFragment.class);
            mFragments[1] = findFragment(ContactFragment.class);
            mFragments[2] = findFragment(NewsFragment.class);
            mFragments[3] = findFragment(SettingFragment.class);
        }

        mBottomBar = (BottomBar) rootView.findViewById(R.id.bottomBar);
        mBottomBar.setColor(R.color.bottombar_item_unselect, R.color.colorPrimary)
                .addItem(R.drawable.ic_message_white, "消息")
                .addItem(R.drawable.ic_contact_white, "联系人")
                .addItem(R.drawable.ic_news_white, "新闻")
                .addItem(R.drawable.ic_settings_white, "设置");
        mBottomBar.setOnTabSelectedListener(this);
    }

    public void switchToFirst() {
        if (mCurrentPosition == 0) return;
        updateNotification(0);
        showHideFragment(mFragments[0], mFragments[mCurrentPosition]);
        mCurrentPosition = 0;
        mBottomBar.setTabSelected(0);
    }

    @Override
    public void onTabSelected(int position, int prePosition) {
        updateNotification(position);
        showHideFragment(mFragments[position], mFragments[prePosition]);
        mCurrentPosition = position;
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void updateNotification(int position) {
        if (position == 0) {
            NimConfig.nofityWithNoTopBar();
        } else {
            NimConfig.nofityWithTopBar();
        }
    }

    public int getTabPosition() {
        return mCurrentPosition;
    }

    /**
     *  如果是根元素就不去动画，在loadRootFragment时没有设置setTransition
     */
    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);
//        Class topClass = getTopFragment().getClass();
//        if (topClass == SessionFragment.class) {
//            if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN && !enter && animation.getDuration() > 100) {
//                animation.setStartOffset(300);
//            }
//        }
//        return animation;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
