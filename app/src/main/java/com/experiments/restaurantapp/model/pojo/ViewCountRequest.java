package com.experiments.restaurantapp.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Experiments on 13-Apr-17.
 */

public class ViewCountRequest {
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    public ViewCountRequest(String restaurantId) {
        this.restaurantId = restaurantId;
    }


    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
