package com.experiments.restaurantapp.util;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Experiments on 26-Feb-17.
 */

public class DiffUtilCallback<T extends Identity> extends DiffUtil.Callback {

    private final List<T> oldList;
    private final List<T> newList;

    public DiffUtilCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getIdentifier().equals(newList.get(newItemPosition).getIdentifier());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

}
