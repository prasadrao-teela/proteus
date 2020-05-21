package com.proteus.animations;

import com.flipkart.android.proteus.ProteusBuilder;
import com.proteus.animations.parser.LottieAnimationViewParser;

/**
 * Created by Prasad Rao on 08-05-2020 17:42
 **/
public class AnimationModule implements ProteusBuilder.Module {
    @Override
    public void registerWith(ProteusBuilder builder) {
        builder.register(new LottieAnimationViewParser());
    }
}
