package com.dev.androidapp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import com.dev.androidapp.RestaurantApp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Fenil on 21-Nov-16.
 */

public class Utils {

    private Utils() {
        //avoid objects
    }

    public static final String DATE_FORMAT = "MMMM dd, yyyy | hh:mm a";

    public static String generateRandomId() {
        String uniqueId = UUID.randomUUID().toString();
        return uniqueId.replaceAll("-", "");
    }

    public static String getCurrentTime() {
        long mills = System.currentTimeMillis();
        Date date = new Date(mills);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }


    public static int getAppVersionCode() {
        PackageInfo packageInfo = getAppPackageInfo();
        return packageInfo != null ? packageInfo.versionCode : 0;
    }

    public static String getAppVersionName() {
        PackageInfo packageInfo = getAppPackageInfo();
        return packageInfo != null ? packageInfo.versionName : "";
    }

    @Nullable
    private static PackageInfo getAppPackageInfo() {
        Context context = RestaurantApp.getInstance();
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
