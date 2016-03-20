package com.wittarget.immunization.profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wittarget.immunization.R;
import com.wittarget.immunization.utils.AsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;

public class BabyManagementActivity extends AppCompatActivity implements AsyncResponse {
    JSONArray arr = null;
    private String mainURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_management);
//        String token = config.getToken(this);
//        System.out.println("Token: "+ token);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(BabyManagementActivity.this, BabyProfileDisplayActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addObjectsToView(JSONArray jsonArray, String url) {

    }

    @Override
    public void onTaskComplete(Object out) {
        try {
            arr = new JSONArray((String) out);
            addObjectsToView(arr, getMainURL());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onTaskStart() {

    }


    private String getMainURL() {
        return this.mainURL;
    }

    private void setMaintURL(String page) {
        this.mainURL = getMainURL() + page;
    }
}
