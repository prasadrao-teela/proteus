package com.flipkart.android.proteus.support.view.custom;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.flipkart.android.proteus.ProteusView;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

/**
 * Created by Prasad Rao on 06-05-2020 22:06
 **/
public class ProteusSpringDotsIndicator extends SpringDotsIndicator implements ProteusView {

    private Manager manager;

    public ProteusSpringDotsIndicator(Context context) {
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
}
