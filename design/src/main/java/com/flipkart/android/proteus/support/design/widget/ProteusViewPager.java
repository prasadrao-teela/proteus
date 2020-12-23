package com.flipkart.android.proteus.support.design.widget;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.flipkart.android.proteus.ProteusView;

import java.util.ArrayList;
/**
 * Created by Prasad Rao on 28-02-2020 18:18
 **/
public class ProteusViewPager extends ViewPager implements ProteusView {

    private Manager manager;
    private ArrayList<OnPageChangeListener> listeners = new ArrayList<>();

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        // Unspecified means that the ViewPager is in a ScrollView WRAP_CONTENT.
        // At Most means that the ViewPager is not in a ScrollView WRAP_CONTENT.
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            // super has to be called in the beginning so the child views can be initialized.
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int height = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int childMeasuredHeight = child.getMeasuredHeight();
                if (childMeasuredHeight > height) {
                    height = childMeasuredHeight;
                }
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        // super has to be called again so the new specs are treated as exact measurements
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        super.addOnPageChangeListener(listener);
        this.listeners.add(listener);
    }

    @Override
    public void removeOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        super.removeOnPageChangeListener(listener);
        this.listeners.remove(listener);
    }

    @Override
    public void setCurrentItem(int item) {
        if (item != getCurrentItem()) {
            super.setCurrentItem(item);
        } else {
            post(() -> dispatchOnPageSelected(item));
        }
    }

    private void dispatchOnPageSelected(int position) {
        for (int i = 0, size = listeners.size(); i < size; i++) {
            OnPageChangeListener listener = listeners.get(i);
            if (listener != null) {
                listener.onPageSelected(position);
            }
        }
    }
}
