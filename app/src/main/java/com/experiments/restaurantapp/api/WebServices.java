package com.experiments.restaurantapp.api;

import com.experiments.restaurantapp.model.pojo.GetRestaurantsRequest;
import com.experiments.restaurantapp.model.pojo.GetRestaurantsResponse;
import com.experiments.restaurantapp.model.pojo.SearchRequest;
import com.experiments.restaurantapp.model.pojo.ViewCountRequest;
import com.experiments.restaurantapp.model.pojo.ViewCountResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created on 19-Mar-17.
 * <p>
 * interface for all apis
 */
public interface WebServices {

    @POST("getRestaurants")
    Call<GetRestaurantsResponse> getRestaurants(
            @Body GetRestaurantsRequest restaurantsRequest
    );

    @POST("searchRestaurants")
    Call<GetRestaurantsResponse> searchRestaurants(
            @Body SearchRequest searchRequest
    );

    @POST("increaseViewCount")
    Call<ViewCountResponse> increaseViewCount(
            @Body ViewCountRequest viewCountRequest
    );
}
