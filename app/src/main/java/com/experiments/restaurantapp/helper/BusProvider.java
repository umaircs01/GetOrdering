package com.experiments.restaurantapp.helper;

import com.squareup.otto.Bus;

/**
 * Created by Experiments on 05-Mar-17.
 */
public class BusProvider {
    private static BusProvider ourInstance = new BusProvider();
    private final Bus bus;

    private BusProvider() {
        bus = new Bus();
    }

    public static BusProvider getInstance() {
        return ourInstance;
    }

    public Bus getBus() {
        return bus;
    }
}
