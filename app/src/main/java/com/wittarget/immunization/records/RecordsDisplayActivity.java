package com.wittarget.immunization.records;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.wittarget.immunization.R;
import com.wittarget.immunization.utils.AsyncResponse;
import com.wittarget.immunization.utils.LoadImageFromURL;
import com.wittarget.immunization.utils.PaintService;
import com.wittarget.immunization.utils.ServerResponse;
import com.wittarget.immunization.utils.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecordsDisplayActivity extends Activity implements AsyncResponse {
    JSONArray arr = null;
    private String mainURL = "";
    private TextView title = null;
    private TextView subtitle = null;
    private TextView body_records = null;
    @Override
    public void onTaskComplete(Object out) {
        try {
            Log.d("system time2: ", "" + System.currentTimeMillis());
            arr = new JSONArray((String) out);
            Log.d("system time3: ", "" + System.currentTimeMillis());
            addObjectsToView(arr, getMainURL());
            Log.d("system time4: ", "" + System.currentTimeMillis());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onTaskStart() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PaintService.setBackgroundPainted(false);
        PaintService.setTextPainted(false);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        // getActionBar().hide();

        Intent intent = getIntent();
        setMaintURL(config.SERVERADDRESS + "/records/records.php");

        setContentView(R.layout.activity_records);
        title = (TextView) this.findViewById(R.id.title_records);
        subtitle = (TextView) this.findViewById(R.id.subtitle_records);
        body_records = (TextView) this.findViewById(R.id.body_records);

        ServerResponse pud = new ServerResponse(this);
        pud.execute(getMainURL() + "?page=" + intent.getStringExtra("record") + "&section=" + intent.getStringExtra("section"));
        System.out.println("HHHHHHHHHHrecords:" + getMainURL() + "?page=" + intent.getStringExtra("record") + "&section=" + intent.getStringExtra("section"));
    }

    private void addObjectsToView(JSONArray jsonArray, String url) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject item = jsonArray.getJSONObject(i);

                if (item.getString("type").equals("title_records")) {
                    title.setText(item.getString("name"));
                } else if (item.getString("type").equals("subtitle_records")) {
                    subtitle.setText(item.getString("name"));
                } else if (item.getString("type").equals("body_records")) {
                    body_records.setText(item.getString("name"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private String getMainURL() {
        return this.mainURL;
    }

    private void setMaintURL(String page) {
        this.mainURL = getMainURL() + page;
    }
}
