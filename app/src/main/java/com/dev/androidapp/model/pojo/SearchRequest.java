package com.dev.androidapp.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

public class SearchRequest extends SugarRecord implements Parcelable {

    @Ignore
    @SerializedName("username")
    @Expose
    private String username;
    @Ignore
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("queries")
    @Expose
    @Ignore
    private List<String> queries = null;
    @SerializedName("distance")
    @Expose
    private Distance distance;
    @SerializedName("address")
    @Expose
    @Ignore
    private List<String> address = null;
    @SerializedName("categories")
    @Expose
    @Ignore
    private List<String> categories = null;
    @SerializedName("business_types")
    @Expose
    @Ignore
    private List<String> businessTypes = null;
    @SerializedName("is_open")
    @Expose
    private boolean open;
    @SerializedName("take_away")
    @Expose
    private boolean takeAway;

    private long timestamp;

    private String formattedTime;
    private String searchName;


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getQueries() {
        return queries;
    }

    public void setQueries(List<String> queries) {
        this.queries = queries;
    }

    @Nullable
    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(List<String> businessTypes) {
        this.businessTypes = businessTypes;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public void setTakeAway(boolean takeAway) {
        this.takeAway = takeAway;
    }

    public boolean isTakeAway() {
        return takeAway;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchName() {
        return searchName;
    }

    public SearchRequest() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeStringList(this.queries);
        dest.writeParcelable(this.distance, flags);
        dest.writeStringList(this.address);
        dest.writeStringList(this.categories);
        dest.writeStringList(this.businessTypes);
        dest.writeByte(this.open ? (byte) 1 : (byte) 0);
        dest.writeByte(this.takeAway ? (byte) 1 : (byte) 0);
        dest.writeLong(this.timestamp);
        dest.writeString(this.formattedTime);
        dest.writeString(this.searchName);
    }

    protected SearchRequest(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.queries = in.createStringArrayList();
        this.distance = in.readParcelable(Distance.class.getClassLoader());
        this.address = in.createStringArrayList();
        this.categories = in.createStringArrayList();
        this.businessTypes = in.createStringArrayList();
        this.open = in.readByte() != 0;
        this.takeAway = in.readByte() != 0;
        this.timestamp = in.readLong();
        this.formattedTime = in.readString();
        this.searchName = in.readString();
    }

    public static final Creator<SearchRequest> CREATOR = new Creator<SearchRequest>() {
        @Override
        public SearchRequest createFromParcel(Parcel source) {
            return new SearchRequest(source);
        }

        @Override
        public SearchRequest[] newArray(int size) {
            return new SearchRequest[size];
        }
    };
}