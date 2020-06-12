package com.indicator.library;

import java.util.List;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

public abstract class IndicatorAdapter {

    @NonNull
    public abstract List<String> getTitle();

    public @ColorRes
    int getSelectColorRes() {
        return android.R.color.black;
    }

    public @ColorRes
    int getUnSelectColorRes() {
        return android.R.color.darker_gray;
    }
}
