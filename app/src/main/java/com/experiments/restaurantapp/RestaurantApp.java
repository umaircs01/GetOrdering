package com.experiments.restaurantapp;

import android.content.Context;
import android.util.Log;

import com.experiments.restaurantapp.helper.ErrorReporter;
import com.facebook.stetho.Stetho;
import com.orm.SugarApp;

import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by Experiments on 13-Mar-17.
 */
public class RestaurantApp extends SugarApp {

    private static RestaurantApp instance;
    public static Realm realm;

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        realm = Realm.getInstance(getBaseContext());
        Timber.Tree logObservingTree = new Timber.Tree() {
            @Override
            protected void log(int priority, String tag, String message, Throwable t) {
                if (priority < Log.WARN) return;
                reportErrorsAndWarnings(priority, tag, message, t);
            }
        };
        Timber.plant(BuildConfig.DEBUG ? new Timber.DebugTree() : logObservingTree);
        Stetho.initializeWithDefaults(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Timber.e("onLowMemory: ");
    }

    private void reportErrorsAndWarnings(int priority, String tag, String message, Throwable t) {
        ErrorReporter errorReporter = ErrorReporter.getInstance();
        switch (priority) {
            case Log.ERROR:
                if (t != null) {
                    errorReporter.reportCaughtException(t);
                } else {
                    errorReporter.reportError("tag = " + tag + "message = " + message);
                }
                break;
            case Log.WARN:
                if (t != null) {
                    errorReporter.reportCaughtException(t);
                } else {
                    errorReporter.reportWarning("tag = " + tag + "message = " + message);
                }
                break;
        }
    }

}
