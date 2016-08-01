package com.garfield.baselib.fragmentation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.garfield.baselib.anim.DefaultHorizontalAnimator;
import com.garfield.baselib.anim.FragmentAnimator;

/**
 * Created by gaowei3 on 2016/7/22.
 */
public class SupportFragment extends Fragment {

    protected SupportActivity mActivity;
    private FragmentAnimator mFragmentAnimator;

    private int mContainerId;   // 该Fragment所处的Container的id

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SupportActivity) {
            this.mActivity = (SupportActivity) context;
        } else {
            throw new RuntimeException("Must extends SupportActivity!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentAnimator = onCreateFragmentAnimator();
        if (mFragmentAnimator == null) {
            mFragmentAnimator = mActivity.getFragmentAnimator();
            if (mFragmentAnimator == null) {
                mFragmentAnimator = new DefaultHorizontalAnimator();
            }
        }
        Bundle bundle = getArguments();
        mContainerId = bundle.getInt(FragmentHelper.FRAGMENT_ARG_CONTAINER);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(mActivity, mFragmentAnimator.getEnter());
        } else {
            return AnimationUtils.loadAnimation(mActivity, mFragmentAnimator.getExit());
        }
    }

    protected FragmentAnimator onCreateFragmentAnimator() {
        return null;
    }

    public int getContainerId() {
        return mContainerId;
    }
}
