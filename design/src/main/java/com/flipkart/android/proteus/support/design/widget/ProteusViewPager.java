package com.flipkart.android.proteus.support.design.widget;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.flipkart.android.proteus.ProteusView;

/**
 * Created by Prasad Rao on 28-02-2020 18:18
 **/
public class ProteusViewPager extends ViewPager implements ProteusView {

    private Manager manager;

    public ProteusViewPager(@NonNull Context context) {
        super(context);
    }

    @Override
    public Manager getViewManager() {
        return manager;
    }

    @Override
    public void setViewManager(@NonNull Manager manager) {
        this.manager = manager;
    }

    @NonNull
    @Override
    public View getAsView() {
        return this;
    }

    public void moveToNextPage() {
        if (getAdapter() != null && getCurrentItem() < getAdapter().getCount() - 1) {
            setCurrentItem(getCurrentItem() + 1);
        }
    }

    public void moveToPreviousPage() {
        if (getAdapter() != null && getCurrentItem() > 0) {
            setCurrentItem(getCurrentItem() - 1);
        }
    }
}
