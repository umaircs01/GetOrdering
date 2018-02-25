package com.dev.androidapp.model.pojo;

import com.orm.SugarRecord;

/**
 * Created by Experiments on 11-Apr-17.
 */

public class Category extends SugarRecord {

    private  long requestId;
    private  String name;

    public Category() {
    }

    public Category(long requestId, String name) {
        this.requestId = requestId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
