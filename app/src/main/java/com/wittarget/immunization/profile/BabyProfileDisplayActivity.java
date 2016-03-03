package com.wittarget.immunization.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wittarget.immunization.R;
import com.wittarget.immunization.utils.AsyncResponse;

public class BabyProfileDisplayActivity extends AppCompatActivity implements
        AsyncResponse {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_profile);
    }

    @Override
    public void onTaskComplete(Object output) {

    }

    @Override
    public void onTaskStart() {

    }
}
