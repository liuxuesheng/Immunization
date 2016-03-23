package com.wittarget.immunization.profile;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wittarget.immunization.R;
import com.wittarget.immunization.utils.AsyncResponse;
import com.wittarget.immunization.utils.ServerResponse;
import com.wittarget.immunization.utils.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BabyManagementActivity extends AppCompatActivity implements AsyncResponse {
    JSONArray arr = null;
    private String mainURL = "";
    private LinearLayout baby_container = null;
    private Toolbar toolbar = null;

    @Override
    public void onTaskComplete(Object out) {
        try {
            //news list
            arr = new JSONArray((String) out);
            JSONObject item = null;

            for (int i = 0; i < arr.length(); i++) {
                TextView nicknameTextview = null;
                String nickname = null;
                try {
                    item = arr.getJSONObject(i);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //create nickname text view
                try {
                    nickname = item.getString("nickname");
                    final String name = item.getString("nickname");
                    nicknameTextview = new TextView(this);
                    nicknameTextview.setText(nickname);
                    nicknameTextview.setBackgroundColor(Color.WHITE);
                    nicknameTextview.setPadding(30, 20, 30, 20);
                    nicknameTextview.setTextSize(25);
                    nicknameTextview.setBackgroundColor(Color.TRANSPARENT);
                    nicknameTextview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("nickname", name);
                            intent.setClass(BabyManagementActivity.this, BabyProfileChangeActivity.class);
                            startActivity(intent);
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                baby_container.addView(nicknameTextview);
                addDivider(baby_container, Color.LTGRAY, 3);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onTaskStart() {
        Log.d("system time1: ", "" + System.currentTimeMillis());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_management);
        //Set toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MyBabies");

        String token = config.getToken(this);

        setMaintURL(config.SERVERADDRESS + "/profile/babies_list.php");
        ServerResponse pud = new ServerResponse(this);
        pud.execute(getMainURL() + "?token=" + token);
        System.out.println(getMainURL() + "?token=" + token);

        baby_container = (LinearLayout) findViewById(R.id.container_baby);

        //create one float action button
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

    private void addDivider(LinearLayout ll, int color, int height) {
        try {
            View divider = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, height);
            params.setMargins(0, 0, 0, 0);
            divider.setLayoutParams(params);
            divider.setBackgroundColor(color);
            ll.addView(divider);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getMainURL() {
        return this.mainURL;
    }

    private void setMaintURL(String page) {
        this.mainURL = getMainURL() + page;
    }
}
