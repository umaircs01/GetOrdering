package com.dev.androidapp.view.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.dev.androidapp.R;
import com.dev.androidapp.model.pojo.SearchRequest;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Experiments on 10-Apr-17.
 */

public class SavedSearchAdapter extends BaseRecyclerAdapter<SearchRequest,SavedSearchAdapter.ViewHolder>{

    @Nullable
    private DeleteListener deleteListener;

    public SavedSearchAdapter(Context context, List<SearchRequest> models) {
        super(context, models);
    }

    @Override
    protected int provideLayout() {
        return R.layout.row_search_history;
    }

    @Override
    protected ViewHolder generateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bind(ViewHolder holder, final SearchRequest model, int position) {
        holder.tvTime.setText(model.getFormattedTime());
        holder.tvSearchName.setText(model.getSearchName());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener != null) {
                    deleteListener.onDelete(model);
                }
            }
        });
    }

    public void setDeleteListener(@Nullable DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public interface DeleteListener {
        void onDelete(SearchRequest request);
    }

    static class ViewHolder extends BaseRecyclerAdapter.BaseViewHolder{
        @BindView(R.id.tvTime)
        AppCompatTextView tvTime;
        @BindView(R.id.tvSearchName)
        AppCompatTextView tvSearchName;
        @BindView(R.id.ivDelete)
        AppCompatImageView ivDelete;

         ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
