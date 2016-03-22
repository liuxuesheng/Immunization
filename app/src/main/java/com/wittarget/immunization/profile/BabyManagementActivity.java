package com.wittarget.immunization.profile;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    public void onTaskComplete(Object out) {
        try {
            //news list
            arr = new JSONArray((String) out);
            JSONObject item = null;
            String currentId = null;

            for (int i = 0; i < arr.length(); i++) {
                TextView nicknameTextview = null;
                boolean flag = false;
                TextView sectionTextView = null;
                String item_section = null;
                String nickname = null;
                try {
                    item = arr.getJSONObject(i);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (sectionTextView != null) {
                    addDivider(baby_container, Color.rgb(30, 136, 229), 6);
                    baby_container.addView(sectionTextView);
                    addDivider(baby_container, Color.LTGRAY, 3);
                    continue;
                }

                try {
                    item_section = item.getString("item_section");
                    System.out.println("Myitem_section" + item_section);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //crete records name view
                try {
                    nickname = item.getString("nickname");
                    nicknameTextview = new TextView(this);
                    nicknameTextview.setText(item.getString("nickname"));
                    nicknameTextview.setBackgroundColor(Color.WHITE);
                    nicknameTextview.setPadding(20, 10, 30, 10);
                    nicknameTextview.setTextSize(18);
                    nicknameTextview.setBackgroundColor(Color.TRANSPARENT);
                    System.out.println("My nickname" + nickname);
                    nicknameTextview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

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
