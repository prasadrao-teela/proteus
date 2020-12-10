package com.flipkart.android.proteus.support.adapter;

import android.view.View;
import android.view.ViewGroup;

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
 * Created by Prasad Rao on 29-04-2020 12:58
 **/
public class SimpleSpinnerAdapter extends ProteusSpinnerAdapter {

    private static final String ATTRIBUTE_ITEM_LAYOUT = "item-layout";
    private static final String ATTRIBUTE_ITEM_COUNT = "item-count";
    private static final String ATTRIBUTE_ITEMS = "items";
    private static final String ATTRIBUTE_SELECTED = "selected";

    public static final Builder<SimpleSpinnerAdapter> BUILDER = (view, config) -> {
        Layout layout = config.getAsObject().getAsLayout(ATTRIBUTE_ITEM_LAYOUT);
        Integer count = config.getAsObject().getAsInteger(ATTRIBUTE_ITEM_COUNT);
        ObjectValue data = view.getViewManager().getDataContext().getData();
        ProteusContext context = (ProteusContext) view.getContext();

        return new SimpleSpinnerAdapter(context, data, Objects.requireNonNull(layout),
            count != null ? count : 0);
    };

    private ProteusContext context;
    private ProteusLayoutInflater inflater;
    private ObjectValue data;
    private int count;
    private Layout layout;
    private Map<String, Value> scope;

    private SimpleSpinnerAdapter(ProteusContext context, ObjectValue data, Layout layout,
        int count) {
        this.context = context;
        this.inflater = context.getInflater();
        this.data = data;
        this.count = count;
        this.layout = new Layout(layout.type, layout.attributes, null, layout.extras);
        this.scope = layout.data;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public ObjectValue getItem(int position) {
        return DataContext.create(context, data, position, scope).getData();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProteusView view;
        if (convertView == null) {
            view = inflater.inflate(layout, new ObjectValue());
        } else {
            view = (ProteusView) convertView;
        }
        if (position == 0) {
            view.getAsView().setAlpha(0.5f);
        } else {
            view.getAsView().setAlpha(1);
        }
        ProteusView.Manager viewManager = view.getViewManager();
        ObjectValue data = getItem(position);
        viewManager.update(data);
        return view.getAsView();
    }

    public void updateData(Array data) {
        this.data.add(ATTRIBUTE_ITEMS, data);
        count = data.size();
        notifyDataSetChanged();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        if (position == 0) {
            view.setAlpha(0.5f);
            view.setFocusable(true);
            view.setEnabled(true);
            view.setClickable(true);
        } else {
            view.setAlpha(1);
            view.setFocusable(false);
            view.setEnabled(false);
            view.setClickable(false);
        }
        return view;
    }


    public int getDefaultSelectedItem(){
        Array items = this.data.getAsArray(ATTRIBUTE_ITEMS);
        for (int i = 0; i < items.size(); i++) {
            ObjectValue objectValue = DataContext.create(context, data, i, scope).getData();
            if(objectValue.getAsBoolean(ATTRIBUTE_SELECTED,false)){
                return i;
            }
        }
        return 0;
    }

    public void addPromptMessage(Value value){
        Array items = this.data.getAsArray(ATTRIBUTE_ITEMS);
        items.add(0, value);
    }
}
