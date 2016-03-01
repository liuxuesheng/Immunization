package com.wittarget.immunization;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wittarget.immunization.utils.config;

public class MainActivity extends AppCompatActivity {

    private static int screenWidth = 0;
    private static int newsImageWidth = 0;
    private static int newsImageHeight = 0;
    private static int testImageWidth = 0;
    private static int testImageHeight = 0;

    private FragmentManager fragmentManager;
    private myAdapter mAdapter;
    private myViewPager mPager;
    private int lastEnabled = 0;
    private TextView lastEnabledText = null;
    private TextView lastTextView = null;
    private ImageView lastImageView = null;
    private LinearLayout lastLayout = null;
    private int pageIndex = 0;

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getNewsImageWidth() {
        return newsImageWidth;
    }

    public static int getNewsImageHeight() {
        return newsImageHeight;
    }

    public static int getTestImageWidth() {
        return testImageWidth;
    }

    public static int getTestImageHeight() {
        return testImageHeight;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get metrics
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;

        newsImageWidth = getScreenWidth() * 7 / 24;
        newsImageHeight = (int) (getNewsImageWidth() * 0.618);

        testImageWidth = getScreenWidth() / 2;
        testImageHeight = (int) (getTestImageWidth() * 0.618);

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
                mPager.setCurrentItem(1);
                imageButton1.setImageResource(R.drawable.ic_records_white_24dp);
                textButton1.setTextColor(Color.WHITE);
                setPageIndex(1);
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetLayout();
                ResetLast(ll2, imageButton2, textButton2);
                mPager.setCurrentItem(2);
                imageButton2.setImageResource(R.drawable.ic_map_white_24dp);
                textButton2.setTextColor(Color.WHITE);
                setPageIndex(2);
            }
        });

        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetLayout();
                ResetLast(ll3, imageButton3, textButton3);
                mPager.setCurrentItem(3);
                imageButton3.setImageResource(R.drawable.ic_profile_white_24dp);
                textButton3.setTextColor(Color.WHITE);
                setPageIndex(3);
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

    public void logout(View v) {
        config.setAuth(this, false);
        config.setToken(this, "");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
