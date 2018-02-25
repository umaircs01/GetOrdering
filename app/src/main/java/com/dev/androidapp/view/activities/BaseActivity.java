package com.dev.androidapp.view.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.dev.androidapp.helper.BusProvider;
import com.dev.androidapp.model.events.NetworkChangedEvent;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;


/**
 * Created by Fenil on 21-Nov-16.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * simple progress dialog
     */
    private ProgressDialog progressDialog;
    private final Bus bus = BusProvider.getInstance().getBus();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private boolean wasNetworkDisconnected;
    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isInternetAvailable(context)) {
                if (!wasNetworkDisconnected) return;
                bus.post(new NetworkChangedEvent(true));
                wasNetworkDisconnected = false;
            } else {
                if (wasNetworkDisconnected) return;
                bus.post(new NetworkChangedEvent(false));
                wasNetworkDisconnected = true;
            }
        }
    };

    /**
     * Hide key board.
     */
    public final void hideKeyBoard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayout());
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        init();
    }
    /**
     * to show simple progress dialog with message
     *
     * @param message message string
     */
    public void showProgress(@Nullable String message) {
        dismissProgress();
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    /**
     * to dismiss progress dialog
     */
    public void dismissProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
        wasNetworkDisconnected = false;
        IntentFilter connectivityFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, connectivityFilter);
    }

    @CallSuper
    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
        unregisterReceiver(networkReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgress();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        mainThreadHandler.removeCallbacksAndMessages(null);
        dispose();
        super.onDestroy();
    }


    protected final boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
    }


    public final void showMessage(String error) {
        Snackbar.make(
                findViewById(android.R.id.content),
                error,
                Snackbar.LENGTH_SHORT
        ).show();
    }

    public final void addReplaceFragment(@IdRes int container, Fragment fragment, boolean addFragment, boolean addToBackStack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction();
        if (addFragment) {
            transaction.add(container, fragment, fragment.getClass().getSimpleName());
        } else {
            transaction.replace(container, fragment, fragment.getClass().getSimpleName());
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


    protected abstract void init();

    @LayoutRes
    protected abstract int provideLayout();

    protected abstract void dispose();

    public Bus getBus() {
        return bus;
    }

    public Handler getMainThreadHandler() {
        return mainThreadHandler;
    }
}
