package com.dev.androidapp.view.fragment;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dev.androidapp.R;
import com.dev.androidapp.model.pojo.SearchRequest;
import com.dev.androidapp.view.activities.BaseActivity;
import com.dev.androidapp.view.adapters.SavedSearchAdapter;
import com.dev.androidapp.view.listener.ItemClickListener;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by Experiments on 10-Apr-17.
 */

public class SavedSearchFragment extends BaseFragment implements ItemClickListener<SearchRequest>, SavedSearchAdapter.DeleteListener {
    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;
    @BindView(R.id.tvNoResults)
    AppCompatTextView tvNoResults;
    private SavedSearchAdapter adapter;

    public static SavedSearchFragment newInstance() {
        SavedSearchFragment fragment = new SavedSearchFragment();
        return fragment;
    }
    @Override
    protected void init() {
        final List<SearchRequest> searchRequests = new ArrayList<>();
        List<SearchRequest> dbData = SearchRequest.listAll(SearchRequest.class);
        searchRequests.addAll(dbData);
        adapter = new SavedSearchAdapter(getContext(), searchRequests);
        adapter.setItemClickListener(this);
        adapter.setDeleteListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvSearch.setLayoutManager(layoutManager);
        rvSearch.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvSearch.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                setVisibilityOfNoResults();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setVisibilityOfNoResults();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                setVisibilityOfNoResults();
            }
        });
        setVisibilityOfNoResults();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_saved_search;
    }

    @Override
    protected void dispose() {
        adapter = null;
    }


    private void setVisibilityOfNoResults() {
        if (adapter.getModels().isEmpty()) {
            tvNoResults.setVisibility(View.VISIBLE);
        } else {
            tvNoResults.setVisibility(GONE);
        }
    }

    @Override
    public void onItemClicked(SearchRequest model) {
        HomeFragment homeFragment = HomeFragment.newInstance(model);
        ((BaseActivity) getActivity()).addReplaceFragment(R.id.container_home, homeFragment, true, true);
    }

    @Override
    public void onDelete(SearchRequest model) {
        boolean deleted = SugarRecord.delete(model);
        if (deleted) {
            List<SearchRequest> models = adapter.getModels();
            int indexOf = models.indexOf(model);
            models.remove(indexOf);
            adapter.notifyItemRemoved(indexOf);
        }
    }
}
