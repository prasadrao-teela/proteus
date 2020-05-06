package com.flipkart.android.proteus.support.design.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.DataContext;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusLayoutInflater;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Value;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Prasad Rao on 28-04-2020 13:29
 **/
public class TabViewPagerAdapter extends ProteusPagerAdapter {

    private static final String ATTRIBUTE_ITEM_LAYOUT = "item-layout";
    private static final String ATTRIBUTE_ITEM_COUNT = "item-count";
    private static final String ITEMS = "items";
    private static final String TITLE = "title";

    private ProteusLayoutInflater inflater;

    private ObjectValue data;
    private int count;
    private Layout layout;
    private Map<String, Value> scope;

    private TabViewPagerAdapter(ProteusLayoutInflater inflater, ObjectValue data, Layout layout,
        int count) {
        this.inflater = inflater;
        this.data = data;
        this.count = count;
        this.layout = new Layout(layout.type, layout.attributes, null, layout.extras);
        this.scope = layout.data;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final ProteusView view = inflater.inflate(layout, new ObjectValue());
        final DataContext context =
            DataContext.create(view.getViewManager().getContext(), data, position, scope);

        // Updates the data
        view.getViewManager().update(context.getData());

        final View asView = view.getAsView();
        container.addView(asView);
        return asView;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ViewGroup) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        final Value title = data.getAsArray(ITEMS).get(position).getAsObject().get(TITLE);
        return title == null ? null : title.getAsString();
    }

    public static final Builder<TabViewPagerAdapter> BUILDER = (viewPager, config) -> {
        Layout layout = config.getAsObject().getAsLayout(ATTRIBUTE_ITEM_LAYOUT);
        Integer count = config.getAsObject().getAsInteger(ATTRIBUTE_ITEM_COUNT);
        ObjectValue data = viewPager.getViewManager().getDataContext().getData();
        ProteusContext context = (ProteusContext) viewPager.getContext();

        return new TabViewPagerAdapter(context.getInflater(), data, Objects.requireNonNull(layout),
            count != null ? count : 0);
    };

}
