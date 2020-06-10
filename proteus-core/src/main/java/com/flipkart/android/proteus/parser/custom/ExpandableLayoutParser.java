package com.flipkart.android.proteus.parser.custom;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.BooleanAttributeProcessor;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.view.ProteusExpandableView;
import com.flipkart.android.proteus.view.custom.ExpandableLayout;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;

/**
 * Created by Sandeep on 05-06-2020 00:26
 **/

public class ExpandableLayoutParser <V extends ExpandableLayout> extends ViewTypeParser<V> {
    @NonNull
    @Override
    public String getType() {
        return "ExpandableLayout";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "FrameLayout";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout, @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        return  new ProteusExpandableView(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor("el_duration", new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setDuration(value.intValue());
            }
        });

        addAttributeProcessor("el_expanded", new BooleanAttributeProcessor<V>() {
            @Override
            public void setBoolean(V view, boolean value) {
                view.setExpanded(value);
            }
        });

        addAttributeProcessor("el_parallax", new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setParallax(value.intValue());
            }
        });

        addAttributeProcessor("android_orientation", new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setOrientation(value.intValue());
            }
        });


    }
}
