package com.flipkart.android.proteus.support.adapter;

import android.widget.BaseAdapter;

import androidx.annotation.NonNull;

import com.flipkart.android.proteus.support.view.ProteusSpinner;
import com.flipkart.android.proteus.value.ObjectValue;

/**
 * Created by Prasad Rao on 29-04-2020 12:37
 **/
public abstract class ProteusSpinnerAdapter extends BaseAdapter {

    public interface Builder<A extends ProteusSpinnerAdapter> {
        @NonNull
        A create(@NonNull ProteusSpinner spinner, @NonNull ObjectValue config);
    }
}
