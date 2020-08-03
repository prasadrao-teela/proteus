package com.flipkart.android.proteus.view;

import android.content.Context;
import android.view.View;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.ProteusView;

/**
 * Created by Prasad Rao on 06-05-2020 11:42
 **/
public class ProteusVideoView extends VideoView implements ProteusView {

    private Manager manager;
    private String filePath;

    public ProteusVideoView(Context context) {
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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Nullable
    public String getFilePath() {
        return filePath;
    }
}
