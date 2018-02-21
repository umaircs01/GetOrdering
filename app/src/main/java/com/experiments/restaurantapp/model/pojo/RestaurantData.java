package com.experiments.restaurantapp.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

import java.text.NumberFormat;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Experiments on 05-Apr-17.
 */
@Table
public class RestaurantData {

    @Ignore
    private transient final NumberFormat numberFormat;

    @SerializedName("id")
    @Expose
    private String restaurantId;

    @SerializedName("trading_name")
    @Expose
    private String tradingName;

    @SerializedName("food_type")
    @Expose
    private String foodType;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("contact_info")
    @Expose
    private ContactInfo contactInfo;

    @SerializedName("work_info")
    @Expose
    private WorkInfo workInfo;

    @SerializedName("business_type")
    @Expose
    private List<String> businessType = null;

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("categories")
    @Expose
    private List<String> categories = null;

    @SerializedName("meals")
    @Expose
    private List<String> meals = null;

    @SerializedName("menu_url")
    @Expose
    private String menuUrl;

    @SerializedName("Views")
    @Expose
    private int views;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    private float distance;
    private boolean isFavourite;

    public RestaurantData() {
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(1);
        isFavourite = false;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public float getDistance() {
        return distance;
    }

    public String getFormattedDistance() {
        return numberFormat.format(distance);
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }


    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public WorkInfo getWorkInfo() {
        return workInfo;
    }

    public void setWorkInfo(WorkInfo workInfo) {
        this.workInfo = workInfo;
    }

    public List<String> getBusinessType() {
        return businessType;
    }

    public void setBusinessType(List<String> businessType) {
        this.businessType = businessType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getMeals() {
        return meals;
    }

    public void setMeals(List<String> meals) {
        this.meals = meals;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public int getViews() {
        return views;
    }
}
