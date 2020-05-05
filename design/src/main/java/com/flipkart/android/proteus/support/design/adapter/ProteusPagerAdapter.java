package com.flipkart.android.proteus.support.design.adapter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.flipkart.android.proteus.support.design.widget.ProteusViewPager;
import com.flipkart.android.proteus.value.ObjectValue;

/**
 * Created by Prasad Rao on 28-04-2020 13:36
 **/
public abstract class ProteusPagerAdapter extends PagerAdapter {

    public interface Builder<A extends ProteusPagerAdapter> {
        @NonNull
        A create(@NonNull ProteusViewPager viewPager, @NonNull ObjectValue config);
    }
}
