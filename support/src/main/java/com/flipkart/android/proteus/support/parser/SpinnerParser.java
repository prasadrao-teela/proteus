package com.flipkart.android.proteus.support.parser;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.flipkart.android.proteus.ProteusConstants;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.AttributeProcessor;
import com.flipkart.android.proteus.processor.EventProcessor;
import com.flipkart.android.proteus.support.adapter.ProteusSpinnerAdapter;
import com.flipkart.android.proteus.support.adapter.SpinnerAdapterFactory;
import com.flipkart.android.proteus.support.view.ProteusSpinner;
import com.flipkart.android.proteus.toolbox.Attributes;
import com.flipkart.android.proteus.value.AttributeResource;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Resource;
import com.flipkart.android.proteus.value.StyleResource;
import com.flipkart.android.proteus.value.Value;

/**
 * Created by Prasad Rao on 29-04-2020 12:54
 **/
public class SpinnerParser<V extends AppCompatSpinner> extends ViewTypeParser<V> {

    private static final String ATTRIBUTE_ADAPTER = "adapter";
    private static final String ATTRIBUTE_TYPE = ProteusConstants.TYPE;

    @NonNull private final SpinnerAdapterFactory adapterFactory;

    public SpinnerParser(@NonNull SpinnerAdapterFactory adapterFactory) {
        this.adapterFactory = adapterFactory;
    }

    @NonNull
    @Override
    public String getType() {
        return "AppCompatSpinner";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "ViewGroup";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout,
        @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        return new ProteusSpinner(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor(ATTRIBUTE_ADAPTER, new AttributeProcessor<V>() {

            @Override
            public void handleValue(V view, Value value) {
                if (value.isObject()) {
                    String type = value.getAsObject().getAsString(ATTRIBUTE_TYPE);
                    if (type != null) {
                        ProteusSpinnerAdapter adapter =
                            adapterFactory.create(type, (ProteusSpinner) view, value.getAsObject());
                        view.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void handleResource(V view, Resource resource) {
                throw new IllegalArgumentException("Recycler View 'adapter' expects only object " +
                    "values");
            }

            @Override
            public void handleAttributeResource(V view, AttributeResource attribute) {
                throw new IllegalArgumentException("Recycler View 'adapter' expects only object " +
                    "values");
            }

            @Override
            public void handleStyleResource(V view, StyleResource style) {
                throw new IllegalArgumentException("Recycler View 'adapter' expects only object " +
                    "values");
            }
        });

        addAttributeProcessor(Attributes.Spinner.onSpinnerItemSelected, new EventProcessor<V>() {
            @Override
            public void setOnEventListener(V view, Value value) {
                view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v, int position,
                        long id) {
                        trigger(Attributes.Spinner.onSpinnerItemSelected, value, (ProteusView) view);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }
}
