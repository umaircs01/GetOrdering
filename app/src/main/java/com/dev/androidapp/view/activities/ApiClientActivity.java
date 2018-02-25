/*
 * Fenil Shah
 */

package com.dev.androidapp.view.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.dev.androidapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import timber.log.Timber;

/**
 * Fenil Shah
 */
public abstract class ApiClientActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;

    @Override
    @CallSuper
    protected void init() {
        if (googleApiClient == null) {
            GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this);
            addApis(builder);
            googleApiClient = builder.build();
        }
    }

    @NonNull
    protected GoogleApiClient apiClient() {
        return googleApiClient;
    }

    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Timber.d("onConnectionSuspended() called with: cause = [" + cause + "]");
        switch (cause) {
            case CAUSE_NETWORK_LOST:
                showMessage(getString(R.string.connect_internet));
                break;
            case CAUSE_SERVICE_DISCONNECTED:
                showMessage(getString(R.string.play_service_disconnected));
                break;
            default:
                showMessage(getString(R.string.unknown_error));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.d("onConnectionFailed() called with: connectionResult = [" + connectionResult + "]");
        showMessage(getString(R.string.play_service_failed));
    }

    protected abstract void addApis(GoogleApiClient.Builder builder);

    @Override
    @CallSuper
    protected void dispose() {
        if (!googleApiClient.isConnected()) return;
        if (googleApiClient.isConnectionCallbacksRegistered(this)) {
            googleApiClient.unregisterConnectionCallbacks(this);
        }
        if (googleApiClient.isConnectionFailedListenerRegistered(this)) {
            googleApiClient.unregisterConnectionFailedListener(this);
        }
    }
}
