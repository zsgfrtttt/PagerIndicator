package com.indicator.library;

import android.os.Build;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;

import java.util.List;

public class IndicatorScrollHelper {

    private PagerIndicator mPagerIndicator;
    private IndicatorContainer mWrapper;
    private Interpolator mInterpolator = new LinearInterpolator();
    private List<PagerTitleView> mTitleViews;

    public IndicatorScrollHelper(PagerIndicator mPagerIndicator, IndicatorContainer mWrapper) {
        this.mPagerIndicator = mPagerIndicator;
        this.mWrapper = mWrapper;
        mTitleViews = mWrapper.getTitleViews();
    }

    /**
     * position 为左边的item的位置
     *
     * @param position
     * @param positionOffset
     */
    public void onPageScrolled(int position, float positionOffset) {
        int index = mWrapper.getSelectPosition();
        boolean leftToRight = index == position;//从左到右滑动
        int left = mWrapper.getTitleLeft();
        int right = mWrapper.getTitleRight();
        int top = mWrapper.getTitleTop();
        int bottom = mWrapper.getTitleBottom();
        int nextLeft = mWrapper.getNextTitleLeft(leftToRight);
        int nextRight = mWrapper.getNextTitleRight(leftToRight);
        int indicatorLeft;
        int indicatorRight;
        float interpolation = mInterpolator.getInterpolation(positionOffset);
        if (leftToRight) {
            indicatorLeft = (int) (left + interpolation * (nextLeft - left));
            indicatorRight = (int) (right + interpolation * (nextRight - right));
        } else {
            indicatorLeft = (int) (nextLeft + interpolation * (left - nextLeft));
            indicatorRight = (int) (nextRight + interpolation * (right - nextRight));
        }
        handleHorizontalScroll(position, positionOffset);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mPagerIndicator.setLeftTopRightBottom(indicatorLeft, top, indicatorRight, bottom);
        } else {
            mPagerIndicator.setLeft(indicatorLeft);
            mPagerIndicator.setTop(top);
            mPagerIndicator.setRight(indicatorRight);
            mPagerIndicator.setBottom(bottom);
        }
    }

    private void handleHorizontalScroll(int position, float positionOffset) {
        HorizontalScrollView scrollView = mWrapper.getScrollView();
        int range = mWrapper.getScrollRange();
        if (range > 0) {
            int scrollX = (int) ((position + positionOffset) / (mTitleViews.size() - 1) * range);
            scrollX = Math.max(0,Math.min(scrollX,range));
            scrollView.scrollTo(scrollX, 0);
        }
    }

    public void onPageSelected(int position) {
        for (int i = 0; i < mTitleViews.size(); i++) {
            mTitleViews.get(i).setSelected(i == position);
        }
    }
}
