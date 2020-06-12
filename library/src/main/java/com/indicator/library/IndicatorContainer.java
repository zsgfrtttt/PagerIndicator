package com.indicator.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class IndicatorContainer extends FrameLayout {

    private IndicatorAdapter mAdapter;
    private HorizontalScrollView mScrollView;
    private LinearLayout mIndicatorLayout;
    private LinearLayout mTitleLayout;
    private ViewPager mViewPager;
    private List<PagerTitleView> mTitleViews = new ArrayList<>();

    private int mCurrentIndex = 0;
    private IndicatorScrollHelper mHpler;


    private int mVerticalPadding;
    private int mHorizontalPadding;
    private int mIndicatorColor;
    private int mTextSize;

    private int mViewPagerState = ViewPager.SCROLL_STATE_DRAGGING;
    private boolean mIsSelecting ;

    public IndicatorContainer(@NonNull Context context) {
        this(context, null);
    }

    public IndicatorContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.pager_indicator);
        mVerticalPadding = typedArray.getDimensionPixelSize(typedArray.getIndex(R.styleable.pager_indicator_verticalPadding), 0);
        mHorizontalPadding = typedArray.getDimensionPixelSize(typedArray.getIndex(R.styleable.pager_indicator_horizontalPadding), 0);
        mTextSize = typedArray.getDimensionPixelOffset(typedArray.getIndex(R.styleable.pager_indicator_android_textSize), 0);
        mIndicatorColor = typedArray.getColor(typedArray.getIndex(R.styleable.pager_indicator_indicatorColor), Color.parseColor("#666666"));
        typedArray.recycle();
    }

    private void init() {
        mTitleViews.clear();
        removeAllViews();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pager_indicator_layout, this);
        mScrollView = view.findViewById(R.id.hori);
        mIndicatorLayout = view.findViewById(R.id.indicator_layout);
        mTitleLayout = view.findViewById(R.id.title_layout);
    }

    public void setAdapter(IndicatorAdapter adapter) {
        this.mAdapter = adapter;
        init();
        if (mAdapter != null) {
            List<String> title = mAdapter.getTitle();
            if (!title.isEmpty()) {
                mViewPager.setOffscreenPageLimit(title.size());
            }
            for (int i = 0; i < title.size(); i++) {
                final int index = i;
                PagerTitleView view = new PagerTitleView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setText(title.get(i));
                view.setTextSize(10);
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
                view.setSelectColorRes(mAdapter.getSelectColorRes());
                view.setUnSelectColorRes(mAdapter.getUnSelectColorRes());
                view.setVerticalAndHorizontalPadding(mVerticalPadding, mHorizontalPadding);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mIsSelecting = true;
                        mViewPager.setCurrentItem(index);
                    }
                });
                mTitleLayout.addView(view, params);
                mTitleViews.add(view);
            }

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mAdapter != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            PagerIndicator indicator = new PagerIndicator(getContext());
            indicator.setColor(mIndicatorColor);
            mIndicatorLayout.addView(indicator, params);
            mHpler = new IndicatorScrollHelper(indicator, this);
            mHpler.onPageSelected(mCurrentIndex);
        }
    }

    public IndicatorContainer bind(ViewPager vp) {
        this.mViewPager = vp;
        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mViewPagerState == ViewPager.SCROLL_STATE_DRAGGING){
                    mHpler.onPageScrolled(position, positionOffset);
                }else if (mViewPagerState == ViewPager.SCROLL_STATE_SETTLING && !mIsSelecting){
                    mHpler.onPageScrolled(position, positionOffset);
                }
            }

            //只有setCurrentIndex才会触发
            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                mHpler.onPageSelected(position);
                if (mViewPagerState == ViewPager.SCROLL_STATE_SETTLING && mIsSelecting) {
                    mHpler.onPageScrolled(position, 0);
                }
            }

            //最先被回调
            @Override
            public void onPageScrollStateChanged(int state) {
                mViewPagerState = state;
                if (state == ViewPager.SCROLL_STATE_IDLE){
                    mIsSelecting = false;
                }
            }
        });
        return this;
    }

    public int getTitleLeft() {
        return mTitleViews.get(mCurrentIndex).getLeft();
    }

    public int getTitleRight() {
        return mTitleViews.get(mCurrentIndex).getRight();
    }

    public int getSelectPosition() {
        return mCurrentIndex;
    }

    public int getNextTitleLeft(boolean leftToRight) {
        if (leftToRight) {
            return mTitleViews.get(Math.min(mCurrentIndex + 1, mTitleViews.size() - 1)).getLeft();
        } else {
            return mTitleViews.get(Math.max(0, mCurrentIndex - 1)).getLeft();
        }
    }

    public int getNextTitleRight(boolean leftToRight) {
        if (leftToRight) {
            return mTitleViews.get(Math.min(mCurrentIndex + 1, mTitleViews.size() - 1)).getRight();
        } else {
            return mTitleViews.get(Math.max(0, mCurrentIndex - 1)).getRight();
        }
    }

    public int getTitleBottom() {
        return mTitleViews.get(mCurrentIndex).getBottom();
    }

    public int getTitleTop() {
        return mTitleViews.get(mCurrentIndex).getTop();
    }

    public List<PagerTitleView> getTitleViews() {
        return mTitleViews;
    }

    public int getScrollRange() {
        return mTitleLayout.getWidth() - mScrollView.getWidth();
    }

    public HorizontalScrollView getScrollView() {
        return mScrollView;
    }
}
