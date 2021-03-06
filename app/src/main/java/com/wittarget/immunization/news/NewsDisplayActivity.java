package com.wittarget.immunization.news;

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

public class NewsDisplayActivity extends Activity implements AsyncResponse {

    JSONArray arr = null;
    private String mainURL = "";
    private TextView title = null;
    private TextView subtitle = null;
    private TextView date = null;
    private TextView body_news = null;
    private ImageView iv = null;

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
        Log.d("system time1: ", ""+System.currentTimeMillis());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PaintService.setBackgroundPainted(false);
        PaintService.setTextPainted(false);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
       // getActionBar().hide();

        Intent intent = getIntent();
        setMaintURL(config.SERVERADDRESS+"/news/news.php");

        setContentView(R.layout.activity_news);
        title = (TextView) this.findViewById(R.id.title_news);
        subtitle = (TextView) this.findViewById(R.id.subtitle_news);
        date = (TextView) this.findViewById(R.id.date_news);
        body_news = (TextView) this.findViewById(R.id.body_news);
        iv = (ImageView) this.findViewById(R.id.imageview_news);

        ServerResponse pud = new ServerResponse(this);
        pud.execute(getMainURL() + "?page=" + intent.getStringExtra("record") + "&section=" + intent.getStringExtra("section"));
        System.out.println("HHHHHHHHHHnews:" + getMainURL() + "?page=" + intent.getStringExtra("record") + "&section=" + intent.getStringExtra("section"));
    }

    private void addObjectsToView(JSONArray jsonArray, String url) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject item = jsonArray.getJSONObject(i);

                if (item.getString("type").equals("webview")) {
                    try {
                        LoadImageFromURL loadImage = new LoadImageFromURL();
                        Log.d("system time5: ", "" + System.currentTimeMillis());
                        loadImage.execute(config.SERVERADDRESS + "/news/" + item.getString("url"), iv, false);

                        Log.d("system time6: ", "" + System.currentTimeMillis());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (item.getString("type").equals("title_news")) {
                    title.setText(item.getString("name"));
                } else if (item.getString("type").equals("subtitle_news")) {
                    subtitle.setText(item.getString("name"));
                } else if (item.getString("type").equals("date_news")) {
                    date.setText(item.getString("name"));
                } else if (item.getString("type").equals("body_news")) {
                    body_news.setText(item.getString("name"));
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
