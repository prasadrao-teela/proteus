package com.flipkart.android.proteus.parser;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.EventProcessor;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Value;
import com.flipkart.android.proteus.view.ProteusExpandableTextView;

/**
 * Created by Prasad Rao on 10-06-2020 20:01
 **/
public class ExpandableTextViewParser<V extends ProteusExpandableTextView>
    extends ViewTypeParser<V> {
    @NonNull
    @Override
    public String getType() {
        return "ExpandableTextView";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "TextView";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout,
        @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        return new ProteusExpandableTextView(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor("collapsibleMaxLines", new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setCollapsibleMaxLines(value.intValue());
            }
        });

        addAttributeProcessor("expandText", new EventProcessor<V>() {
            @Override
            public void setOnEventListener(V view, Value value) {
                view.setOnClickListener(v -> {
                    if (view.getMaxLines() > view.getCollapsibleMaxLines()) {
                        view.collapse();
                    } else {
                        view.expand();
                        trigger("expandText",value, view);
                    }
                });
            }
        });
    }
}
