package com.proteus.map.utils;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;

public class LocationUtil {
    private static LocationUtil locationUtil;
    public static final String TAG = "LocationUtil";
    private SettingsClient settingsClient;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationManager locationManager;

    private LocationUtil(){}
    public static LocationUtil getInstance(){
        if(locationUtil== null){
            locationUtil = new LocationUtil();
        }
        return locationUtil;
    }

    public void enableGps(Activity activity, OnGpsListener onGpsListener) {
        initClient(activity);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGpsListener.onGpsStatusChanged(true);
        } else {
            settingsClient.checkLocationSettings(locationSettingsRequest).addOnSuccessListener(activity, locationSettingsResponse -> {

            }).addOnFailureListener(activity, e -> {
                if (e instanceof ApiException) {
                    switch (((ApiException) e).getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException exception = (ResolvableApiException) e;
                                exception.startResolutionForResult(activity, 1);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.e(TAG, "Location settings are inadequate, and cannot be " +
                                    "fixed here. Fix in Settings.");
                            break;

                    }
                }
            });
        }
    }

    public void initClient(Activity activity) {
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        settingsClient = LocationServices.getSettingsClient(activity);
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(2 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
        builder.setAlwaysShow(true);
    }


   public interface OnGpsListener {
        void onGpsStatusChanged(boolean isGPSEnabled);
    }
}
