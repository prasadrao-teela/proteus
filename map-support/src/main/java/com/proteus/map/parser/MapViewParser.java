package com.proteus.map.parser;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.EventProcessor;
import com.flipkart.android.proteus.toolbox.Attributes;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.flipkart.android.proteus.value.Value;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.proteus.map.constant.Keys;
import com.proteus.map.view.ProteusMapView;

public class MapViewParser <V extends ProteusMapView> extends ViewTypeParser<V> {
    @NonNull
    @Override
    public String getType() {
        return "MapView";
    }

    @Nullable
    @Override
    public String getParentType() {
        return "FrameLayout";
    }

    @NonNull
    @Override
    public ProteusView createView(@NonNull ProteusContext context, @NonNull Layout layout,
                                  @NonNull ObjectValue data, @Nullable ViewGroup parent, int dataIndex) {
        return new ProteusMapView(context);
    }
    @Override
    protected void addAttributeProcessors() {
        addAttributeProcessor(Attributes.MapView.onMapReady, new EventProcessor<V>() {
            @Override
            public void setOnEventListener(final V view, final Value value) {
                view.getMapAsync(googleMap -> trigger(Attributes.MapView.onMapReady, value, view));
            }
        });

        addAttributeProcessor(Attributes.MapView.onLocationChanged, new EventProcessor<V>() {
            @Override
            public void setOnEventListener(final V view, final Value value) {
                view.getMapAsync(googleMap -> {
                    Fragment fragment = null;
                    try {
                        fragment = FragmentManager.findFragment(view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    view.registerGoogleApiClient(googleMap, fragment);
                    googleMap.setOnCameraIdleListener(() -> {
                        LatLng latLng = googleMap.getCameraPosition().target;
                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(latLng));
                        double latitude = latLng.latitude;
                        double longitude = latLng.longitude;
                        ObjectValue objectValue = value.getAsObject();
                        objectValue.addProperty(Keys.LATITUDE,latitude);
                        objectValue.addProperty(Keys.LONGITUDE,longitude);
                        trigger(Attributes.MapView.onLocationChanged, value, view);
                    });
                });
            }
        });
    }
}
