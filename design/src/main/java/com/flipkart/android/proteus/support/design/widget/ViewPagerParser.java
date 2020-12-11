package com.flipkart.android.proteus.support.design.widget;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flipkart.android.proteus.DataContext;
import com.flipkart.android.proteus.ProteusConstants;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.managers.AdapterBasedViewManager;
import com.flipkart.android.proteus.processor.AttributeProcessor;
import com.flipkart.android.proteus.processor.BooleanAttributeProcessor;
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor;
import com.flipkart.android.proteus.processor.DrawableResourceProcessor;
import com.flipkart.android.proteus.processor.EventProcessor;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.support.design.adapter.ProteusPagerAdapter;
import com.flipkart.android.proteus.support.design.adapter.ViewPagerAdapterFactory;
import com.flipkart.android.proteus.value.AttributeResource;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Resource;
import com.flipkart.android.proteus.value.StyleResource;
import com.flipkart.android.proteus.value.Value;
import com.google.android.material.tabs.TabLayout;

/**
 * Created by Prasad Rao on 28-02-2020 18:18
 **/
public class ViewPagerParser<V extends ViewPager> extends ViewTypeParser<V> {
    private static final String ATTRIBUTE_ADAPTER = "adapter";

    private static final String ATTRIBUTE_TYPE = ProteusConstants.TYPE;

    @NonNull private final ViewPagerAdapterFactory adapterFactory;

    private TabLayout tabLayout;

    public ViewPagerParser(@NonNull ViewPagerAdapterFactory adapterFactory) {
        this.adapterFactory = adapterFactory;
    }

    @NonNull
    @Override
    public String getType() {
        return "ViewPager";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "ViewGroup";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout,
        @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        ProteusViewPager proteusViewPager = new ProteusViewPager(context);
        if (parent != null) {
            tabLayout = parent.findViewWithTag("tabLayout");
            if (tabLayout != null) {
                tabLayout.setupWithViewPager(proteusViewPager);
            }
        }
        return proteusViewPager;
    }

    @NonNull
    @Override
    public ProteusView.Manager createViewManager(@NonNull ProteusContext context,
        @NonNull ProteusView view, @NonNull Layout layout, @NonNull ObjectValue data,
        @Nullable ViewTypeParser caller, @Nullable ViewGroup parent, int dataIndex) {
        DataContext dataContext = createDataContext(context, layout, data, parent, dataIndex);
        return new AdapterBasedViewManager(context,
            null != caller ? caller : this, view.getAsView(), layout, dataContext);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor(ATTRIBUTE_ADAPTER, new AttributeProcessor<V>() {

            @Override
            public void handleValue(V view, Value value) {
                if (value.isObject()) {
                    String type = value.getAsObject().getAsString(ATTRIBUTE_TYPE);
                    if (type != null) {
                        ProteusPagerAdapter adapter =
                            adapterFactory.create(type, (ProteusViewPager) view,
                                value.getAsObject());
                        view.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void handleResource(V view, Resource resource) {
                throw new IllegalArgumentException(
                    "View pager 'adapter' expects only object " + "values");
            }

            @Override
            public void handleAttributeResource(V view, AttributeResource attribute) {
                throw new IllegalArgumentException(
                    "View pager 'adapter' expects only object " + "values");
            }

            @Override
            public void handleStyleResource(V view, StyleResource style) {
                throw new IllegalArgumentException(
                    "View pager 'adapter' expects only object " + "values");
            }
        });

        addAttributeProcessor("offscreenPageLimit", new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setOffscreenPageLimit(value.intValue());
            }
        });

        addAttributeProcessor("currentItem", new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setCurrentItem(value.intValue(), true);
            }
        });

        addAttributeProcessor("tabBackground", new DrawableResourceProcessor<V>() {
            @Override
            public void setDrawable(V view, Drawable drawable) {

                if (tabLayout == null) return;

                tabLayout.clearOnTabSelectedListeners();
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
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

        addAttributeProcessor("clipToPadding", new BooleanAttributeProcessor<V>() {
            @Override
            public void setBoolean(V view, boolean value) {
                view.setClipToPadding(value);
            }
        });

        addAttributeProcessor("pageMargin", new DimensionAttributeProcessor<V>() {
            @Override
            public void setDimension(V view, float value) {
                view.setPageMargin((int) value);

            }
        });

        addAttributeProcessor("onPageChanged", new EventProcessor<V>() {
            @Override
            public void setOnEventListener(V view, Value value) {
                view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

                    @Override
                    public void onPageSelected(int position) {
                        PagerAdapter adapter = view.getAdapter();
                        if (adapter instanceof ProteusPagerAdapter) {
                            Value action = ((ProteusPagerAdapter) adapter).getActions(position);
                            trigger("onPageChanged", action, (ProteusView) view);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) { }
                });
            }
        });
    }
}
