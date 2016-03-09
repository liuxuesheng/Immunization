package com.wittarget.immunization.mainPageFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wittarget.immunization.R;
import com.wittarget.immunization.records.RecordsDisplayActivity;
import com.wittarget.immunization.utils.AsyncResponse;
import com.wittarget.immunization.utils.ServerResponse;
import com.wittarget.immunization.utils.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RecordsFragment extends Fragment implements AsyncResponse {
    Activity activity;
    JSONArray arr = null;
    private LinearLayout records_container = null;

    public static RecordsFragment newInstance(String text) {
        RecordsFragment f = new RecordsFragment();
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
                                                                                                                                                                                                                                                                arr = new JSONArray((String) out);
                                                                                                                                                                                                                                                                JSONObject item = null;
            String currentId = null;

            for (int i = 0; i < arr.length(); i++) {
                TextView title_textview = null;
                TextView subtitle_textview = null;
                CheckBox checkbox = null;
                boolean flag = false;
                TextView sectionTextView = null;
                String item_section = null;
                final boolean[] hasChecked = new boolean[arr.length()];

                try {
                    item = arr.getJSONObject(i);
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
                    System.out.println("Myitem_section" + item_section);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //crete records name view
                try {
                    currentId = item.getString("id");
                    final String recordsId = currentId;
                    final String current_item_section = item_section;

                    title_textview = new TextView(activity);
                    title_textview.setText(item.getString("immu_name"));
                    title_textview.setBackgroundColor(Color.WHITE);
                    title_textview.setPadding(20, 10, 30, 10);
                    title_textview.setTextSize(18);
                    title_textview.setBackgroundColor(Color.TRANSPARENT);
                    title_textview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent myIntent = null;
                            myIntent = new Intent(activity, RecordsDisplayActivity.class);
                            myIntent.putExtra("record", recordsId);
                            myIntent.putExtra("section", current_item_section);
                            activity.startActivity(myIntent);
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //crete records subtitle view
                try {
                    currentId = item.getString("id");
                    final String recordsId = currentId;
                    final String current_item_section = item_section;

                    subtitle_textview = new TextView(activity);
                    subtitle_textview.setText(item.getString("immu_subtitle"));
                    subtitle_textview.setBackgroundColor(Color.WHITE);
                    subtitle_textview.setPadding(20, 10, 30, 10);
                    subtitle_textview.setTextSize(14);
                    subtitle_textview.setBackgroundColor(Color.TRANSPARENT);
                    subtitle_textview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent myIntent = null;
                            myIntent = new Intent(activity, RecordsDisplayActivity.class);
                            myIntent.putExtra("record", recordsId);
                            myIntent.putExtra("section", current_item_section);
                            activity.startActivity(myIntent);
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //crete check box view
                try {
                    currentId = item.getString("id");
                    final String recordsId = currentId;
                    final String current_item_section = item_section;
                    final int checkboxID = Integer.parseInt(recordsId);

                    checkbox = new CheckBox(activity);
                    checkbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            hasChecked[checkboxID] = !hasChecked[checkboxID];
                            System.out.println("lllllllllllllllllll" + hasChecked[Integer.parseInt(recordsId)]);
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                if (flag == true) {
                    records_container.addView(title_textview);
                } else {
                    LinearLayout linearLayoutH = new LinearLayout(activity);
                    linearLayoutH.setOrientation(LinearLayout.HORIZONTAL);
                    //linearLayoutH.addView(imageView);
                    LinearLayout linearLayoutV = new LinearLayout(activity);
                    linearLayoutV.setOrientation(LinearLayout.VERTICAL);
                    linearLayoutV.addView(title_textview);
                    linearLayoutV.addView(subtitle_textview);
                    linearLayoutH.addView(checkbox);
                    linearLayoutH.addView(linearLayoutV);

                    records_container.addView(linearLayoutH);
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
