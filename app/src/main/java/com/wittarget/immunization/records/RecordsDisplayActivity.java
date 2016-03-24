package com.wittarget.immunization.records;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wittarget.immunization.R;
import com.wittarget.immunization.utils.AsyncResponse;
import com.wittarget.immunization.utils.PaintService;
import com.wittarget.immunization.utils.ServerResponse;
import com.wittarget.immunization.utils.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class RecordsDisplayActivity extends Activity implements AsyncResponse {
    static final int DATE_DIALOG_ID = 0;
    public String current_date;
    JSONArray arr = null;
    private String mainURL = "";
    private TextView title = null;
    private TextView subtitle = null;
    private TextView body_records = null;
    private LinearLayout bookDate_container = null;
    private int mYear = 2016;
    private int mMonth = 2;
    private int mDay = 11;
    private LinearLayout myLinearLayout = null;
    private ArrayList<LinearLayout> textViewList = null;

    // the call back received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    String yearString = String.valueOf(mYear);
                    String monthString = setDateFormat(mMonth + 1);
                    String dayString = setDateFormat(mDay);
                    current_date = yearString + "-" + monthString + "-" + dayString;
                    System.out.println("Current date2: " + current_date);
                    final TextView sectionTextView = new TextView(getApplicationContext());
                    sectionTextView.setPadding(20, 10, 60, 10);
                    sectionTextView.setTextSize(20);
                    sectionTextView.setTextColor(Color.BLACK);
                    sectionTextView.setBackgroundColor(Color.TRANSPARENT);
                    sectionTextView.setText(current_date);

                    int index = textViewList.indexOf(myLinearLayout);

                    Button bt = new Button(getApplicationContext());
                    bt.setBackground(getDrawable(R.drawable.ic_event_black_24dp));
                    LinearLayout.LayoutParams layoutBt = new LinearLayout.LayoutParams(90, 90);
                    layoutBt.setMargins(0, 20, 60, 10);
                    bt.setLayoutParams(layoutBt);

                    Button bt_schedule = new Button(getApplicationContext());
                    bt_schedule.setText("Schedule");
                    bt_schedule.setTextSize(18);
                    bt_schedule.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    bt_schedule.setTextColor(Color.WHITE);

                    final LinearLayout ll = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layout.setMargins(0, 20, 0, 20);
                    ll.setLayoutParams(layout);
                    ll.setOrientation(LinearLayout.HORIZONTAL);

                    ll.addView(bt);
                    ll.addView(sectionTextView);
                    ll.addView(bt_schedule);

                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myLinearLayout = ll;
                            showDialog(DATE_DIALOG_ID);
                        }
                    });

                    bt_schedule.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar cal = Calendar.getInstance();
                            Intent intent = new Intent(Intent.ACTION_EDIT);
                            intent.setType("vnd.android.cursor.item/event");
                            intent.putExtra("beginTime", cal.getTimeInMillis());
                            intent.putExtra("allDay", true);
                            intent.putExtra("rrule", "FREQ=YEARLY");
                            intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
                            intent.putExtra("title", "Schedule a time to inject immunization");
                            System.out.println("Schedule:"+ cal.getTimeInMillis());
                            startActivity(intent);

                        }
                    });

                    textViewList.remove(myLinearLayout);
                    textViewList.add(index, ll);

                    bookDate_container.removeView(myLinearLayout);
                    bookDate_container.addView(ll, index);
                }
            };

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
        bookDate_container = (LinearLayout) this.findViewById(R.id.bookDate_layout);

        ServerResponse pud = new ServerResponse(this);
        pud.execute(getMainURL() + "?page=" + intent.getStringExtra("record") + "&section=" + intent.getStringExtra("section"));

    }

    private void addObjectsToView(JSONArray jsonArray, String url) {
        textViewList = new ArrayList<LinearLayout>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject item = jsonArray.getJSONObject(i);

                if (item.getString("type").equals("title_records")) {
                    title.setText(item.getString("name"));
                    //System.out.println("hello");

                } else if (item.getString("type").equals("subtitle_records")) {
                    subtitle.setText(item.getString("name"));

                } else if (item.getString("type").equals("body_records")) {
                    body_records.setText(item.getString("name"));

                } else if (item.getString("type").equals("immu_times")) {
                    //title.setText(item.getString("name"));
                    for (int j = 0; j < Integer.parseInt(item.getString("name")); j++) {
                        try {
                            final TextView sectionTextView = new TextView(this);

                            current_date = String.valueOf(mYear) + "-" + setDateFormat(mMonth + 1 + j*1) + "-" + setDateFormat(mDay);

                            sectionTextView.setPadding(20, 10, 60, 10);
                            sectionTextView.setTextSize(20);
                            sectionTextView.setBackgroundColor(Color.TRANSPARENT);
                            sectionTextView.setText(current_date);

                            Button bt = new Button(this);
                            bt.setBackground(getDrawable(R.drawable.ic_event_black_24dp));
                            LinearLayout.LayoutParams layoutBt = new LinearLayout.LayoutParams(90, 90);
                            layoutBt.setMargins(0, 20, 60, 10);
                            bt.setLayoutParams(layoutBt);

                            Button bt_schedule = new Button(this);
                            bt_schedule.setText("Schedule");
                            bt_schedule.setTextSize(20);
                            bt_schedule.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            bt_schedule.setTextColor(Color.WHITE);

                            final LinearLayout ll = new LinearLayout(this);
                            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layout.setMargins(0, 20, 0, 20);
                            ll.setLayoutParams(layout);
                            ll.setOrientation(LinearLayout.HORIZONTAL);

                            ll.addView(bt);
                            ll.addView(sectionTextView);
                            ll.addView(bt_schedule);

                            bt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    myLinearLayout = ll;
                                    showDialog(DATE_DIALOG_ID);
                                }
                            });

                            bt_schedule.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Calendar cal = Calendar.getInstance();
                                    Intent intent = new Intent(Intent.ACTION_EDIT);
                                    intent.setType("vnd.android.cursor.item/event");
                                    intent.putExtra("beginTime", cal.getTimeInMillis());
                                    intent.putExtra("allDay", true);
                                    intent.putExtra("rrule", "FREQ=YEARLY");
                                    intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
                                    intent.putExtra("title", "Schedule a time to inject immunization");
                                    System.out.println("Schedule:"+ cal.getTimeInMillis());
                                    startActivity(intent);

                                }
                            });

                            textViewList.add(j, ll);
                            bookDate_container.addView(ll);
                        } catch (Exception ex) {
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

