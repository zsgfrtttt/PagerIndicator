package com.indicator.library;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

public class PagerTitleView extends AppCompatTextView {

    @ColorInt
    private int selectColor;
    @ColorInt
    private int unSelectColor;

    private int mVerticalPadding;
    private int mHorizontalPadding;

    public PagerTitleView(Context context) {
        this(context, null);
    }

    public PagerTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mVerticalPadding = dp2px(6);
        mHorizontalPadding = dp2px(8);
        setIncludeFontPadding(false);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
        setPadding(mHorizontalPadding, mHorizontalPadding, mHorizontalPadding, mVerticalPadding);
    }

    private int dp2px(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5);
    }

    public void setSelectColorRes(int selectColorRes) {
        this.selectColor = ResourcesCompat.getColor(getResources(), selectColorRes, null);
    }

    public void setUnSelectColorRes(int unSelectColorRes) {
        this.unSelectColor = ResourcesCompat.getColor(getResources(), unSelectColorRes, null);
    }

    @Override
    public void setTextSize(int unit, float size) {
        if (size != 0) {
            super.setTextSize(unit, size);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        setTextColor(selected ? selectColor : unSelectColor);
    }

    public void setVerticalAndHorizontalPadding(int verticalPadding, int horizontalPadding) {
        if (verticalPadding == 0) {
            mVerticalPadding = dp2px(6);
        } else {
            mVerticalPadding = verticalPadding;
        }
        if (horizontalPadding == 0) {
            mHorizontalPadding = dp2px(8);
        } else {
            mHorizontalPadding = horizontalPadding;
        }
        setPadding(mHorizontalPadding, mVerticalPadding, mHorizontalPadding, mVerticalPadding);
    }

}
