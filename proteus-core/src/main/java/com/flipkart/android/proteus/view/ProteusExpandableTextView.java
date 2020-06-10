package com.flipkart.android.proteus.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.toolbox.Attributes;

/**
 * Created by Prasad Rao on 10-06-2020 20:00
 **/
public class ProteusExpandableTextView extends TextView implements ProteusView {

    private Manager manager;

    private int collapsibleMaxLines = 5;

    public ProteusExpandableTextView(Context context) {
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

    public int getCollapsibleMaxLines() {
        return collapsibleMaxLines;
    }

    public void setCollapsibleMaxLines(int collapsibleMaxLines) {
        this.collapsibleMaxLines = collapsibleMaxLines;
    }

    public void expand() {
        ObjectAnimator animation = ObjectAnimator.ofInt(this, Attributes.TextView.MaxLines, 100);
        animation.setDuration(200).start();
    }

    public void collapse() {
        ObjectAnimator animation =
            ObjectAnimator.ofInt(this, Attributes.TextView.MaxLines, collapsibleMaxLines);
        animation.setDuration(200).start();
    }
}
