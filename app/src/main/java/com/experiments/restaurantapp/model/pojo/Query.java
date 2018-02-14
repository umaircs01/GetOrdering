package com.experiments.restaurantapp.model.pojo;

import com.orm.SugarRecord;

/**
 * Created by Experiments on 11-Apr-17.
 */

public class Query extends SugarRecord{

    private  long requestId;
    private  String name;

    public Query() {
    }

    public Query(long requestId, String name) {
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
