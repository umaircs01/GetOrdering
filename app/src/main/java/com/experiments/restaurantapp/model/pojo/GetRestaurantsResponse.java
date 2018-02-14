package com.experiments.restaurantapp.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRestaurantsResponse {

    @SerializedName("success")
    @Expose
    private long success;
    @SerializedName("data")
    @Expose
    private List<RestaurantData> data = null;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public long getSuccess() {
        return success;
    }

    public void setSuccess(long success) {
        this.success = success;
    }

    public List<RestaurantData> getData() {
        return data;
    }

    public void setData(List<RestaurantData> data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}