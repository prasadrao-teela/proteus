package com.flipkart.android.proteus.support.parser;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.ColorResourceProcessor;
import com.flipkart.android.proteus.processor.DrawableResourceProcessor;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.processor.StringAttributeProcessor;
import com.flipkart.android.proteus.support.view.ProteusOtpView;
import com.flipkart.android.proteus.support.view.custom.OtpEntryEditText;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;

/**
 * Created by Prasad Rao on 30-04-2020 15:43
 **/
public class OtpViewParser<V extends OtpEntryEditText> extends ViewTypeParser<V> {
    @NonNull
    @Override
    public String getType() {
        return "OtpEntryEditText";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "AppCompatEditText";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout,
        @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        return new ProteusOtpView(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor("itemBackground", new DrawableResourceProcessor<V>() {
            @Override
            public void setDrawable(V view, Drawable drawable) {
                view.setItemBackground(drawable);
            }
        });

        addAttributeProcessor("itemCount", new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setItemCount(value.intValue());
            }
        });

        addAttributeProcessor("lineColor", new ColorResourceProcessor<V>() {
            @Override
            public void setColor(V view, int color) {
                view.setLineColor(color);
            }

            @Override
            public void setColor(V view, ColorStateList colors) {
                view.setLineColor(colors);
            }
        });

        addAttributeProcessor("viewType", new StringAttributeProcessor<V>() {
            @Override
            public void setString(V view, String value) {
                view.setViewType(value);
            }
        });
    }
}
