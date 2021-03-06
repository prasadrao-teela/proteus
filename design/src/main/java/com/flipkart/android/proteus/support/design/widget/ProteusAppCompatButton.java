package com.flipkart.android.proteus.support.design.widget;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.flipkart.android.proteus.ProteusView;

/**
 * Created by Prasad Rao on 06-05-2020 22:30
 **/
public class ProteusAppCompatButton extends AppCompatButton implements ProteusView {

    private Manager manager;

    public ProteusAppCompatButton(Context context) {
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
