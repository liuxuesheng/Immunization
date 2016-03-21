package com.wittarget.immunization.profile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.wittarget.immunization.LoginActivity;
import com.wittarget.immunization.R;
import com.wittarget.immunization.utils.AsyncResponse;
import com.wittarget.immunization.utils.ServerResponse;
import com.wittarget.immunization.utils.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class BabyProfileDisplayActivity extends AppCompatActivity implements AsyncResponse {

    private static final String TAG = "BabyProfileDisplayActivity";

    JSONArray arr = null;
    private String mainURL = "";

    static final int DATE_DIALOG_ID = 0;
    static final int PROVINCE_DIALOG_ID = 1;
    static final int CITY_DIALOG_ID = 2;
    static final int GENDER_DIALOG_ID = 3;

    static final String[] province_array = {"Ontario", "Quebec", "Nova Scotia", "New Brunswick", "Manitoba", "British Columbia", "Prince Edward Island", "Saskatchewan", "Alberta", "Newfoundland and Labrador"};
    static final String[] city_array = {"Thunder Bay", "Mississauga"};
    static final String[] gender_array = {"girl", "boy"};
    public TextView tvBirthday;
    public TextView tvProvince;
    public TextView tvCity;
    public TextView tvGender;
    public TextView tvEmail;
    public EditText etInput_nickname;
    public Button btnSave;

    private int mYear;
    private int mMonth;
    private int mDay;
    private String mProvince;
    private String mCity;
    private String mGender;
    private String mBirthday;
    private int gender_number;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_profile);
        //date picker presentation
        tvBirthday = (TextView) findViewById(R.id.input_birthday);//button for showing date picker dialog
        tvProvince = (TextView) findViewById(R.id.input_province);
        tvCity = (TextView) findViewById(R.id.input_city);
        tvGender = (TextView) findViewById(R.id.input_gender);
        tvEmail = (TextView) findViewById(R.id.input_email);
        btnSave = (Button) findViewById(R.id.btn_save);
        etInput_nickname = (EditText)findViewById(R.id.input_nickname);


        Intent intent = getIntent();
        setMaintURL(config.SERVERADDRESS + "/profile/profile.php");

        clickTextviewListener(tvBirthday);
        clickTextviewListener(tvProvince);
        clickTextviewListener(tvCity);
        clickTextviewListener(tvGender);

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        // display the current date
        updateDisplay();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    //create textview listener
    public void clickTextviewListener(View v) {
        if (v == tvBirthday) {
            v.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showDialog(DATE_DIALOG_ID);
                }
            });
        } else if (v == tvProvince) {
            v.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showDialog(PROVINCE_DIALOG_ID);

                }
            });
        } else if (v == tvCity) {
            v.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showDialog(CITY_DIALOG_ID);
                }
            });
        } else if (v == tvGender) {
            v.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showDialog(GENDER_DIALOG_ID);
                }
            });
        } else {
        }
    }

    //return date picker dialog
    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
            case PROVINCE_DIALOG_ID:
                AlertDialog.Builder provinceBuilder = new AlertDialog.Builder(this);
                provinceBuilder.setTitle("Select Province")
                        .setItems(province_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mProvince = province_array[which];
                                tvProvince.setText(mProvince);
                            }
                        });
                return provinceBuilder.create();
            case CITY_DIALOG_ID:
                AlertDialog.Builder cityBuilder = new AlertDialog.Builder(this);
                cityBuilder.setTitle("Select City")
                        .setItems(city_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mCity = city_array[which];
                                tvCity.setText(mCity);
                            }
                        });
                return cityBuilder.create();
            case GENDER_DIALOG_ID:
                AlertDialog.Builder genderBuilder = new AlertDialog.Builder(this);
                genderBuilder.setTitle("Select Gender")
                        .setItems(gender_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mGender = gender_array[which];
                                tvGender.setText(mGender);
                                if(mGender == "boy"){
                                    gender_number = 0;
                                }
                                else
                                    gender_number = 1;
                            }
                        });
                return genderBuilder.create();
        }
        return null;
    }

    //update month day year
    private void updateDisplay() {
        String yearString = String.valueOf(mYear);
        String monthString = setDateFormat(mMonth + 1);
        String dayString = setDateFormat(mDay);
        mBirthday = yearString + "-" + monthString + "-" + dayString;
        tvBirthday.setText(mBirthday);
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

    @Override
    public void onTaskComplete(Object output) {
        try {
            arr = new JSONArray((String) output);
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

    public void saveProfile() {
        final ProgressDialog progressDialog = new ProgressDialog(BabyProfileDisplayActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Waiting...");
        progressDialog.show();
        ServerResponse pud = new ServerResponse(this);

        String nickname = etInput_nickname.getText().toString();
        String URL = (config.SERVERADDRESS + "/profile/profile.php?nickname=" + nickname + "&gender=" + gender_number + "&birthday=" + mBirthday + "&province=" + mProvince + "&city=" + mCity).replace(" ","%20");
        pud.execute(URL);
        //System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHhh" + config.SERVERADDRESS + "/profile/profile.php?nickname=" + nickname + "&gender=" + gender_number + "&birthday=" + mBirthday + "&province=" + mProvince + "&city=" + mCity);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (config.getAuth(getApplicationContext()))
                            System.out.println("pass");
                        else
                            System.out.println("failed");
                        progressDialog.dismiss();
                    }
                }, 1000);
    }
}
