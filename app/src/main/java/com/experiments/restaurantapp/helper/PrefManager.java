/*
 * Copyright (c) 2015. Droid Experiments Pvt. Ltd. All Rights Reserved.
 */

package com.experiments.restaurantapp.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Keep;

/**
 * Created by Android Experiments on 02-Sep05.
 * <p>
 * handy class to set/get all shared preferences
 */
@Keep
public class PrefManager {


    /**
     * Gets pref boolean
     *
     * @param context    the context
     * @param prefEntity the pref entity
     * @return the pref boolean
     */
    public static boolean getPrefBoolean(Context context, String prefEntity) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefEntity.PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(prefEntity, false);
    }

    /**
     * Gets long shared preference.
     *
     * @param context    the context
     * @param prefEntity the key
     * @return the pref long
     */
    public static long getPrefLong(Context context, String prefEntity) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefEntity.PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(prefEntity, 0);
    }

    /**
     * Gets pref float.
     *
     * @param context    the context
     * @param prefEntity the key
     * @return the pref float
     */
    public static double getPrefFloat(Context context, String prefEntity) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefEntity.PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(prefEntity, 0);
    }


    /**
     * Gets pref string.
     *
     * @param context    the context
     * @param prefEntity the key
     * @return the pref string
     */
    public static String getPrefString(Context context, String prefEntity) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefEntity.PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(prefEntity, null);
    }

    /**
     * Gets pref integer.
     *
     * @param context    the context
     * @param prefEntity the key
     * @return the pref integer
     */
    public static int getPrefInteger(Context context, String prefEntity) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefEntity.PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(prefEntity, 0);
    }


    /**
     * Sets boolean shared preference.
     *
     * @param context    the context
     * @param prefEntity the key
     * @param value      the value
     */
    public static void setPrefBoolean(Context context, String prefEntity, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefEntity.PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(prefEntity, value);
        editor.apply();
    }

    /**
     * Sets long shared preference.
     *
     * @param context    the context
     * @param prefEntity the key
     * @param value      the value
     */
    public static void setPrefLong(Context context, String prefEntity, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefEntity.PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(prefEntity, value);
        editor.apply();
    }


    /**
     * Sets float shared preference.
     *
     * @param context    the context
     * @param prefEntity the key
     * @param value      the value
     */
    public static void setPrefFloat(Context context, String prefEntity, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefEntity.PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(prefEntity, value);
        editor.apply();
    }

    /**
     * Sets pref string.
     *
     * @param context    the context
     * @param prefEntity the key
     * @param value      the value
     */
    public static void setPrefString(Context context, String prefEntity, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefEntity.PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefEntity, value);
        editor.apply();
    }

    /**
     * Sets  integer shared preference.
     *
     * @param context    the context
     * @param prefEntity the key
     * @param value      the value
     */
    public static void setPrefInteger(Context context, String prefEntity, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefEntity.PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(prefEntity, value);
        editor.apply();
    }
}
