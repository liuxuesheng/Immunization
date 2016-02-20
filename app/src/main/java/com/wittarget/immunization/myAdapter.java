package com.wittarget.immunization;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wittarget.immunization.mainPageFragments.immuchartFragment;
import com.wittarget.immunization.mainPageFragments.mapFragment;
import com.wittarget.immunization.mainPageFragments.newsFragment;
import com.wittarget.immunization.mainPageFragments.profileFragment;

public class myAdapter extends FragmentPagerAdapter {
    static final int NUM_ITEMS = 4;

    public myAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return newsFragment.newInstance("");
            case 1:
                return immuchartFragment.newInstance("");
            case 2:
                return mapFragment.newInstance("");
            case 3:
                return profileFragment.newInstance("");
            default:
                return null;
        }
    }
}
