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

package com.flipkart.android.proteus.support.v7.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.flipkart.android.proteus.DataContext;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusLayoutInflater;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Value;

import java.util.Map;

/**
 *
 */
public class CustomRecyclerViewAdapter extends ProteusRecyclerViewAdapter<ProteusViewHolder> {

    private static final String ATTRIBUTE_ITEM_LAYOUT = "item-layout";
    private static final String ATTRIBUTE_ITEM_COUNT = "item-count";
    private static final String ITEM = "item";
    private static final String IS_SELECTED = "isSelected";
    private static final String CHECKBOX_ID = "checkboxId";
    private static final String OVER_DUE_AMOUNT = "overDueAmount";
    private boolean onBind;
    private int totalAmount;
    public static final String  ACTION_UPDATED_SELECTED_AMOUNT = "com.prefr.action.update_selected_amount";
    public static final String EXTRA_SELECTED_AMOUNT = "extraSelectedAmount";

    public static final Builder<CustomRecyclerViewAdapter> BUILDER = (view, config) -> {
        Layout layout = config.getAsObject().getAsLayout(ATTRIBUTE_ITEM_LAYOUT);
        Integer count = config.getAsObject().getAsInteger(ATTRIBUTE_ITEM_COUNT);
        ObjectValue data = view.getViewManager().getDataContext().getData();
        ProteusContext context = (ProteusContext) view.getContext();
        return new CustomRecyclerViewAdapter(context.getInflater(), data, layout, count != null ? count : 0);
    };

    private ProteusLayoutInflater inflater;

    private ObjectValue data;
    private int count;
    private Layout layout;
    private Map<String, Value> scope;

    private CustomRecyclerViewAdapter(ProteusLayoutInflater inflater, ObjectValue data, Layout layout, int count) {
        this.inflater = inflater;
        this.data = data;
        this.count = count;
        this.layout = new Layout(layout.type, layout.attributes, null, layout.extras);
        this.scope = layout.data;
    }

    @NonNull
    @Override
    public ProteusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        ProteusView view = inflater.inflate(layout, new ObjectValue());
        return new ProteusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProteusViewHolder holder, int position) {
        DataContext context = DataContext.create(holder.context, data, position, scope);
        holder.view.getViewManager().update(context.getData());
        ObjectValue itemObjectValue = context.getData().getAsObject(ITEM);
        if (itemObjectValue != null) {
            String checkBoxId = itemObjectValue.getAsString(CHECKBOX_ID);
            if (TextUtils.isEmpty(checkBoxId)) {
                return;
            }
            ObjectValue currentObjectValue = itemObjectValue.getAsObject();
            if (currentObjectValue != null) {
                View view = holder.view.getViewManager().findViewById(checkBoxId);
                if (view instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) view;
                    checkBox.setTag(position);
                    boolean isSelected = currentObjectValue.getAsBoolean(IS_SELECTED, false);
                    onBind = true;
                    if (isSelected) {
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                    }
                    onBind = false;
                    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (!onBind) {
                            int index = Integer.parseInt(buttonView.getTag().toString());
                            totalAmount = 0;
                            for (int i = 0; i < count; i++) {
                                ObjectValue item = DataContext.create(holder.context, data, i, scope).getData().getAsObject(ITEM);
                                if (item != null) {
                                    int overDueAmount = item.getAsInteger(OVER_DUE_AMOUNT, 0);
                                    if ((isChecked && i <= index) || (!isChecked && i < index)) {
                                        totalAmount = totalAmount + overDueAmount;
                                        item.getAsObject().addProperty(IS_SELECTED, true);
                                    } else {
                                        item.getAsObject().addProperty(IS_SELECTED, false);
                                    }
                                }
                            }
                            notifySelectedAmount(holder.context);
                            notifyDataSetChanged();
                        }
                    });
                }
            }
        }
    }

    private void notifySelectedAmount(Context context){
        Intent intent = new Intent(ACTION_UPDATED_SELECTED_AMOUNT);
        intent.putExtra(EXTRA_SELECTED_AMOUNT,totalAmount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return count;
    }
}
