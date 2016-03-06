package com.wittarget.immunization.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class config {
   // public final static String SERVERADDRESS = "http://192.168.0.18/Immunizationserver";
    public final static String SERVERADDRESS = "http://10.100.26.145/immunizationserver";
    //public final static String SERVERADDRESS = "http://192.168.250.10/immunizationserver";

    public static boolean getAuth(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("immunization", Context.MODE_PRIVATE);
        return prefs.getBoolean("immunization.auth", false);
    }

    public static void setAuth(Context context, Boolean login) {
        SharedPreferences.Editor editor = context.getSharedPreferences("immunization", Context.MODE_PRIVATE).edit();
        editor.putBoolean("immunization.auth", login);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("immunization", Context.MODE_PRIVATE);
        return prefs.getString("immunization.token", "");
    }

    public static void setToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences("immunization", Context.MODE_PRIVATE).edit();
        editor.putString("immunization.token", token);
        editor.apply();
    }
}
