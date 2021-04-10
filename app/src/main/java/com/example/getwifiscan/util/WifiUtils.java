package com.example.getwifiscan.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.example.getwifiscan.util.PreferenceKeyConst;

public class WifiUtils {
    private static SharedPreferences sPrefs;
    private static Context sCurrentContext;
    static SharedPreferences sharedPreferences;

    public static SharedPreferences getPrefs() {
        if (sPrefs == null) {
            sPrefs = PreferenceManager.getDefaultSharedPreferences(getCurrentContext());
        }
        return sPrefs;
    }
    //static String fileTemp = sharedPreferences.getString(PreferenceKeyConst.KEY_ROOM_ID, "UnKnown");

    public static Context getCurrentContext() {
        return sCurrentContext;
    }

    public static void setCurrentContext(Context context) {
        sCurrentContext = context;
    }


    public static String getLocalDateTimeNow() {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Date currentTime = Calendar.getInstance(timeZone).getTime();
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(currentTime);
    }
}
