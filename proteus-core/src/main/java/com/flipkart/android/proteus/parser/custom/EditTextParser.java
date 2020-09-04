/*
 * Copyright 2019 Flipkart Internet Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flipkart.android.proteus.parser.custom;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.BooleanAttributeProcessor;
import com.flipkart.android.proteus.processor.DrawableResourceProcessor;
import com.flipkart.android.proteus.processor.EventProcessor;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.processor.StringAttributeProcessor;
import com.flipkart.android.proteus.toolbox.Attributes;
import com.flipkart.android.proteus.util.InputTypes;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Value;
import com.flipkart.android.proteus.view.ProteusEditText;

import java.lang.reflect.Field;

/**
 * Created by kirankumar on 25/11/14.
 */
public class EditTextParser<T extends EditText> extends ViewTypeParser<T> {
    private static final String FOCUS_DOWN = "down";
    private static final String FOCUS_RIGHT = "right";
    private static final String FOCUS_UP = "up";
    private static final String FOCUS_LEFT = "left";
    private final int DRAWABLE_RIGHT = 2;
    private Drawable showPasswordDrawable, hidePasswordDrawable;

    @NonNull
    @Override
    public String getType() {
        return "EditText";
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
        return new ProteusEditText(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor(Attributes.EditText.maxLength, new NumberAttributeProcessor<T>() {
            @Override
            public void setNumber(T view, @NonNull Number value) {
                view.setFilters(addInputFilter(view.getFilters(), new InputFilter.LengthFilter(value.intValue())));
            }
        });

        addAttributeProcessor(Attributes.EditText.inputType, new StringAttributeProcessor<T>() {
            @Override
            public void setString(T view, String value) {
                view.setInputType(InputTypes.getInputType(value));
            }
        });
        addAttributeProcessor(Attributes.EditText.nextAutoFocus, new StringAttributeProcessor<T>() {
            @Override
            public void setString(T view, String value) {
                nextAutoFocus(view, value);
            }
        });

        addAttributeProcessor(Attributes.EditText.enableShowPassword, new BooleanAttributeProcessor<T>() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void setBoolean(T view, boolean value) {
                if(value){
                    view.setOnTouchListener((v, event) -> {
                        if (event.getAction() == MotionEvent.ACTION_UP && view.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                            if (event.getRawX() >= view.getRight() - view.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {
                                if (view.getTransformationMethod() == null) {
                                    view.setTransformationMethod(new PasswordTransformationMethod());
                                    if(showPasswordDrawable != null){
                                        view.setCompoundDrawablesWithIntrinsicBounds(null, null, showPasswordDrawable, null);
                                    }
                                    view.setSelection(view.getText().length());
                                } else {
                                    view.setTransformationMethod(null);
                                    if(hidePasswordDrawable != null) {
                                        view.setCompoundDrawablesWithIntrinsicBounds(null, null, hidePasswordDrawable, null);
                                    }
                                    view.setSelection(view.getText().length());
                                }
                                return true;
                            }
                        }
                        return false;
                    });
                }
            }
        });
        addAttributeProcessor(Attributes.EditText.showPasswordDrawable, new DrawableResourceProcessor<T>() {
            @Override
            public void setDrawable(T view, Drawable drawable) {
                showPasswordDrawable = drawable;
            }
        });

        addAttributeProcessor(Attributes.EditText.hidePasswordDrawable, new DrawableResourceProcessor<T>() {
            @Override
            public void setDrawable(T view, Drawable drawable) {
                hidePasswordDrawable = drawable;
            }
        });

        addAttributeProcessor(Attributes.EditText.onTextEmpty, new EventProcessor<T>() {
            @Override
            public void setOnEventListener(T view, Value value) {
                    view.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if(s.toString().trim().isEmpty()){
                                trigger(Attributes.EditText.onTextEmpty,value,(ProteusView) view);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) { }
                    });
            }
        });

        addAttributeProcessor(Attributes.EditText.onTextNonEmpty, new EventProcessor<T>() {
            @Override
            public void setOnEventListener(T view, Value value) {
                view.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!s.toString().trim().isEmpty()){
                            trigger(Attributes.EditText.onTextNonEmpty,value,(ProteusView) view);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) { }
                });
            }
        });

        addAttributeProcessor(Attributes.EditText.digits, new StringAttributeProcessor<T>() {
            @Override
            public void setString(T view, String value) {
                view.setFilters(addInputFilter(view.getFilters(), (source, start, end, dest, dstart, dend) -> {
                    if (source instanceof SpannableStringBuilder) {
                        SpannableStringBuilder sourceAsSpannableBuilder = (SpannableStringBuilder)source;
                        for (int i = end - 1; i >= start; i--) {
                            char currentChar = source.charAt(i);
                            if (!value.contains(String.valueOf(currentChar))) {
                                sourceAsSpannableBuilder.delete(i, i+1);
                            }
                        }
                        return source;
                    } else {
                        StringBuilder filteredStringBuilder = new StringBuilder();
                        for (int i = start; i < end; i++) {
                            char currentChar = source.charAt(i);
                            if (value.contains(String.valueOf(currentChar))) {
                                filteredStringBuilder.append(currentChar);
                            }
                        }
                        return filteredStringBuilder.toString();
                    }
                }));
            }
        });

        addAttributeProcessor(Attributes.TextView.TextAllCaps, new StringAttributeProcessor<T>() {
            @Override
            public void setString(T view, String value) {
                view.setFilters(addInputFilter(view.getFilters(), new InputFilter.AllCaps()));
            }
        });
    }

    private void nextAutoFocus(EditText editText, String value) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int maxLength = getMaxLength(editText);
                if (maxLength == s.length()) {
                    View nextView = null;
                    switch (value) {
                        case FOCUS_DOWN:
                            nextView = editText.focusSearch(View.FOCUS_DOWN);
                            break;
                        case FOCUS_RIGHT:
                            nextView = editText.focusSearch(View.FOCUS_RIGHT);
                            break;
                        case FOCUS_UP:
                            nextView = editText.focusSearch(View.FOCUS_UP);
                            break;
                        case FOCUS_LEFT:
                            nextView = editText.focusSearch(View.FOCUS_LEFT);
                            break;
                    }
                    if (nextView != null)
                        nextView.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }


    private int getMaxLength(EditText editText){
        int length = 0;
        try {
            InputFilter[] inputFilters = editText.getFilters();
            for (InputFilter filter : inputFilters) {
                Class<?> c = filter.getClass();
                if (c.getName().equals(
                        "android.text.InputFilter$LengthFilter")) {
                    Field[] f = c.getDeclaredFields();
                    for (Field field : f) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            length = (Integer) field.get(filter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }
    InputFilter[] addInputFilter(InputFilter[] array, InputFilter inputFilter){
        InputFilter[] copy = new InputFilter[array.length + 1];
        System.arraycopy(array, 0, copy, 0, array.length);
        copy[array.length] = inputFilter;
        return copy;
    }
}
