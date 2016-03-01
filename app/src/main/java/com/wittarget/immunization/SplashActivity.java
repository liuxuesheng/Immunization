package com.wittarget.immunization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.wittarget.immunization.utils.config;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final boolean userLoggedin = config.getAuth(getApplicationContext());

        Log.d("sada8d9wq89d ", "" + userLoggedin);

        Thread splashScreen = new Thread() {
            public void run() {
                try {
                    Thread.sleep(1*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Log.d("sada8d9wq89d ", "" + userLoggedin);
                    if (userLoggedin == false) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    finish();
                }
            }
        };
        splashScreen.start();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
    }
}
