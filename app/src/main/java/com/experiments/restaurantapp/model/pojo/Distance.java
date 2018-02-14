package com.experiments.restaurantapp.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Distance extends SugarRecord implements Parcelable {

    private long searchRequestId;
    @SerializedName("more_than")
    @Expose
    private long more_than_fake = 0;
    @SerializedName("less_than")
    @Expose
    private long lessThan;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;

    private long moreThan;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getSearchRequestId() {
        return searchRequestId;
    }

    public void setSearchRequestId(long searchRequestId) {
        this.searchRequestId = searchRequestId;
    }

    public long getMoreThan() {
        return moreThan;
    }

    public void setMoreThan(long moreThan) {
        this.moreThan = moreThan;
    }

    public long getLessThan() {
        return lessThan;
    }

    public void setLessThan(long lessThan) {
        this.lessThan = lessThan;
    }

    public Distance() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.searchRequestId);
        dest.writeLong(this.more_than_fake);
        dest.writeLong(this.lessThan);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeLong(this.moreThan);
    }

    protected Distance(Parcel in) {
        this.searchRequestId = in.readLong();
        this.more_than_fake = in.readLong();
        this.lessThan = in.readLong();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.moreThan = in.readLong();
    }

    public static final Creator<Distance> CREATOR = new Creator<Distance>() {
        @Override
        public Distance createFromParcel(Parcel source) {
            return new Distance(source);
        }

        @Override
        public Distance[] newArray(int size) {
            return new Distance[size];
        }
    };
}