package com.wittarget.immunization;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wittarget.immunization.mainPageFragments.RecordsFragment;
import com.wittarget.immunization.mainPageFragments.MapFragment;
import com.wittarget.immunization.mainPageFragments.NewsFragment;
import com.wittarget.immunization.mainPageFragments.ProfileFragment;

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
                return NewsFragment.newInstance("News");
            case 1:
                return RecordsFragment.newInstance("Records");
            case 2:
                return MapFragment.newInstance("Map");
            case 3:
                return ProfileFragment.newInstance("Profile");
            default:
                return null;
        }
    }
}
