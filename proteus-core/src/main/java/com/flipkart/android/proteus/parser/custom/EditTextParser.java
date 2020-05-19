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

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.processor.StringAttributeProcessor;
import com.flipkart.android.proteus.toolbox.Attributes;
import com.flipkart.android.proteus.util.InputTypes;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
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
                view.setFilters(new InputFilter[]{new InputFilter.LengthFilter(value.intValue())});
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
}
