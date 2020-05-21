package com.flipkart.android.proteus.support;

import androidx.annotation.NonNull;

import com.flipkart.android.proteus.ProteusBuilder;
import com.flipkart.android.proteus.support.adapter.SimpleSpinnerAdapter;
import com.flipkart.android.proteus.support.adapter.SpinnerAdapterFactory;
import com.flipkart.android.proteus.support.parser.OtpViewParser;
import com.flipkart.android.proteus.support.parser.SeekBarParser;
import com.flipkart.android.proteus.support.parser.SpinnerParser;
import com.flipkart.android.proteus.support.parser.SpringDotIndicatorParser;

/**
 * Created by Prasad Rao on 29-04-2020 12:22
 **/
public class SupportModule implements ProteusBuilder.Module {
    private static final String ADAPTER_SIMPLE_LIST = "SimpleSpinnerAdapter";

    @NonNull private final SpinnerAdapterFactory adapterFactory;

    private SupportModule(@NonNull SpinnerAdapterFactory adapterFactory) {
        this.adapterFactory = adapterFactory;
    }

    public static SupportModule create() {
        return new Builder().build();
    }

    @Override
    public void registerWith(ProteusBuilder builder) {
        builder.register(new SpinnerParser(adapterFactory));
        builder.register(new OtpViewParser());
        builder.register(new SpringDotIndicatorParser());
        builder.register(new SeekBarParser());
    }

    public static class Builder {
        @NonNull private final SpinnerAdapterFactory adapterFactory = new SpinnerAdapterFactory();

        SupportModule build() {
            adapterFactory.register(ADAPTER_SIMPLE_LIST, SimpleSpinnerAdapter.BUILDER);
            return new SupportModule(adapterFactory);
        }
    }
}
