package com.flipkart.android.proteus.support.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.support.view.custom.OtpEntryEditText;

/**
 * Created by Prasad Rao on 30-04-2020 15:42
 **/
public class ProteusOtpView extends OtpEntryEditText implements ProteusView {

    private Manager manager;

    public ProteusOtpView(Context context) {
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
