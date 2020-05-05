package com.flipkart.android.proteus.support.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.support.view.ProteusSpinner;
import com.flipkart.android.proteus.value.ObjectValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Prasad Rao on 29-04-2020 12:37
 **/
public class SpinnerAdapterFactory {

    private Map<String, ProteusSpinnerAdapter.Builder> adapters = new HashMap<>();

    public void register(@NonNull String type, @NonNull ProteusSpinnerAdapter.Builder builder) {
        adapters.put(type, builder);
    }

    @Nullable
    public ProteusSpinnerAdapter.Builder remove(@NonNull String type) {
        return adapters.remove(type);
    }

    public ProteusSpinnerAdapter create(@NonNull String type, @NonNull ProteusSpinner view,
        @NonNull ObjectValue config) {
        return Objects.requireNonNull(adapters.get(type)).create(view, config);
    }
}
