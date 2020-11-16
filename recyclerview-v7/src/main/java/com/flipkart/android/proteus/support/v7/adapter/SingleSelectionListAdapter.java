package com.flipkart.android.proteus.support.v7.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.flipkart.android.proteus.DataContext;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusLayoutInflater;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.value.Array;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Value;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Prasad Rao on 23-04-2020 17:25
 **/
public class SingleSelectionListAdapter extends ProteusRecyclerViewAdapter<ProteusViewHolder> {

    private ProteusContext context;
    private static final String ATTRIBUTE_ITEM_LAYOUT = "item-layout";
    private static final String ATTRIBUTE_ITEM_COUNT = "item-count";
    private static final String ATTRIBUTE_ITEMS = "items";
    private static final String ATTRIBUTE_ITEM = "item";
    private static final String ATTRIBUTE_SELECTED = "selected";

    public static final Builder<SingleSelectionListAdapter> BUILDER = (view, config) -> {
        Layout layout = config.getAsObject().getAsLayout(ATTRIBUTE_ITEM_LAYOUT);
        Integer count = config.getAsObject().getAsInteger(ATTRIBUTE_ITEM_COUNT);
        ObjectValue data = view.getViewManager().getDataContext().getData();
        ProteusContext context = (ProteusContext) view.getContext();

        return new SingleSelectionListAdapter(context, context.getInflater(), data,
            Objects.requireNonNull(layout),
            count != null ? count : 0);
    };

    private ProteusLayoutInflater inflater;

    private ObjectValue data;
    private int count;
    private Layout layout;
    private Map<String, Value> scope;

    private SingleSelectionListAdapter(ProteusContext context, ProteusLayoutInflater inflater, ObjectValue data,
                                       Layout layout, int count) {
        System.out.println("debug: ========== SingleSelectionListAdapter ===============");
        this.context = context;
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
        ObjectValue data = context.getData();
        holder.view.getViewManager().update(data);
        ObjectValue item = data.getAsObject(ATTRIBUTE_ITEM);
        if (item != null) {
            Boolean selected = item.getAsBoolean(ATTRIBUTE_SELECTED);
            holder.view.getAsView().setSelected(selected != null && selected);
            holder.view.getAsView().setOnClickListener(v -> {
                Array items = this.data.getAsArray(ATTRIBUTE_ITEMS);
                for (int i = 0; i < items.size(); i++) {
                    ObjectValue objectValue = items.get(i).getAsObject();
                    if (isEnableUnSelect() && objectValue.getAsBoolean(ATTRIBUTE_SELECTED, false)) {
                        objectValue.addProperty(ATTRIBUTE_SELECTED, false);
                    } else {
                        objectValue.addProperty(ATTRIBUTE_SELECTED, i == position);
                    }
                }
                notifyDataSetChanged();

                if (getOnItemClickListener() != null) {
                    getOnItemClickListener().onItemClick(holder.view, item, position);
                }

                if (getOnAnyItemSelectedListener() != null) {
                    if (isItemSelected())
                        getOnAnyItemSelectedListener().onAnyItemSelected(holder.view);
                }
                if (getOnNoItemSelectedListener() != null) {
                    if (!isItemSelected())
                        getOnNoItemSelectedListener().onNoItemSelected(holder.view);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public boolean isItemSelected() {
        Array items = this.data.getAsArray(ATTRIBUTE_ITEMS);
        for (int i = 0; i < items.size(); i++) {
            ObjectValue item = items.get(i).getAsObject();
            Boolean selected = item.getAsBoolean(ATTRIBUTE_SELECTED);
            if (selected != null && selected) return true;
        }
        return false;
    }

    public ObjectValue getSelectedItem() {
        System.out.println("debug: SingleSelectionListAdapter: " + data);
        Array items = this.data.getAsArray(ATTRIBUTE_ITEMS);
        for (int i = 0; i < items.size(); i++) {
            ObjectValue item = items.get(i).getAsObject();
            Boolean selected = item.getAsBoolean(ATTRIBUTE_SELECTED);
            if (selected != null && selected) return item;
        }
        return null;
    }

    public void updateData(Array data) {
        this.data.add(ATTRIBUTE_ITEMS, data);
        count = data.size();
        notifyDataSetChanged();
    }
    public void autoSelectItem() {
        Array items = this.data.getAsArray(ATTRIBUTE_ITEMS);
        for (int i = 0; i < items.size(); i++) {
            ObjectValue objectValue = DataContext.create(context, data, i, scope).getData();
            if(objectValue.getAsBoolean(ATTRIBUTE_SELECTED,false)){
                items.get(i).getAsObject().addProperty(ATTRIBUTE_SELECTED,true);
                break;
            }
        }
        notifyDataSetChanged();
    }
}
