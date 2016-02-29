package com.wittarget.immunization.mainPageFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wittarget.immunization.MainActivity;
import com.wittarget.immunization.R;
import com.wittarget.immunization.news.NewsDisplayActivity;
import com.wittarget.immunization.utils.AsyncResponse;
import com.wittarget.immunization.utils.LoadImageFromURL;
import com.wittarget.immunization.utils.PaintService;
import com.wittarget.immunization.utils.ServerResponse;
import com.wittarget.immunization.utils.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class recordsFragment extends Fragment implements AsyncResponse {
    Activity activity;
    JSONArray arr = null;
    //ImageView iv = null;
    private LinearLayout records_container = null;
    //private ViewPager recordsPager = null;

    public static recordsFragment newInstance(String text) {
        recordsFragment f = new recordsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_records, container, false);

        //Search records on server
        ServerResponse pud = new ServerResponse(this);
        pud.execute(config.SERVERADDRESS + "/records/records_list.php");
        records_container = (LinearLayout) v.findViewById(R.id.container_records);
        return v;
    }

    @Override
    public void onTaskComplete(Object out) {
        try {
            //news list
            arr = new JSONArray((String)out);

            ArrayList<String> ids = new ArrayList<String>();
            JSONObject item = null;
            String currentId = null;

            for (int i = 0; i < arr.length(); i++) {
                String imageSrc = null;
                TextView tv = null;
                boolean flag = false;
                TextView sectionTextView = null;
                String item_section = null;

                try {
                    item = arr.getJSONObject(i);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    String sectionName = item.getString("section");
                    sectionTextView = new TextView(activity);
                    sectionTextView.setText(sectionName);
                    sectionTextView.setTextSize(21);
                    sectionTextView.setTextColor(Color.rgb(30, 136, 229));
                    sectionTextView.setPadding(20, 30, 0, 50);
                    sectionTextView.setCompoundDrawablesWithIntrinsicBounds(PaintService.paintTextIconDrawable(activity, null, 19, 16, new ShapeDrawable(new RectShape()), Color.rgb(30, 136, 229)), null, null, null);
                    sectionTextView.setCompoundDrawablePadding(16);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    sectionTextView.setLayoutParams(params);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (sectionTextView != null) {
                    addDivider(records_container, Color.rgb(30, 136, 229), 6);
                    records_container.addView(sectionTextView);
                    addDivider(records_container, Color.LTGRAY, 3);
                    continue;
                }

                try {
                    item_section = item.getString("item_section");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    currentId = item.getString("id");
                    final String newsId = currentId;
                    final String current_item_section = item_section;

                    tv = new TextView(activity);
                    tv.setText(item.getString("immu_name"));
                    tv.setBackgroundColor(Color.WHITE);
                    tv.setPadding(30, 10, 30, 10);
                    tv.setTextSize(17);
                    tv.setBackgroundColor(Color.TRANSPARENT);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent myIntent = null;
                            myIntent = new Intent(activity, NewsDisplayActivity.class);
                            myIntent.putExtra("record", newsId);
                            myIntent.putExtra("section", current_item_section);
                            activity.startActivity(myIntent);
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (flag == true) {
                    records_container.addView(tv);
                } else {
                    LinearLayout ll = new LinearLayout(activity);
                    ll.setOrientation(LinearLayout.HORIZONTAL);

                    //ll.addView(iv);
                    ll.addView(tv);
                    records_container.addView(ll);
                    System.out.println("HHHHHHHHHHHHHHH");

                }

                addDivider(records_container, Color.LTGRAY, 3);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onTaskStart() {

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }
    private void addDivider(LinearLayout ll, int color, int height) {
        try {
            View divider = new View(activity);
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

}
