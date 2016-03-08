package com.wittarget.immunization;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.wittarget.immunization.utils.config;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        PopupMenu.OnMenuItemClickListener {

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
    private ImageView lastImageView = null;
    private LinearLayout lastLayout = null;
    private int pageIndex = 0;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mLastLocation = null;
    private LocationRequest mLocationRequest = null;
    private Toolbar toolbar = null;

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

        //Set toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("News");

        //Get metrics
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;

        newsImageWidth = getScreenWidth() * 7 / 24;
        newsImageHeight = (int) (getNewsImageWidth() * 0.618);

        testImageWidth = getScreenWidth() / 2;
        testImageHeight = (int) (getTestImageWidth() * 0.618);

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

        // Set last visited
        this.lastImageView = imageButton0;
        this.lastLayout = ll0;

        //Setup initial configuration
        imageButton0.setImageResource(R.drawable.ic_home_white_24dp);

        ll0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetLayout(0);
                ResetLast(ll0, imageButton0);
                mPager.setCurrentItem(0);
                imageButton0.setImageResource(R.drawable.ic_home_white_24dp);
                getSupportActionBar().setTitle("News");
                setPageIndex(0);
            }
        });

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetLayout(1);
                ResetLast(ll1, imageButton1);
                mPager.setCurrentItem(1);
                imageButton1.setImageResource(R.drawable.ic_records_white_24dp);
                getSupportActionBar().setTitle("Records");
                setPageIndex(1);
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetLayout(2);
                ResetLast(ll2, imageButton2);
                mPager.setCurrentItem(2);
                imageButton2.setImageResource(R.drawable.ic_map_white_24dp);
                getSupportActionBar().setTitle("Map");
                setPageIndex(2);
            }
        });

        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetLayout(3);
                ResetLast(ll3, imageButton3);
                mPager.setCurrentItem(3);
                imageButton3.setImageResource(R.drawable.ic_profile_white_24dp);
                getSupportActionBar().setTitle("Profile");
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
                int index = 0;

                switch (position) {
                    case 0:
                        index = 0;
                        myll = ll0;
                        myImageButton = imageButton0;
                        break;
                    case 1:
                        index = 1;
                        myll = ll1;
                        myImageButton = imageButton1;
                        break;
                    case 2:
                        index = 2;
                        myll = ll2;
                        myImageButton = imageButton2;
                        break;
                    case 3:
                        index = 3;
                        myll = ll3;
                        myImageButton = imageButton3;
                        break;
                    default:
                        index = 0;
                        myll = ll0;
                        myImageButton = imageButton0;
                        break;
                }
                if (position != getPageIndex()) {
                    ResetLayout(index);
                    ResetLast(myll, myImageButton);
                    if (index == 0)
                        myImageButton.setImageResource(R.drawable.ic_home_white_24dp);
                    else if (index == 1)
                        myImageButton.setImageResource(R.drawable.ic_records_white_24dp);
                    else if (index == 2)
                        myImageButton.setImageResource(R.drawable.ic_map_white_24dp);
                    else if (index == 3)
                        myImageButton.setImageResource(R.drawable.ic_profile_white_24dp);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onMoreAction(MenuItem item) {
        View menuMore = findViewById(R.id.miMore);
        PopupMenu popup = new PopupMenu(this, menuMore);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_setting);
        popup.show();
        return true;

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting_1:
                config.setAuth(this, false);
                config.setToken(this, "");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }


    private void ResetLayout(int index) {
        if (getPageIndex() == 0 && index != 0)
            this.lastImageView.setImageResource(R.drawable.ic_home_black_24dp);
        else if (getPageIndex() == 1 && index != 1)
            this.lastImageView.setImageResource(R.drawable.ic_records_black_24dp);
        else if (getPageIndex() == 2 && index != 2)
            this.lastImageView.setImageResource(R.drawable.ic_map_black_24dp);
        else if (getPageIndex() == 3 && index != 3)
            this.lastImageView.setImageResource(R.drawable.ic_profile_black_24dp);
    }

    private void ResetLast(LinearLayout ll, ImageView iv) {
        this.lastLayout = ll;
        this.lastImageView = iv;
    }

    private int getPageIndex() {
        return this.pageIndex;
    }

    private void setPageIndex(int index) {
        this.pageIndex = index;
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
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } catch (SecurityException ex) {
                Log.d("hihihi", "sec expt");
            }
        } else {
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
        config.setLocation(this, Float.parseFloat(String.valueOf(location.getLatitude())), Float.parseFloat(String.valueOf(location.getLongitude())));
    }
}
