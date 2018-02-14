package com.experiments.restaurantapp.view.listener;

/**
 * Created by Experiments on 25-Feb-17.
 *
 * @param <T> the type parameter
 */
public interface ItemClickListener<T> {
    /**
     * On item clicked.
     *
     * @param model the model
     */
    void onItemClicked(T model);
}
