package com.flipkart.android.proteus.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.view.custom.ExpandableLayout;

/**
 * Created by Sandeep on 05-06-2020 00:26
 **/
public class ProteusExpandableView extends ExpandableLayout implements ProteusView {

    private Manager manager;

    public ProteusExpandableView(Context context) {
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
