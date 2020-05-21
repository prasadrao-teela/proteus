package com.proteus.animations.parser;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.BooleanAttributeProcessor;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.processor.StringAttributeProcessor;
import com.flipkart.android.proteus.toolbox.Attributes;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.proteus.animations.view.ProteusLottieAnimationView;

/**
 * Created by Prasad Rao on 08-05-2020 17:43
 **/
public class LottieAnimationViewParser<V extends LottieAnimationView> extends ViewTypeParser<V> {
    @NonNull
    @Override
    public String getType() {
        return "LottieAnimationView";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "ImageView";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout,
        @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        return new ProteusLottieAnimationView(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor(Attributes.LottieAnimationView.autoPlay,
            new BooleanAttributeProcessor<V>() {
            @Override
            public void setBoolean(V view, boolean value) {
                if (value) {
                    view.playAnimation();
                }
            }
        });

        addAttributeProcessor(Attributes.LottieAnimationView.loop,
            new BooleanAttributeProcessor<V>() {
            @Override
            public void setBoolean(V view, boolean value) {
                if (value) {
                    view.setRepeatCount(LottieDrawable.INFINITE);
                }
            }
        });

        addAttributeProcessor(Attributes.LottieAnimationView.repeatCount,
            new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setRepeatCount(value.intValue());
            }
        });

        addAttributeProcessor(Attributes.LottieAnimationView.fileName,
            new StringAttributeProcessor<V>() {
            @Override
            public void setString(V view, String value) {
                view.setAnimation(value);
            }
        });
        addAttributeProcessor(Attributes.LottieAnimationView.fileName,
            new StringAttributeProcessor<V>() {
            @Override
            public void setString(V view, String value) {
                view.setAnimation(value);
            }
        });
        addAttributeProcessor(Attributes.LottieAnimationView.jsonWithCacheKey,
            new StringAttributeProcessor<V>() {
            @Override
            public void setString(V view, String value) {
                String[] split = value.split(",");
                view.setAnimationFromJson(split[0], split[1]);
            }
        });
    }
}
