package com.flipkart.android.proteus.support.parser;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor;
import com.flipkart.android.proteus.processor.DrawableResourceProcessor;
import com.flipkart.android.proteus.processor.EventProcessor;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.support.view.ProteusSeekBar;
import com.flipkart.android.proteus.toolbox.Attributes;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Value;

/**
 * Created by Prasad Rao on 07-05-2020 22:26
 **/
public class SeekBarParser<V extends AppCompatSeekBar> extends ViewTypeParser<V> {

    private SeekBarChangeListener seekBarChangeListener;

    @NonNull
    @Override
    public String getType() {
        return "AppCompatSeekBar";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "ProgressBar";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout,
        @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        return new ProteusSeekBar(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor(Attributes.SeekBar.progressDrawable,
            new DrawableResourceProcessor<V>() {
            @Override
            public void setDrawable(V view, Drawable drawable) {
                view.setProgressDrawable(drawable);
            }
        });

        addAttributeProcessor(Attributes.SeekBar.thumb, new DrawableResourceProcessor<V>() {
            @Override
            public void setDrawable(V view, Drawable drawable) {
                view.setThumb(drawable);
            }
        });

        addAttributeProcessor(Attributes.SeekBar.thumbOffset, new DimensionAttributeProcessor<V>() {
            @Override
            public void setDimension(V view, float dimension) {
                view.setThumbOffset((int) dimension);
            }
        });

        addAttributeProcessor(Attributes.SeekBar.onProgressChanged, new EventProcessor<V>() {
            @Override
            public void setOnEventListener(final V view, final Value value) {
                SeekBarChangeListener seekBarChangeListener =
                    getSeekBarChangeListener().setEventProcessor(this)
                        .setProgressChangeValue(value)
                        .setView((ProteusView) view);
                view.setOnSeekBarChangeListener(seekBarChangeListener);
            }
        });

        addAttributeProcessor(Attributes.SeekBar.onProgressEnded, new EventProcessor<V>() {
            @Override
            public void setOnEventListener(final V view, final Value value) {
                SeekBarChangeListener seekBarChangeListener =
                    getSeekBarChangeListener().setEventProcessor(this)
                        .setProgressEndValue(value)
                        .setView((ProteusView) view);
                view.setOnSeekBarChangeListener(seekBarChangeListener);
            }
        });

        addAttributeProcessor(Attributes.SeekBar.min, new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                ((ProteusSeekBar) view).setMinValue(value.intValue());
            }
        });

        addAttributeProcessor(Attributes.SeekBar.stepSize, new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                ((ProteusSeekBar) view).setStepSize(value.intValue());
            }
        });
    }

    public SeekBarChangeListener getSeekBarChangeListener() {
        if (seekBarChangeListener == null) {
            seekBarChangeListener = new SeekBarChangeListener();
        }
        return seekBarChangeListener;
    }

    private class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        private Value progressChangeValue;
        private Value progressEndValue;
        private ProteusView view;
        private EventProcessor<V> eventProcessor;

        public SeekBarChangeListener setEventProcessor(EventProcessor<V> eventProcessor) {
            this.eventProcessor = eventProcessor;
            return this;
        }

        public SeekBarChangeListener setProgressChangeValue(Value progressChangeValue) {
            this.progressChangeValue = progressChangeValue;
            return this;
        }

        public SeekBarChangeListener setProgressEndValue(Value progressEndValue) {
            this.progressEndValue = progressEndValue;
            return this;
        }

        public SeekBarChangeListener setView(ProteusView view) {
            this.view = view;
            return this;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            eventProcessor.trigger(Attributes.SeekBar.onProgressChanged, progressChangeValue, view);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            eventProcessor.trigger(Attributes.SeekBar.onProgressEnded, progressEndValue, view);
        }
    }
}

