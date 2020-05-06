package com.flipkart.android.proteus.support.parser;

import android.content.res.ColorStateList;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.BooleanAttributeProcessor;
import com.flipkart.android.proteus.processor.ColorResourceProcessor;
import com.flipkart.android.proteus.support.view.custom.ProteusSpringDotsIndicator;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

/**
 * Created by Prasad Rao on 06-05-2020 21:49
 **/
public class SpringDotIndicatorParser<V extends SpringDotsIndicator> extends ViewTypeParser<V> {

    private ViewPager viewPager;

    @NonNull
    @Override
    public String getType() {
        return "SpringDotsIndicator";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "FrameLayout";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout,
        @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        if (parent != null) {
            viewPager = parent.findViewWithTag("viewPagerWithCircularIndicator");
        }
        return new ProteusSpringDotsIndicator(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor("dotsColor", new ColorResourceProcessor<V>() {
            @Override
            public void setColor(V view, int color) {
                view.setDotsColor(color);
            }

            @Override
            public void setColor(V view, ColorStateList colors) {
            }
        });

        addAttributeProcessor("dotsStrokeColor", new ColorResourceProcessor<V>() {
            @Override
            public void setColor(V view, int color) {
                view.setStrokeDotsIndicatorColor(color);
            }

            @Override
            public void setColor(V view, ColorStateList colors) {

            }
        });

        addAttributeProcessor("dotIndicatorColor", new ColorResourceProcessor<V>() {
            @Override
            public void setColor(V view, int color) {
                view.setDotIndicatorColor(color);
            }

            @Override
            public void setColor(V view, ColorStateList colors) {

            }
        });

        addAttributeProcessor("setUpViewPager", new BooleanAttributeProcessor<V>() {
            @Override
            public void setBoolean(V view, boolean value) {
                if (value && viewPager != null) {
                    view.setViewPager(viewPager);
                }
            }
        });
    }
}
