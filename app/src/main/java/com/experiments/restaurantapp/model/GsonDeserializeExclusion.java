package com.experiments.restaurantapp.model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * Created by fahad on 2/21/2018.
 */

public class GsonDeserializeExclusion implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getDeclaredClass() == NumberFormat.class;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

}
