/*
 * Fenil Shah
 */

package com.experiments.restaurantapp.view.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.experiments.restaurantapp.R;
import com.experiments.restaurantapp.helper.Config;
import com.experiments.restaurantapp.helper.PermissionChecker;
import com.experiments.restaurantapp.model.PlaceInfo;
import com.experiments.restaurantapp.util.Constants;
import com.experiments.restaurantapp.util.PrefUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;

import timber.log.Timber;

/**
 * Fenil Shah
 */
public abstract class PlaceDetectorActivity extends ApiClientActivity implements LocationListener {

    public static final int REQUEST_CHECK_SETTINGS = 222;
    public static final int REQUEST_PERMISSION_LOCATION = 901;
    private static final long DEFAULT_UPDATE_INTERVAL = 30 * 1000;
    private static final int DEFAULT_ACCURACY = LocationRequest.PRIORITY_HIGH_ACCURACY;
    private static final long DEFAULT_AREA_FOR_PLACE_UPDATE = 1000;
    protected final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    protected PermissionChecker permissionChecker;
    public PlaceInfo currentPlace;
    @Nullable
    private Location tempLocation;
    private ResultCallback<LocationSettingsResult> locationSettingsResultCallback;
    private LocationRequest locationRequest;

    private int updateCounter;

    @Override
    protected void init() {
        super.init();
        updateCounter = 0;
        currentPlace = new PlaceInfo();
        permissionChecker = PermissionChecker.create();
    }


    @Override
    protected void dispose() {
        super.dispose();
        locationSettingsResultCallback = null;
    }

    @Override
    protected void addApis(GoogleApiClient.Builder builder) {
        builder.addApi(LocationServices.API);
        builder.addApi(Places.GEO_DATA_API);
        builder.addApi(Places.PLACE_DETECTION_API);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Timber.d("onConnected() called with: bundle = [" + bundle + "]");

        boolean permissionsIfNotGranted = permissionChecker.askPermissionsIfNotGranted(this, REQUEST_PERMISSION_LOCATION, LOCATION_PERMISSIONS);
        if (!permissionsIfNotGranted) return;
        whenApiClientConnected();
    }

    protected void whenApiClientConnected() {
        createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(apiClient(),
                        builder.build());
        locationSettingsResultCallback = new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                onLocationSettingsResult(status);
            }
        };
        result.setResultCallback(locationSettingsResultCallback);
    }

    private void onLocationSettingsResult(Status status) {
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(
                            PlaceDetectorActivity.this,
                            REQUEST_CHECK_SETTINGS
                    );
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                    showMessage(getString(R.string.unable_to_turn_on_location));
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                showMessage(getString(R.string.unable_to_turn_on_location));
                break;
        }
    }


    @SuppressWarnings("MissingPermission")
    protected void startLocationUpdates() {
        if (!apiClient().isConnected()) return;
        LocationServices.FusedLocationApi.requestLocationUpdates(
                apiClient(),
                locationRequest,
                this
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                switch (requestCode) {
                    case REQUEST_CHECK_SETTINGS:
                        startLocationUpdates();
                        break;
                }
                break;
            case Activity.RESULT_CANCELED:
                onUserDenied();
                break;
        }
    }

    protected abstract void onUserDenied();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                boolean granted = permissionChecker.checkGrantResults(grantResults);
                if (granted) {
                    whenApiClientConnected();
                } else {
                    showMessage(getString(R.string.location_permission_is_required));
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Timber.d("onLocationChanged() called with: location = [" + location + "]");
        if (location == null) return;
        long minAreaForUpdate = PrefUtils.getLongPreference(this, Config.Preferences.MIN_AREA_FOR_PLACE_UPDATE, DEFAULT_AREA_FOR_PLACE_UPDATE);
        if (tempLocation == null || Math.abs(tempLocation.distanceTo(location)) >= minAreaForUpdate) {
            currentPlace.setLocation(location);
        }
        tempLocation = location;
        updateCounter++;
        if (updateCounter == 1) {
           onFirstTimeLocationGot(currentPlace);
        }
    }

    protected abstract void onFirstTimeLocationGot(PlaceInfo currentPlace);

    protected void createLocationRequest() {
        if (locationRequest == null) {
            locationRequest = new LocationRequest();
        }

        long updateInterval = PrefUtils.getLongPreference(this, Config.Preferences.LOCATION_UPDATE_INTERVAL, DEFAULT_UPDATE_INTERVAL);
        locationRequest.setInterval(updateInterval);
        locationRequest.setFastestInterval(updateInterval / 2);

        int accuracy = PrefUtils.getIntegerPreference(this, Config.Preferences.LOCATION_ACCURACY, DEFAULT_ACCURACY);
        switch (accuracy) {
            case Constants.LocationConfig.BALANCED_ACCURACY:
                locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                break;
            case Constants.LocationConfig.LOW_ACCURACY:
                locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
                break;
            default:
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                break;
        }

    }

/*    @SuppressWarnings("MissingPermission")
    protected void fetchCurrentPlace() {
        Timber.d("fetchCurrentPlace() called");

        if (!apiClient().isConnected()) return;
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(apiClient(), null);
        placeDetectionResultCallback = new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoodBuffer) {
                onPlaceDetected(placeLikelihoodBuffer);
                placeLikelihoodBuffer.release();
            }
        };
        result.setResultCallback(placeDetectionResultCallback);
    }*/

/*    private void onPlaceDetected(@NonNull PlaceLikelihoodBuffer placeLikelihoodBuffer) {
        Timber.d("onPlaceDetected() called with: placeLikelihoodBuffer = [" + placeLikelihoodBuffer + "]");
        if (placeLikelihoodBuffer.getCount() == 0) return;
        Place placeLikelihood = placeLikelihoodBuffer.get(0).getPlace();
        LatLng latLng = placeLikelihood.getLatLng();
        currentPlace.setLatitude(latLng.latitude);
        currentPlace.setLongitude(latLng.longitude);
        onPlaceUpdated();
    }*/

    @NonNull
    protected PlaceInfo currentPlace() {
        return currentPlace;
    }

    protected abstract void onPlaceUpdated();
}
