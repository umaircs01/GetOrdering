package com.dev.androidapp.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Experiments on 13-Apr-17.
 */

public class ViewCountResponse {

    @SerializedName("success")
    @Expose
    private long success;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public long getSuccess() {
        return success;
    }

    public void setSuccess(long success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
