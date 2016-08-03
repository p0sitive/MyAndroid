package com.garfield.baselib.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.garfield.baselib.R;

public class BottomBar extends LinearLayout {
    private static final int TRANSLATE_DURATION_MILLIS = 200;

    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private boolean mVisible = true;
    private boolean mHasShadow = false;
    private int mUnSelectedColor;
    private int mSelectedColor;

    private LinearLayout mItemLayout;

    private LayoutParams mItemParams;
    private int mCurrentPosition = 0;
    private OnTabSelectedListener mListener;

    public BottomBar(Context context) {
        this(context, null);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);

//        if (attrs != null) {
//            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomBar);
//            mHasShadow = ta.getBoolean(R.styleable.BottomBar_hasShadow, false);
//            ta.recycle();
//        }
//
//        if (mHasShadow) {
//            ImageView shadowView = new ImageView(context);
//            shadowView.setBackgroundResource(R.drawable.actionbar_shadow_up);
//            addView(shadowView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        }

        View lineView = new View(context);
        lineView.setBackgroundColor(getResources().getColor(R.color.gray_trans));
        addView(lineView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        mItemLayout = new LinearLayout(context);
        mItemLayout.setBackgroundColor(Color.WHITE);
        mItemLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(mItemLayout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mItemParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        mItemParams.weight = 1;
    }

    public BottomBar setColor(int colorUnSelected, int colorSelected) {
        mUnSelectedColor = colorUnSelected;
        mSelectedColor = colorSelected;
        return this;
    }

    public BottomBar addItem(int resource, String content) {
        final BottomBarTab tab = new BottomBarTab(getContext(), resource, content, mUnSelectedColor, mSelectedColor);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) return;

                int pos = tab.getTabPosition();
                if (mCurrentPosition == pos) {
                    mListener.onTabReselected(pos);
                } else {
                    mListener.onTabSelected(pos, mCurrentPosition);
                    tab.setSelected(true);
                    mListener.onTabUnselected(mCurrentPosition);
                    mItemLayout.getChildAt(mCurrentPosition).setSelected(false);
                    mCurrentPosition = pos;
                }
            }
        });
        tab.setTabPosition(mItemLayout.getChildCount());
        tab.setLayoutParams(mItemParams);
        mItemLayout.addView(tab);
        return this;
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        mListener = onTabSelectedListener;
    }

    public void setCurrentItem(final int position) {
        mItemLayout.post(new Runnable() {
            @Override
            public void run() {
                mItemLayout.getChildAt(position).performClick();
            }
        });
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position, int prePosition);
        void onTabUnselected(int position);
        void onTabReselected(int position);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, mCurrentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        if (mCurrentPosition != ss.position) {
            mItemLayout.getChildAt(mCurrentPosition).setSelected(false);
            mItemLayout.getChildAt(ss.position).setSelected(true);
        }
        mCurrentPosition = ss.position;
    }

    static class SavedState extends BaseSavedState {
        private int position;

        public SavedState(Parcel source) {
            super(source);
            position = source.readInt();
        }

        public SavedState(Parcelable superState, int position) {
            super(superState);
            this.position = position;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(position);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }


    public void hide() {
        hide(true);
    }

    public void show() {
        show(true);
    }

    public void hide(boolean anim) {
        toggle(false, anim, false);
    }

    public void show(boolean anim) {
        toggle(true, anim, false);
    }

    public boolean isVisible() {
        return mVisible;
    }

    private void toggle(final boolean visible, final boolean animate, boolean force) {
        if (mVisible != visible || force) {
            mVisible = visible;
            int height = getHeight();
            if (height == 0 && !force) {
                ViewTreeObserver vto = getViewTreeObserver();
                if (vto.isAlive()) {
                    // view树完成测量并且分配空间而绘制过程还没有开始的时候播放动画。
                    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currentVto = getViewTreeObserver();
                            if (currentVto.isAlive()) {
                                currentVto.removeOnPreDrawListener(this);
                            }
                            toggle(visible, animate, true);
                            return true;
                        }
                    });
                    return;
                }
            }
            int translationY = visible ? 0 : height;
            if (animate) {
                animate().setInterpolator(mInterpolator)
                        .setDuration(TRANSLATE_DURATION_MILLIS)
                        .translationY(translationY);
            } else {
                ViewCompat.setTranslationY(this, translationY);
            }
        }
    }
}