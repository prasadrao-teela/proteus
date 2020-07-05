package com.flipkart.android.proteus.parser.custom;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.ArrayAttributeProcessor;
import com.flipkart.android.proteus.processor.ColorResourceProcessor;
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.processor.StringAttributeProcessor;
import com.flipkart.android.proteus.value.Array;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.view.custom.ProgressIndicatorView;
import com.flipkart.android.proteus.view.custom.ProteusProgressIndicatorView;

public class ProgressIndicatorParser<T extends ProgressIndicatorView> extends ViewTypeParser<T> {
    @NonNull
    @Override
    public String getType() {
        return "ProgressIndicatorView";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "View";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout, @NonNull ObjectValue data,
                                  @Nullable ViewGroup parent, int dataIndex) {
        return new ProteusProgressIndicatorView(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor("pendingStepTextColor", new ColorResourceProcessor<T>() {
            @Override
            public void setColor(T view, int color) {
                view.setPendingStepTextColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setPendingStepTextColor(colors.getDefaultColor());
            }
        });

        addAttributeProcessor("pendingStepBackgroundColor", new ColorResourceProcessor<T>() {
            @Override
            public void setColor(T view, int color) {
                view.setPendingStepBackgroundColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setPendingStepBackgroundColor(colors.getDefaultColor());
            }
        });

        addAttributeProcessor("pendingStepBorderColor", new ColorResourceProcessor<T>() {
            @Override
            public void setColor(T view, int color) {
                view.setPendingStepBorderColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setPendingStepBorderColor(colors.getDefaultColor());
            }
        });

        addAttributeProcessor("currentStepTextColor", new ColorResourceProcessor<T>() {
            @Override
            public void setColor(T view, int color) {
                view.setCurrentStepTextColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setCurrentStepTextColor(colors.getDefaultColor());
            }
        });

        addAttributeProcessor("currentStepBackgroundColor", new ColorResourceProcessor<T>() {
            @Override
            public void setColor(T view, int color) {
                view.setCurrentStepBackgroundColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setCurrentStepBackgroundColor(colors.getDefaultColor());
            }
        });

        addAttributeProcessor("currentStepBorderColor", new ColorResourceProcessor<T>() {
            @Override
            public void setColor(T view, int color) {
                view.setCurrentStepBorderColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setCurrentStepBorderColor(colors.getDefaultColor());
            }
        });

        addAttributeProcessor("completedStepTextColor", new ColorResourceProcessor<T>() {
            @Override
            public void setColor(T view, int color) {
                view.setCompletedStepTextColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setCompletedStepTextColor(colors.getDefaultColor());
            }
        });

        addAttributeProcessor("completedStepBackgroundColor", new ColorResourceProcessor<T>() {
            @Override
            public void setColor(T view, int color) {
                view.setCompletedStepBackgroundColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setCompletedStepBackgroundColor(colors.getDefaultColor());
            }
        });

        addAttributeProcessor("completedStepBorderColor", new ColorResourceProcessor<T>() {
            @Override
            public void setColor(T view, int color) {
                view.setCompletedStepBorderColor(color);
            }

            @Override
            public void setColor(T view, ColorStateList colors) {
                view.setCompletedStepBorderColor(colors.getDefaultColor());
            }
        });


        addAttributeProcessor("currentStepIndex", new NumberAttributeProcessor<T>() {
            @Override
            public void setNumber(T view, @NonNull Number value) {
                view.setCurrentStepPosition(value.intValue());
            }
        });

        addAttributeProcessor("borderStroke", new DimensionAttributeProcessor<T>() {
            @Override
            public void setDimension(T view, float dimension) {
                view.setBorderStroke(dimension);
            }
        });

        addAttributeProcessor("stepCornerRadius", new DimensionAttributeProcessor<T>() {
            @Override
            public void setDimension(T view, float dimension) {
                view.setStepCornerRadius(dimension);
            }
        });

        addAttributeProcessor("stepWidth", new DimensionAttributeProcessor<T>() {
            @Override
            public void setDimension(T view, float dimension) {
                view.setStepWidth(dimension);
            }
        });

        addAttributeProcessor("lineWidth", new DimensionAttributeProcessor<T>() {
            @Override
            public void setDimension(T view, float dimension) {
                view.setLineWidth(dimension);
            }
        });

        addAttributeProcessor("lineStroke", new DimensionAttributeProcessor<T>() {
            @Override
            public void setDimension(T view, float dimension) {
                view.setLineStroke(dimension);
            }
        });

        addAttributeProcessor("currentStepExtraWidth", new DimensionAttributeProcessor<T>() {
            @Override
            public void setDimension(T view, float dimension) {
                view.setCurrentStepExtraWidth(dimension);
            }
        });

        addAttributeProcessor("textSize", new DimensionAttributeProcessor<T>() {
            @Override
            public void setDimension(T view, float dimension) {
                view.setTextSize(dimension);
            }
        });

        addAttributeProcessor("textFont", new StringAttributeProcessor<T>() {
            @Override
            public void setString(T view, String value) {
                Typeface typeface;
                typeface = Typeface.createFromAsset(view.getContext().getAssets(), value);
                if (typeface != null) {
                    view.setTextFont(typeface);
                }
            }
        });

        addAttributeProcessor("entries", new ArrayAttributeProcessor<T>() {
            @Override
            protected void setArray(T view, @NonNull Array value) {
                String[] entries = new String[value.size()];
                for (int index = 0; index < value.size(); index++) {
                    entries[index] = value.get(index).toString();
                }
                view.setEntries(entries);
            }
        });
    }
}
