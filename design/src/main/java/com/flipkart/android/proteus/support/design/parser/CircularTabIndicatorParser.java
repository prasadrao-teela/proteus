package com.flipkart.android.proteus.support.design.parser;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.ColorResourceProcessor;
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor;
import com.flipkart.android.proteus.processor.GravityAttributeProcessor;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.processor.StringAttributeProcessor;
import com.flipkart.android.proteus.support.design.R;
import com.flipkart.android.proteus.support.design.widget.ProteusTabLayout;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.google.android.material.tabs.TabLayout;

/**
 * Created by Prasad Rao on 28-02-2020 18:18
 **/
public class CircularTabIndicatorParser<V extends TabLayout> extends ViewTypeParser<V> {

    @NonNull
    @Override
    public String getType() {
        return "CircularTabIndicator";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "HorizontalScrollView";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout,
        @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        ProteusTabLayout tabLayout = new ProteusTabLayout(context);
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        tabLayout.setTabTextColors(Color.TRANSPARENT, Color.TRANSPARENT);
        if (parent != null) {
            ViewPager viewPager = parent.findViewWithTag("viewPagerWithCircularIndicator");
            if (viewPager != null) {
                tabLayout.setupWithViewPager(viewPager, true);
            }
        }
        return tabLayout;
    }

    @Override
    protected void addAttributeProcessors() {

        addAttributeProcessor("tabMode", new StringAttributeProcessor<V>() {
            @Override
            public void setString(V view, String value) {
                view.setTabMode(TabsAttributeParser.getTabMode(value));
            }
        });

        addAttributeProcessor("tabPadding", new DimensionAttributeProcessor<V>() {
            @Override
            public void setDimension(V view, float dimension) {
                view.setPaddingRelative((int) dimension, (int) dimension, (int) dimension,
                    (int) dimension);
            }
        });

        addAttributeProcessor("backgroundColor", new ColorResourceProcessor<V>() {
            @Override
            public void setColor(V view, int color) {
                view.setBackgroundColor(color);
            }

            @Override
            public void setColor(V view, ColorStateList colors) {
                view.setBackgroundColor(colors.getDefaultColor());
            }
        });

        addAttributeProcessor("tabGravity", new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setTabGravity(value.intValue());
            }
        });

        addAttributeProcessor("viewPagerTag", new StringAttributeProcessor<V>() {
            @Override
            public void setString(V view, String value) {
                view.clearOnTabSelectedListeners();
                view.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        tab.setIcon(R.drawable.selected_dot);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        tab.setIcon(R.drawable.default_dot);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        tab.setIcon(R.drawable.selected_dot);
                    }
                });
                for (int i = 0; i < view.getTabCount(); i++) {
                    TabLayout.Tab tab = view.getTabAt(i);
                    if (tab != null) {
                        if (i == 0) {
                            tab.setIcon(R.drawable.selected_dot);
                        } else {
                            tab.setIcon(R.drawable.default_dot);
                        }
                    }
                }
            }
        });
    }
}
