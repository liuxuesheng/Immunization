package com.wittarget.immunization;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private myAdapter mAdapter;
    private myViewPager mPager;
    private int lastEnabled = 0;
    private TextView lastEnabledText = null;
    private TextView lastTextView = null;
    private ImageView lastImageView = null;
    private LinearLayout lastLayout = null;
    private int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set action bar to center
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);

        fragmentManager = getSupportFragmentManager();

        //Linear layout
        final LinearLayout ll0 = (LinearLayout) findViewById(R.id.main_news);
        final LinearLayout ll1 = (LinearLayout) findViewById(R.id.main_records);
        final LinearLayout ll2 = (LinearLayout) findViewById(R.id.main_map);
        final LinearLayout ll3 = (LinearLayout) findViewById(R.id.main_profile);

        //Tab Image view
        final ImageView imageButton0 = (ImageView) findViewById(R.id.goto_news_iv);
        final ImageView imageButton1 = (ImageView) findViewById(R.id.goto_records_iv);
        final ImageView imageButton2 = (ImageView) findViewById(R.id.goto_map_iv);
        final ImageView imageButton3 = (ImageView) findViewById(R.id.goto_profile_iv);

        // Tab image view
        final TextView textButton0 = (TextView) findViewById(R.id.main_news_iv);
        final TextView textButton1 = (TextView) findViewById(R.id.main_records_iv);
        final TextView textButton2 = (TextView) findViewById(R.id.main_map_iv);
        final TextView textButton3 = (TextView) findViewById(R.id.main_profile_iv);

        // Set last visited
        this.lastImageView = imageButton0;
        this.lastLayout = ll0;
        this.lastTextView = textButton0;

        //Setup initial configuration
        ll0.setBackground(new ColorDrawable(Color.rgb(30, 136, 229)));
        imageButton0.setImageResource(R.drawable.ic_home_white_24dp);
        textButton0.setTextColor(Color.WHITE);
        textButton1.setTextColor(Color.BLACK);
        textButton2.setTextColor(Color.BLACK);
        textButton3.setTextColor(Color.BLACK);

        ll0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetLayout();
                ResetLast(ll0, imageButton0, textButton0);
                mPager.setCurrentItem(0);
                ll0.setBackground(new ColorDrawable(Color.rgb(30, 136, 229)));
                imageButton0.setImageResource(R.drawable.ic_home_white_24dp);
                textButton0.setTextColor(Color.WHITE);
                setPageIndex(0);
            }
        });

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetLayout();
                ResetLast(ll1, imageButton1, textButton1);
                //mPager.setCurrentItem(1);
                ll1.setBackground(new ColorDrawable(Color.rgb(30, 136, 229)));
                imageButton1.setImageResource(R.drawable.ic_records_white_24dp);
                textButton1.setTextColor(Color.WHITE);
                setPageIndex(1);

                //create a map activity
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RecordsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetLayout();
                ResetLast(ll2, imageButton2, textButton2);
                //mPager.setCurrentItem(2);
                ll2.setBackground(new ColorDrawable(Color.rgb(30, 136, 229)));
                imageButton2.setImageResource(R.drawable.ic_map_white_24dp);
                textButton2.setTextColor(Color.WHITE);
                setPageIndex(2);

                //create a map activity
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MapsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetLayout();
                ResetLast(ll3, imageButton3, textButton3);
                //mPager.setCurrentItem(3);
                ll3.setBackground(new ColorDrawable(Color.rgb(30, 136, 229)));
                imageButton3.setImageResource(R.drawable.ic_profile_white_24dp);
                textButton3.setTextColor(Color.WHITE);
                setPageIndex(3);

                //create a login activity
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(intent);
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
                LinearLayout myll = null;
                ImageView myImageButton = null;
                TextView myTextButton = null;

                switch (position) {
                    case 0:
                        myll = ll0;
                        myImageButton = imageButton0;
                        myTextButton = textButton0;
                        break;
                    case 1:
                        myll = ll1;
                        myImageButton = imageButton1;
                        myTextButton = textButton1;
                        break;
                    case 2:
                        myll = ll2;
                        myImageButton = imageButton2;
                        myTextButton = textButton2;
                        break;
                    case 3:
                        myll = ll3;
                        myImageButton = imageButton3;
                        myTextButton = textButton3;
                        break;
                    default:
                        myll = ll0;
                        myImageButton = imageButton0;
                        myTextButton = textButton0;
                        break;
                }
                if (position != getPageIndex()) {
                    ResetLayout();
                    ResetLast(myll, myImageButton, myTextButton);
                    myll.setBackground(new ColorDrawable(Color.rgb(30, 136, 229)));
                    myImageButton.setImageResource(R.drawable.ic_home_white_24dp);
                    myTextButton.setTextColor(Color.WHITE);
                    setPageIndex(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void ResetLayout() {
        this.lastLayout.setBackground(new ColorDrawable(Color.rgb(238, 238, 238)));
        String lastString = this.lastTextView.getText().toString();
        if (lastString.equals("News"))
            this.lastImageView.setImageResource(R.drawable.ic_home_black_24dp);
        else if (lastString.equals("Records"))
            this.lastImageView.setImageResource(R.drawable.ic_records_black_24dp);
        else if (lastString.equals("Map"))
            this.lastImageView.setImageResource(R.drawable.ic_map_black_24dp);
        else if (lastString.equals("Profile"))
            this.lastImageView.setImageResource(R.drawable.ic_profile_black_24dp);
        this.lastTextView.setTextColor(Color.BLACK);
    }

    private void ResetLast(LinearLayout ll, ImageView iv, TextView tv) {
        this.lastLayout = ll;
        this.lastImageView = iv;
        this.lastTextView = tv;
    }

    private int getPageIndex() {
        return this.pageIndex;
    }

    private void setPageIndex(int index) {
        this.pageIndex = index;
    }
}
