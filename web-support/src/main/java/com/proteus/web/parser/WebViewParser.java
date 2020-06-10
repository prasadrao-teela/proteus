package com.proteus.web.parser;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.EventProcessor;
import com.flipkart.android.proteus.processor.StringAttributeProcessor;
import com.flipkart.android.proteus.toolbox.Attributes;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Value;
import com.proteus.web.view.ProteusWebView;

/**
 * Created by Prasad Rao on 09-06-2020 10:51
 **/
public class WebViewParser<V extends ProteusWebView> extends ViewTypeParser<V> {
    @NonNull
    @Override
    public String getType() {
        return "WebView";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "View";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout,
        @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        return new ProteusWebView(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor(Attributes.WebView.Url, new StringAttributeProcessor<V>() {
            @Override
            public void setString(V view, String value) {
                view.loadUrl(value);
            }
        });
        addAttributeProcessor(Attributes.WebView.HTML, new StringAttributeProcessor<V>() {
            @Override
            public void setString(V view, String value) {
                view.loadData(value, "text/html", "UTF-8");
            }
        });

        addAttributeProcessor(Attributes.WebView.onWebPageCompleted, new EventProcessor<V>() {
            @Override
            public void setOnEventListener(V view, Value value) {
                view.setWebPageCallback(() -> trigger(Attributes.WebView.onWebPageCompleted,
                    value, view));
            }
        });
    }
}
