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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.support.v7.widget.ProteusRecyclerView;
import com.flipkart.android.proteus.value.ObjectValue;

/**
 * ProteusRecyclerViewAdapter.
 *
 * @author adityasharat
 */

public abstract class ProteusRecyclerViewAdapter<VH extends ProteusViewHolder>
    extends RecyclerView.Adapter<VH> {

    public interface Builder<A extends ProteusRecyclerViewAdapter<?>> {
        @NonNull
        A create(@NonNull ProteusRecyclerView view, @NonNull ObjectValue config);
    }

    private OnItemClickListener onItemClickListener;
    private static OnAnyItemSelectedListener onAnyItemSelectedListener;
    private static OnNoItemSelectedListener onNoItemSelectedListener;
    private boolean enableUnSelect;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnAnyItemSelectedListener(OnAnyItemSelectedListener listener){
        onAnyItemSelectedListener = listener;
    }

    public OnAnyItemSelectedListener getOnAnyItemSelectedListener(){
        return onAnyItemSelectedListener;
    }

    public void setOnNoItemSelectedListener(OnNoItemSelectedListener listener){
        onNoItemSelectedListener = listener;
    }

    public OnNoItemSelectedListener getOnNoItemSelectedListener(){
        return onNoItemSelectedListener;
    }

    public void setEnableUnSelect(boolean enableUnSelect){
        this.enableUnSelect = enableUnSelect;
    }

    public boolean isEnableUnSelect(){
        return enableUnSelect;
    }

    public interface OnItemClickListener {
        void onItemClick(ProteusView view, ObjectValue data, int position);
    }

    public interface OnAnyItemSelectedListener {
        void onAnyItemSelected(ProteusView view);
    }

    public interface OnNoItemSelectedListener {
        void onNoItemSelected(ProteusView view);
    }
}
