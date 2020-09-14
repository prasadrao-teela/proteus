package com.flipkart.android.proteus.support.design.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.util.InputTypes;

/**
 * Created by Prasad Rao on 27-04-2020 10:57
 **/
public class ProteusAppCompatEditText extends AppCompatEditText implements ProteusView {

    private Manager viewManager;

    public ProteusAppCompatEditText(Context context) {
        super(context);
    }

    @Override
    public Manager getViewManager() {
        return viewManager;
    }

    @Override
    public void setViewManager(@NonNull Manager manager) {
        this.viewManager = manager;
    }

    @NonNull
    @Override
    public View getAsView() {
        return this;
    }

    public void setInputType(String inputType) {
        Typeface typeface = getTypeface();
        setInputType(InputTypes.getInputType(inputType));
        if(getText() != null){
            setSelection(getText().length());
        }
        setTypeface(typeface);
    }
}
