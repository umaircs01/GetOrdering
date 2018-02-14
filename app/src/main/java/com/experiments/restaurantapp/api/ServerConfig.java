package com.experiments.restaurantapp.api;

/**
 * Created on 19-Mar-17.
 * <p>
 * configuration class for server urls
 */
public interface ServerConfig {

    /**
     * The constant TIMEOUT for http timeouts in seconds.
     */
    int TIMEOUT = 100;

    String BASE_URL = "http://nuroworks.com/takki/nuro/api/";
    long SUCCESS_RESPONSE = 1;
}
