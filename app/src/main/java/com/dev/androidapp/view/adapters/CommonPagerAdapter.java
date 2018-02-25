package com.dev.androidapp.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Author : Experiments
 * Date : 09-Apr-16
 * <p>
 * common class for all view pager adapters
 * there are two overloaded constructors. one is without titles and other is with titles<br/>
 * if titles are given; it will call {@link FragmentPagerAdapter#getPageTitle(int)} to set title
 */
public class CommonPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;
    private List<String> titles;


    public CommonPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    public CommonPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
        if (fragments.size() != titles.size()) {
            throw new IllegalArgumentException("no. of titles and no. of fragments must be same in size");
        }
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles != null ? titles.get(position) : null;
    }

    public List<Fragment> getItems() {
        return fragments;
    }
}
