package com.flipkart.android.proteus.view.custom;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.flipkart.android.proteus.ProteusView;

public class ProteusProgressIndicatorView extends ProgressIndicatorView implements ProteusView {

    private Manager manager;

    public ProteusProgressIndicatorView(Context context) {
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
