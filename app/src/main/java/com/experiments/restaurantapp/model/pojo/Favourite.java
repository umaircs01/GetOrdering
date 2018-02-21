package com.experiments.restaurantapp.model.pojo;

import io.realm.RealmObject;

/**
 * Created by fahad on 2/21/2018.
 */

public class Favourite extends RealmObject {

    private String Id;
    private String favObject;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFavObject() {
        return favObject;
    }

    public void setFavObject(String favObject) {
        this.favObject = favObject;
    }
}
