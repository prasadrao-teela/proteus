package com.flipkart.android.proteus.processor;

import android.view.View;

import androidx.annotation.NonNull;

import com.flipkart.android.proteus.value.AttributeResource;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Resource;
import com.flipkart.android.proteus.value.StyleResource;
import com.flipkart.android.proteus.value.Value;

/**
 * Created by Prasad Rao on 29-04-2020 16:35
 **/
public abstract class ObjectAttributeProcessor<V extends View> extends AttributeProcessor<V> {
    @Override
    public void handleValue(V view, Value value) {
        if (value.isObject()) {
            final ObjectValue valueRange = value.getAsObject();
            setObject(view, valueRange);
        }
    }

    @Override
    public void handleResource(V view, Resource resource) {

    }

    @Override
    public void handleAttributeResource(V view, AttributeResource attribute) {

    }

    @Override
    public void handleStyleResource(V view, StyleResource style) {

    }

    protected abstract void setObject(V view, @NonNull ObjectValue value);
}
