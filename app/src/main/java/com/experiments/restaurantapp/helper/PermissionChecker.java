/*
 * Fenil Shah
 */

package com.experiments.restaurantapp.helper;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Fenil Shah
 * Date : 03-Apr-16
 * <p>
 * handles permission checking for marshmallow and above
 */
public class PermissionChecker {

    /**
     * list of non granted permissions<br/>
     * should be cleared every time checking new permissions
     */
    private final List<String> revokedPermissions = new ArrayList<>();

    private PermissionChecker() {
        //avoiding direct instances. use factory method instead.
    }

    @NonNull
    public static PermissionChecker create() {
        return new PermissionChecker();
    }

    /**
     * checks permission and asks if not granted
     *
     * @param baseActivity       activity
     * @param requestCode        permission request code
     * @param permissionsToCheck permissions to check and ask
     * @return true if all asked permissionsToCheck are granted; false otherwise
     * @see #askPermissionsIfNotGranted(Fragment, int, String...) for asking permissions from fragment
     */
    public final boolean askPermissionsIfNotGranted(AppCompatActivity baseActivity, int requestCode, String... permissionsToCheck) {
        revokedPermissions.clear();
        for (String permission : permissionsToCheck) { //iterating through permission given in params
            if (ContextCompat.checkSelfPermission(baseActivity, permission) == android.support.v4.content.PermissionChecker.PERMISSION_GRANTED) { //if granted - skip iteration
                continue;
            }
            revokedPermissions.add(permission); //otherwise add it to list
        }
        if (revokedPermissions.size() > 0) { //request only non granted permissions
            ActivityCompat.requestPermissions(baseActivity, revokedPermissions.toArray(new String[revokedPermissions.size()]), requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * checks permission and asks if not granted - same as its activity variant but intended for fragments
     *
     * @param baseFragment       fragment
     * @param requestCode        permission request code
     * @param permissionsToCheck permissions to check and ask
     * @return true if all asked permissionsToCheck are granted; false otherwise
     */
    public final boolean askPermissionsIfNotGranted(Fragment baseFragment, int requestCode, String... permissionsToCheck) {
        revokedPermissions.clear();
        for (String permission : permissionsToCheck) { //iterating through permission given in params
            if (ContextCompat.checkSelfPermission(baseFragment.getContext(), permission) == android.support.v4.content.PermissionChecker.PERMISSION_GRANTED) { //if granted - skip iteration
                continue;
            }
            revokedPermissions.add(permission); //otherwise add it to list
        }
        if (revokedPermissions.size() > 0) { //request only non granted permissions
            baseFragment.requestPermissions(revokedPermissions.toArray(new String[revokedPermissions.size()]), requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * checks grant results from {@link ActivityCompat.OnRequestPermissionsResultCallback}.
     * <br> Shows snake bar with rationale message if permission denied.
     * <br> Asks again if user allow on showing rationale
     *
     * @param grantResults grant results from {@link ActivityCompat.OnRequestPermissionsResultCallback}
     * @return true if all permissions asked have been granted; false otherwise
     */
    public final boolean checkGrantResults(int[] grantResults) {
        //initializing granted flag to false is mandatory since array of grant results can be empty
        boolean allPermissionGranted = false;

        for (int grantResult : grantResults) { //iterating through grant results
            if (grantResult != android.support.v4.content.PermissionChecker.PERMISSION_GRANTED) { //if granted - skip iteration
                allPermissionGranted = false; //otherwise break loop and return false
                break;
            } else {
                //if granted, don't forgot to dismiss any previous snake bar showing for rationale
                allPermissionGranted = true;
            }
        }
        return allPermissionGranted;
    }
}