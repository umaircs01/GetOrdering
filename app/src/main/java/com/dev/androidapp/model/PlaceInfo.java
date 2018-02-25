package com.dev.androidapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Experiments on 30-Mar-17.
 */

public class PlaceInfo implements Parcelable {
    private android.location.Location location;


    public android.location.Location getLocation() {
        return location;
    }

    public void setLocation(android.location.Location location) {
        this.location = location;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.location, flags);
    }

    public PlaceInfo() {
    }

    protected PlaceInfo(Parcel in) {
        this.location = in.readParcelable(android.location.Location.class.getClassLoader());
    }

    public static final Creator<PlaceInfo> CREATOR = new Creator<PlaceInfo>() {
        @Override
        public PlaceInfo createFromParcel(Parcel source) {
            return new PlaceInfo(source);
        }

        @Override
        public PlaceInfo[] newArray(int size) {
            return new PlaceInfo[size];
        }
    };
}
