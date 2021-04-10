package com.example.getwifiscan;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.getwifiscan.model.WifiObjectCustom;
import com.example.getwifiscan.util.PreferenceKeyConst;
import com.example.getwifiscan.util.WifiHelper;
import com.example.getwifiscan.util.WifiUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "AndroidExample";
    private static final int MY_REQUEST_CODE = 123;
    static SharedPreferences sharedPreferences;
    private final String filetype = "file.txt";
    private final String filepath = "MyFileStorage";
    private final Handler mHandler = new Handler();
    private WifiManager wifiManager;
    private Button buttonState;
    private Button buttonScan;

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int index = sharedPreferences.getInt(PreferenceKeyConst.KEY_TIMEOUT_TO_SYNC, 1000);
            int indexScan = sharedPreferences.getInt(PreferenceKeyConst.KEY_INDEX_SCAN_AT, 1);
            String fileTemp = sharedPreferences.getString(PreferenceKeyConst.KEY_ROOM_ID, "UnKnown");
            String fileDes = WifiUtils.getLocalDateTimeNow() + " - " + fileTemp + filetype;
            String text = sharedPreferences.getString(PreferenceKeyConst.KEY_FILE_NAME, fileDes);
            buttonScan.setEnabled(false);
            if (indexScan <= index + 1) {
                sharedPreferences.edit().putInt(PreferenceKeyConst.KEY_INDEX_SCAN_AT, indexScan + 1).apply();
                sharedPreferences.edit().putString(PreferenceKeyConst.KEY_FILE_NAME, text).apply();
            askAndStartScanWifi();
            mHandler.postDelayed(mRunnable, 6000);
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(PreferenceKeyConst.KEY_INDEX_SCAN_AT);
                editor.remove(PreferenceKeyConst.KEY_FILE_NAME);
                buttonScan.setEnabled(true);
                mHandler.removeCallbacks(mRunnable);
            }
        }
    };

    private Button buttonPref;
    private EditText editTextPassword;
    private LinearLayout linearLayoutScanResults;
    private TextView textViewScanResults;
    private WifiBroadcastReceiver wifiReceiver;

    public static void saveToPref(Map<String, WifiObjectCustom> wifiObjectCustomMap) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (null == wifiObjectCustomMap || wifiObjectCustomMap.isEmpty()) return;
        JSONArray array = new JSONArray();
        WifiObjectCustom wifiObjectCustom;
        for (Map.Entry<String, WifiObjectCustom> entry : wifiObjectCustomMap.entrySet()) {
            wifiObjectCustom = entry.getValue();
            if (null != wifiObjectCustom) {
                array.put(wifiObjectCustom.toJson());
            }
        }
        editor.putString(PreferenceKeyConst.KEY_BSSID_LIST, array.toString());
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_wi_main);
        sharedPreferences = getSharedPreferences(PreferenceKeyConst.APP_PREF, Context.MODE_PRIVATE);
        this.wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // Instantiate broadcast receiver
        this.wifiReceiver = new WifiBroadcastReceiver();

        // Register the receiver
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        //
        this.buttonState = this.findViewById(R.id.button_state);
        this.buttonScan = this.findViewById(R.id.button_scan);
        this.buttonPref = this.findViewById(R.id.button_show_pref);

        //this.editTextPassword.setVisibility(View.GONE);
        this.textViewScanResults = this.findViewById(R.id.textView_scanResults);
        this.linearLayoutScanResults = this.findViewById(R.id.linearLayout_scanResults);

        this.buttonState.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(PreferenceKeyConst.KEY_BSSID_LIST);
                editor.remove(PreferenceKeyConst.KEY_NO_CURRENT_AT);
                editor.remove(PreferenceKeyConst.KEY_INDEX_SCAN_AT);
                editor.remove(PreferenceKeyConst.KEY_FILE_NAME);
                textViewScanResults.setText("");
                editor.apply();
                Toast.makeText(MainActivity.this, "Reset cache success", Toast.LENGTH_SHORT)
                        .show();

            }
        });
//        this.buttonPref.setOnClickListener(new View.OnClickListener() {
//
//            @SneakyThrows
//            @Override
//            public void onClick(View v) {
//                readDataFromFile(new File(getExternalFilesDir(filepath), filetype));
//            }
//        });

        this.buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunnable.run();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(LOG_TAG, "onRequestPermissionsResult");
        switch (requestCode) {
            case MY_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(LOG_TAG, "Permission Granted: " + permissions[0]);
                    this.doStartScanWifi();
                } else {
                    Log.d(LOG_TAG, "Permission Denied: " + permissions[0]);
                }
                break;
            }
        }
    }

    private void readDataFromFile(File file) throws IOException {
        String out = "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            out = out + strLine;
        }
        textViewScanResults.setText(out);
//        Toast.makeText(MainActivity.this, out, Toast.LENGTH_SHORT)
//                .show();
    }

    private void askAndStartScanWifi() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23
            int permission1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (permission1 != PackageManager.PERMISSION_GRANTED) {
                Log.d(LOG_TAG, "Requesting Permissions");
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.ACCESS_NETWORK_STATE
                        }, MY_REQUEST_CODE);
                return;
            }
            Log.d(LOG_TAG, "Permissions Already Granted");
        }
        this.doStartScanWifi();
    }

    private void doStartScanWifi() {
        this.wifiManager.startScan();
    }

    class WifiBroadcastReceiver extends BroadcastReceiver {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.d(LOG_TAG, "onReceive()");
            Toast.makeText(MainActivity.this, "Scan Complete!", Toast.LENGTH_SHORT).show();
            boolean ok = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
            //if (ok) {
                Log.d(LOG_TAG, "Scan OK");
                List<ScanResult> list = wifiManager.getScanResults();
                //save to File Local
                int totalNum = sharedPreferences.getInt(PreferenceKeyConst.KEY_MAX_NUMBER_OF_WIFI, 8);
                //save-file
                try {
                    String fileFinal = sharedPreferences.getString(PreferenceKeyConst.KEY_FILE_NAME,
                            WifiUtils.getLocalDateTimeNow() + filetype);
                    File file = new File(getExternalFilesDir(filepath), fileFinal);
                    WifiHelper.saveDataToFile(file, totalNum, list, sharedPreferences);
                    readDataFromFile(file);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}
