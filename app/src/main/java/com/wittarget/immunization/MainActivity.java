package com.wittarget.immunization;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.wittarget.immunization.utils.config;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
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
    private GoogleApiClient mGoogleApiClient = null;
    private Location mLastLocation = null;
    private LocationRequest mLocationRequest = null;

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

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        Log.d("hihihi", "1 location");
        createLocationRequest();
        Log.d("hihihi", "2 location");
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

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException ex) {
            Log.d("hihihi", "sec expt");
        }

        if (mLastLocation == null) {
            try{
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } catch (SecurityException ex) {
                Log.d("hihihi", "sec expt");
            }
        }else {
            handleNewLocation(mLastLocation);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("hihihi", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("hihihi", "Location services suspended. Please reconnect.");
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void handleNewLocation(Location location) {
        Log.d("hihihi", String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude()));
    }
}
