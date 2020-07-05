package com.flipkart.android.proteus.processor;

import android.view.View;

import androidx.annotation.NonNull;

import com.flipkart.android.proteus.value.Array;
import com.flipkart.android.proteus.value.AttributeResource;
import com.flipkart.android.proteus.value.Resource;
import com.flipkart.android.proteus.value.StyleResource;
import com.flipkart.android.proteus.value.Value;

public abstract class ArrayAttributeProcessor<V extends View> extends AttributeProcessor<V> {
    @Override
    public void handleValue(V view, Value value) {
        if (value.isArray()) {
            final Array valueRange = value.getAsArray();
            setArray(view, valueRange);
        }
    }

    @Override
    public void handleResource(V view, Resource resource) { }

    @Override
    public void handleAttributeResource(V view, AttributeResource attribute) { }

    @Override
    public void handleStyleResource(V view, StyleResource style) { }

    protected abstract void setArray(V view, @NonNull Array value);
}
