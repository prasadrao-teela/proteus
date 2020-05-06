package com.flipkart.android.proteus.support.design.parser;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.support.design.widget.ProteusAppCompatButton;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;

/**
 * Created by Prasad Rao on 06-05-2020 22:30
 **/
public class AppCompatButtonParser<V extends AppCompatButton> extends ViewTypeParser<V> {
    @NonNull
    @Override
    public String getType() {
        return "AppCompatButton";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "Button";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout,
        @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        return new ProteusAppCompatButton(context);
    }

    @Override
    protected void addAttributeProcessors() {
    }
}
