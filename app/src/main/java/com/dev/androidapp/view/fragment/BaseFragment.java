package com.dev.androidapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Experiments on 19-Feb-17.
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(provideLayout(), container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        dispose();
        super.onDestroyView();
    }

    protected abstract void init();

    @LayoutRes
    protected abstract int provideLayout();

    protected abstract void dispose();

}
