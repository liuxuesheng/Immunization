package com.wittarget.immunization.records;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    static final int DATE_DIALOG_ID = 0;

    JSONArray arr = null;
    private String mainURL = "";
    private TextView title = null;
    private TextView subtitle = null;
    private TextView body_records = null;
    private LinearLayout bookDate_container = null;
    private int mYear =2016;
    private int mMonth = 2;
    private int mDay = 14;
    public String current_date;

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
        bookDate_container = (LinearLayout)this.findViewById(R.id.bookDate_layout);

        ServerResponse pud = new ServerResponse(this);
        pud.execute(getMainURL() + "?page=" + intent.getStringExtra("record") + "&section=" + intent.getStringExtra("section"));
        System.out.println("HHHHHHHHHHrecords:" + getMainURL() + "?page=" + intent.getStringExtra("record") + "&section=" + intent.getStringExtra("section"));
    }

    private void addObjectsToView(JSONArray jsonArray, String url) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject item = jsonArray.getJSONObject(i);
                TextView sectionTextView = null;

                if (item.getString("type").equals("title_records")) {
                    title.setText(item.getString("name"));
                    //System.out.println("hello");

                } else if (item.getString("type").equals("subtitle_records")) {
                    subtitle.setText(item.getString("name"));

                } else if (item.getString("type").equals("body_records")) {
                    body_records.setText(item.getString("name"));

                } else if (item.getString("type").equals("immu_times")) {
                    //title.setText(item.getString("name"));
                    for(int j = 0; j< Integer.parseInt(item.getString("name"));j++){
                        try{
                            sectionTextView = new TextView(this);
                            current_date = String.valueOf(mYear)+ "-" +setDateFormat(mMonth + 1)+ "-" +setDateFormat(mDay);
                            System.out.println("current_date" + current_date);
                            sectionTextView.setText(current_date);
                            sectionTextView.setPadding(20, 10, 30, 10);
                            sectionTextView.setTextSize(20);
                            sectionTextView.setBackgroundColor(Color.TRANSPARENT);
                            sectionTextView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showDialog(DATE_DIALOG_ID);
                                }
                            });
                            bookDate_container.addView(sectionTextView);
                        }catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //return date picker dialog
    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }
    // the call back received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    //update month day year
    private void updateDisplay() {
        String yearString = String.valueOf(mYear);
        String monthString = setDateFormat(mMonth + 1);
        String dayString = setDateFormat(mDay);
        current_date = yearString + "-" + monthString + "-" + dayString;
        //current_date.setText(current_date);
    }
    //set date to yyyy-mm-dd
    public String setDateFormat(int i) {
        String datestring;
        if (i < 10) {
            datestring = "0" + String.valueOf(i);
        } else {
            datestring = String.valueOf(i);
        }
        return datestring;
    }

    private String getMainURL() {
        return this.mainURL;
    }

    private void setMaintURL(String page) {
        this.mainURL = getMainURL() + page;
    }
}
