package com.flipkart.android.proteus.support.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;

import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.toolbox.Attributes;
import com.flipkart.android.proteus.value.ObjectValue;

/**
 * Created by Prasad Rao on 29-04-2020 12:53
 **/
public class ProteusSpinner extends AppCompatSpinner implements ProteusView {

    private Manager manager;

    public ProteusSpinner(Context context) {
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

    public String getSelectedValue(String key) {
        return ((ObjectValue) getSelectedItem()).getAsString(key);
    }

    @Override
    public Object getSelectedItem() {
        ObjectValue selectedItem = (ObjectValue) super.getSelectedItem();
        return selectedItem.getAsObject(Attributes.Spinner.item);
    }
}
