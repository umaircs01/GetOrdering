/*
 * Fenil Shah
 */

package com.dev.androidapp.helper;

import com.google.firebase.crash.FirebaseCrash;

/**
 * Fenil Shah
 */
public class ErrorReporter {

    private static ErrorReporter errorReporter;

    private ErrorReporter() {
    }

    public static synchronized ErrorReporter getInstance() {
        if (errorReporter == null) {
            errorReporter = new ErrorReporter();
        }
        return errorReporter;
    }

    public static synchronized void deallocate() {
        errorReporter = null;
    }

    public void reportCaughtException(Throwable t) {
        FirebaseCrash.report(t);
    }

    public void reportBehaviour(String message) {
        FirebaseCrash.log("Behaviour : " + message);
    }

    public void reportWarning(String message) {
        FirebaseCrash.log("Warning : " + message);
    }

    public void reportError(String message) {
        FirebaseCrash.log("Error : " + message);
    }
}
