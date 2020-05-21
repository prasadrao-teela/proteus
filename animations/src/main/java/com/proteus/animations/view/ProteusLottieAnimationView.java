package com.proteus.animations.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.flipkart.android.proteus.ProteusView;

/**
 * Created by Prasad Rao on 08-05-2020 17:43
 **/
public class ProteusLottieAnimationView extends LottieAnimationView implements ProteusView {

    private Manager manager;

    public ProteusLottieAnimationView(Context context) {
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
