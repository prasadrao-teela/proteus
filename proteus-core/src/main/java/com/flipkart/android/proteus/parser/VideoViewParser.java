package com.flipkart.android.proteus.parser;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.StringAttributeProcessor;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.view.ProteusVideoView;

/**
 * Created by Prasad Rao on 06-05-2020 11:43
 **/
public class VideoViewParser<V extends VideoView> extends ViewTypeParser<V> {
    @NonNull
    @Override
    public String getType() {
        return "VideoView";
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
        return new ProteusVideoView(context);
    }

    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor("videoUri", new StringAttributeProcessor<V>() {
            @Override
            public void setString(V view, String value) {
                view.setVideoURI(Uri.parse(value));
            }
        });
    }
}
