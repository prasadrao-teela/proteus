package com.proteus.map.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.flipkart.android.proteus.ProteusView;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.proteus.map.client.ProteusGoogleApiClient;

public class ProteusMapView extends MapView implements ProteusView, LifecycleObserver{
    private Manager manager;
    private int zoomLevel = 15;
    private int interval = 1000;
    private int fastestInterval = 1000;
    private int priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
    private ProteusGoogleApiClient proteusGoogleApiClient;
    public ProteusMapView(Context context) {
        super(context);
        this.onCreate(null);
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
        return this;
    }

    public void setZoomLevel(int zoomLevel){
        this.zoomLevel = zoomLevel;
    }

    public void setInterval(int interval){
        this.interval = interval;
    }

    public void setFastestInterval(int fastestInterval) {
        this.fastestInterval = fastestInterval;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getZoomLevel(){
        return zoomLevel;
    }

    public int getInterval() {
        return interval;
    }

    public int getFastestInterval() {
        return fastestInterval;
    }

    public int getPriority() {
        return priority;
    }

    @SuppressLint("MissingPermission")
    public void registerGoogleApiClient(GoogleMap googleMap){
        if(googleMap != null){
            Fragment fragment = null;
            try {
                fragment = FragmentManager.findFragment(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(fragment  != null){
                fragment.getLifecycle().addObserver(this);
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            proteusGoogleApiClient = new ProteusGoogleApiClient(getContext(),this, googleMap);
            proteusGoogleApiClient.buildGoogleApiClient();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void mapOnResume() {
            super.onResume();
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void mapOnStart() {
            super.onStart();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void mapOnPause() {
            super.onPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void mapOnStop() {
            super.onStop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void mapOnDestroy() {
            super.onDestroy();
        if(proteusGoogleApiClient != null){
            proteusGoogleApiClient.disconnect();
        }
    }
}
