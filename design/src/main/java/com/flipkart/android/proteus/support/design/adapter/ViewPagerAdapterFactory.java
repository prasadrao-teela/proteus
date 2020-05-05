package com.flipkart.android.proteus.support.design.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.support.design.widget.ProteusViewPager;
import com.flipkart.android.proteus.value.ObjectValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Prasad Rao on 28-02-2020 19:50
 **/
public class ViewPagerAdapterFactory {

    private Map<String, ProteusPagerAdapter.Builder> adapters = new HashMap<>();

    public void register(@NonNull String type, @NonNull ProteusPagerAdapter.Builder builder) {
        adapters.put(type, builder);
    }

    @Nullable
    public ProteusPagerAdapter.Builder remove(@NonNull String type) {
        return adapters.remove(type);
    }

    public ProteusPagerAdapter create(@NonNull String type, @NonNull ProteusViewPager view,
        @NonNull ObjectValue config) {
        return Objects.requireNonNull(adapters.get(type)).create(view, config);
    }
}
