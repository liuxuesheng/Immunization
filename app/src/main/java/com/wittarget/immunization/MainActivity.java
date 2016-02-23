package com.wittarget.immunization;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private myAdapter mAdapter;
    private myViewPager mPager;
    private int lastEnabled = 0;
    private TextView lastEnabledText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set action bar to center
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        fragmentManager = getSupportFragmentManager();

        // Tab image view
        final TextView tabNews = (TextView) findViewById(R.id.main_news_iv);
        final TextView tabRecords = (TextView) findViewById(R.id.main_records_iv);
        final TextView tabMap = (TextView) findViewById(R.id.main_map_iv);
        final TextView tabProfile = (TextView) findViewById(R.id.main_profile_iv);

        //Setup initial configuration
        tabNews.setTextColor(Color.WHITE);
        lastEnabledText = tabNews;

        tabNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLastEnabled(0);
                tabNews.setTextColor(Color.WHITE);
                mPager.setCurrentItem(0);
                lastEnabled = 0;
                lastEnabledText = tabNews;
            }
        });

        tabRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLastEnabled(1);
                tabRecords.setTextColor(Color.WHITE);
                mPager.setCurrentItem(1);
                lastEnabled = 1;
                lastEnabledText = tabRecords;
            }
        });

        tabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLastEnabled(2);
                tabMap.setTextColor(Color.WHITE);
                mPager.setCurrentItem(2);
                lastEnabled = 2;
                lastEnabledText = tabMap;
            }
        });

        tabProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLastEnabled(3);
                tabProfile.setTextColor(Color.WHITE);
                mPager.setCurrentItem(3);
                lastEnabled = 3;
                lastEnabledText = tabProfile;
            }
        });

        //Create pager
        mAdapter = new myAdapter(fragmentManager);
        mPager = (myViewPager) findViewById(R.id.main_pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(4);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        resetLastEnabled(0);
                        tabNews.setTextColor(Color.WHITE);
                        lastEnabled = 0;
                        lastEnabledText = tabNews;
                        break;
                    case 1:
                        resetLastEnabled(1);
                        tabRecords.setTextColor(Color.WHITE);
                        lastEnabled = 1;
                        lastEnabledText = tabRecords;
                        break;
                    case 2:
                        resetLastEnabled(2);
                        tabMap.setTextColor(Color.WHITE);
                        lastEnabled = 2;
                        lastEnabledText = tabMap;
                        break;
                    case 3:
                        resetLastEnabled(3);
                        tabProfile.setTextColor(Color.WHITE);
                        lastEnabled = 3;
                        lastEnabledText = tabProfile;
                        break;
                    default:
                        lastEnabled = 0;
                        lastEnabledText = tabNews;
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void resetLastEnabled(int index) {
        lastEnabledText.setTextColor(Color.BLACK);
    }
}
