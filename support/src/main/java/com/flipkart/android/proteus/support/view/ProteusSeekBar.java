package com.flipkart.android.proteus.support.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.flipkart.android.proteus.ProteusView;

/**
 * Created by Prasad Rao on 07-05-2020 22:25
 **/
public class ProteusSeekBar extends AppCompatSeekBar implements ProteusView {

    private int minValue;

    private int stepSize;

    private Manager manager;

    public ProteusSeekBar(Context context) {
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

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getStepSize() {
        return stepSize;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }
}
