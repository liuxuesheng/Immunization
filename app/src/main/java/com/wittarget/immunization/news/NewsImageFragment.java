package com.wittarget.immunization.news;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wittarget.immunization.R;
import com.wittarget.immunization.utils.AsyncResponse;
import com.wittarget.immunization.utils.LoadImageFromURL;
import com.wittarget.immunization.utils.ServerResponse;
import com.wittarget.immunization.utils.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsImageFragment extends Fragment
        implements
        AsyncResponse {

    Activity activity;
    JSONArray arr = null;
    private String news_image_ids = null;
    private String imagsrc = null;
    private WebView imageWebView = null;
    private TextView imageTextView = null;
    private String news_title = null;
    private String news_item_section = null;

    public static NewsImageFragment newInstance(int pos) {
        NewsImageFragment f = new NewsImageFragment();
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager_news_images, container, false);

        ServerResponse pud = new ServerResponse(this);
        pud.execute(config.SERVERADDRESS + "/news/news_image_list.php" + "?image=" + getArguments().getInt("pos"));
        return v;
    }

    @Override
    public void onTaskComplete(Object out) {
        try {
            arr = new JSONArray((String) out);
            JSONObject item = arr.getJSONObject(0);

            ImageView iv = (ImageView) getView().findViewById(R.id.imageview_news_slider);
            //Image slider
            try {

                news_image_ids = item.getString("id");
                imagsrc = item.getString("news_imageURL");
                news_title = item.getString("news_title");
                news_item_section = item.getString("section");
                ;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            LoadImageFromURL loadImage = new LoadImageFromURL();
            loadImage.execute(config.SERVERADDRESS+"/news/" + imagsrc, iv, true, iv.getWidth(), iv.getHeight());

            final String newsId = news_image_ids;
            final String current_item_section = news_item_section;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = null;
                    myIntent = new Intent(activity, NewsDisplayActivity.class);
                    myIntent.putExtra("record", newsId);
                    myIntent.putExtra("section", current_item_section);
                    activity.startActivity(myIntent);
                }
            });

            imageTextView = (TextView) getView().findViewById(R.id.textview_news_slider);
            imageTextView.setText(news_title);
            imageTextView.setTextColor(Color.WHITE);
            imageTextView.setBackgroundColor(Color.argb(150, 30, 136, 229));

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onTaskStart() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }
}