package com.proteus.map.parser;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.ViewTypeParser;
import com.flipkart.android.proteus.processor.EventProcessor;
import com.flipkart.android.proteus.processor.NumberAttributeProcessor;
import com.flipkart.android.proteus.toolbox.Attributes;
import com.flipkart.android.proteus.value.Array;
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

        addAttributeProcessor(Attributes.MapView.zoomLevel, new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setZoomLevel(value.intValue());
            }
        });

        addAttributeProcessor(Attributes.MapView.interval, new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setInterval(value.intValue());
            }
        });

        addAttributeProcessor(Attributes.MapView.fastestInterval, new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setFastestInterval(value.intValue());
            }
        });

        addAttributeProcessor(Attributes.MapView.priority, new NumberAttributeProcessor<V>() {
            @Override
            public void setNumber(V view, @NonNull Number value) {
                view.setPriority(value.intValue());
            }
        });

        addAttributeProcessor(Attributes.MapView.onLocationChanged, new EventProcessor<V>() {
            @Override
            public void setOnEventListener(final V view, final Value value) {
                view.getMapAsync(googleMap -> {
                    view.registerGoogleApiClient(googleMap);
                    googleMap.setOnCameraIdleListener(() -> {
                        LatLng latLng = googleMap.getCameraPosition().target;
                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(latLng));
                        double latitude = latLng.latitude;
                        double longitude = latLng.longitude;
                        ObjectValue locationObjectValue = new ObjectValue();
                        if (value.isArray() && ((Array) value).size() > 0) {
                            ObjectValue objectValue = value.getAsArray().get(0).getAsObject();
                            locationObjectValue.addProperty(Keys.LATITUDE,latitude);
                            locationObjectValue.addProperty(Keys.LONGITUDE,longitude);
                            ObjectValue dataObjectValue = objectValue.getAsObject(Keys.DATA);
                            if (dataObjectValue != null) {
                                dataObjectValue.add(Keys.LOCATION, locationObjectValue);
                            } else {
                                objectValue.add(Keys.LOCATION, locationObjectValue);
                            }
                        } else if(value.isObject()){
                            ObjectValue objectValue = value.getAsObject();
                            locationObjectValue.addProperty(Keys.LATITUDE,latitude);
                            locationObjectValue.addProperty(Keys.LONGITUDE,longitude);
                            objectValue.add(Keys.LOCATION,locationObjectValue);
                        }
                        trigger(Attributes.MapView.onLocationChanged, value, view);
                    });
                });
            }
        });
    }
}
