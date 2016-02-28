package com.wittarget.immunization.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.wittarget.immunization.news.NewsImageFragment;

public class NewsImageAdapter extends FragmentPagerAdapter {
    static final int NUM_ITEMS = 3;

    public NewsImageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("asd8sd7sd ", "position news: " + position);
        return NewsImageFragment.newInstance(position);
    }}
