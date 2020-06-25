package com.proteus.map.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.flipkart.android.proteus.ProteusView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.proteus.map.client.ProteusGoogleApiClient;

public class ProteusMapView extends MapView implements ProteusView, OnMapReadyCallback,LifecycleObserver{
    private Manager manager;
    private MapView mapView;
    private ProteusGoogleApiClient proteusGoogleApiClient;
    public ProteusMapView(Context context) {
        super(context);
        this.onCreate(null);
     //   (this).getMapAsync(this);
    }

    @Override
    public Manager getViewManager() {
        return manager;
    }

    @Override
    public void setViewManager(@NonNull Manager manager) {
        this.manager = manager;
    }

    @NonNull
    @Override
    public View getAsView() {
        mapView = this;
        return this;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*if(googleMap != null) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Fragment fragment = null;
            try {
                fragment = FragmentManager.findFragment(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(fragment  != null){
                new MapLifeCycleObserver(fragment.getLifecycle());
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            proteusGoogleApiClient = new ProteusGoogleApiClient(getContext(), googleMap);
            proteusGoogleApiClient.buildGoogleApiClient();
        }*/
    }

    @SuppressLint("MissingPermission")
    public void registerGoogleApiClient(GoogleMap googleMap, @Nullable Fragment fragment){
        if(googleMap != null){
            if(fragment  != null){
                new MapLifeCycleObserver(fragment.getLifecycle());
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            proteusGoogleApiClient = new ProteusGoogleApiClient(getContext(), googleMap);
            proteusGoogleApiClient.buildGoogleApiClient();
        }
    }

    public class MapLifeCycleObserver implements LifecycleObserver {

        public MapLifeCycleObserver(Lifecycle lifecycle){
            lifecycle.addObserver(this);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            if(mapView != null) {
                mapView.onResume();
            }
        }
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
            if(mapView != null) {
                mapView.onStart();
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
            if(mapView != null) {
                mapView.onPause();
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onStop() {
            if(mapView != null) {
                mapView.onStop();
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {
            if(mapView != null) {
                mapView.onDestroy();
            }
            if(proteusGoogleApiClient != null){
                proteusGoogleApiClient.disconnect();
            }
        }
    }
}
