package com.experiments.restaurantapp.view.adapters;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.experiments.restaurantapp.view.listener.ItemClickListener;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Author : Experiments
 * Date : 08-May-16
 * <p>
 * base class for all recycler view adapters in app.<br/>
 * sets click and long click listeners if specified.
 *
 * @param <A> item model
 * @param <B> item view holder
 */
public abstract class BaseRecyclerAdapter<A, B extends BaseRecyclerAdapter.BaseViewHolder> extends RecyclerView.Adapter<B> {

    private final Context context;
    private final List<A> models;

    protected ItemClickListener<A> itemClickListener;
    private LayoutInflater inflater;

    public BaseRecyclerAdapter(Context context, List<A> models) {
        this.context = context;
        this.models = models;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public B onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(provideLayout(), parent, false);
        return generateViewHolder(view);
    }


    @CallSuper
    @Override
    public void onBindViewHolder(B holder, int position) {
        final A model = models.get(position);
        bind(holder, model, position);
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClicked(model);
                }
            });
        }
    }

    @LayoutRes
    protected abstract int provideLayout();

    protected abstract B generateViewHolder(View view);

    protected abstract void bind(B holder, A model, int position);

    protected LayoutInflater getInflater() {
        return inflater;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected Context getContext() {
        return context;
    }

    public List<A> getModels() {
        return models;
    }


    public void setItemClickListener(ItemClickListener<A> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    protected static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

} 