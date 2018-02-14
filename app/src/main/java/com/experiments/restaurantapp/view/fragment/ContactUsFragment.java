package com.experiments.restaurantapp.view.fragment;

import com.experiments.restaurantapp.R;

/**
 * Created by Experiments on 13-Apr-17.
 */

public class ContactUsFragment extends BaseFragment {

    public static ContactUsFragment newInstance() {
        ContactUsFragment fragment = new ContactUsFragment();
        return fragment;
    }

    @Override
    protected void init() {

    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void dispose() {

    }
}
