package com.flipkart.android.proteus.support.design.widget;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.ColorResourceProcessor;
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor;
import com.flipkart.android.proteus.processor.DrawableResourceProcessor;
import com.flipkart.android.proteus.processor.GravityAttributeProcessor;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.processor.StringAttributeProcessor;
import com.flipkart.android.proteus.support.design.parser.TabsAttributeParser;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.google.android.material.tabs.TabLayout;

/**
 * Created by Prasad Rao on 28-02-2020 18:18
 **/
public class TabLayoutParser<V extends TabLayout> extends ViewTypeParser<V> {

    @NonNull
    @Override
    public String getType() {
        return "TabLayout";
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

        addAttributeProcessor("tabModeInt", new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setTabMode(value.intValue());
            }
        });

        addAttributeProcessor("tabPadding", new DimensionAttributeProcessor<V>() {
            @Override
            public void setDimension(V view, float dimension) {
                view.setPaddingRelative((int) dimension, (int) dimension, (int) dimension,
                    (int) dimension);
            }
        });

        addAttributeProcessor("tabTextColors", new ColorResourceProcessor<V>() {
            @Override
            public void setColor(V view, int color) {
                throw new IllegalArgumentException("itemIconTint must be a color state list");
            }

            @Override
            public void setColor(V view, ColorStateList colors) {
                view.setTabTextColors(colors);
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

        addAttributeProcessor("selectedTabIndicatorColor", new ColorResourceProcessor<V>() {
            @Override
            public void setColor(V view, int color) {
                view.setSelectedTabIndicatorColor(color);
            }

            @Override
            public void setColor(V view, ColorStateList colors) {
                view.setSelectedTabIndicatorColor(colors.getDefaultColor());
            }
        });

        addAttributeProcessor("tabGravity", new GravityAttributeProcessor<V>() {
            @Override
            public void setGravity(V view, int gravity) {
                view.setTabGravity(gravity);
            }
        });

        addAttributeProcessor("tabBackground", new DrawableResourceProcessor<V>() {
            @Override
            public void setDrawable(V view, Drawable drawable) {
                view.clearOnTabSelectedListeners();
                view.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        tab.setIcon(android.R.drawable.ic_input_add);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        tab.setIcon(android.R.drawable.ic_delete);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        tab.setIcon(android.R.drawable.ic_input_add);
                    }
                });
                for (int i = 0; i < view.getTabCount(); i++) {
                    TabLayout.Tab tab = view.getTabAt(i);
                    if (tab != null) {
                        if (i == 0) {
                            tab.setIcon(android.R.drawable.ic_input_add);
                        } else {
                            tab.setIcon(android.R.drawable.ic_delete);
                        }
                    }
                }
            }
        });
    }
}
