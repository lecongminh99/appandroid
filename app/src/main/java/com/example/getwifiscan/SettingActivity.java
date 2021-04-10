package com.example.getwifiscan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.getwifiscan.util.PreferenceKeyConst;

public class SettingActivity extends AppCompatActivity {
    Button btnSave;
    EditText editTextNumberOfWifi;
    EditText editTextTimeOut;
    EditText editTextNumberOfRoom;
    int numberWifi;
    int timeOut;
    String roomID;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedPreferences = getSharedPreferences(PreferenceKeyConst.APP_PREF, Context.MODE_PRIVATE);
        editTextNumberOfWifi = findViewById(R.id.edit_text_num_of_wifi);
        editTextTimeOut = findViewById(R.id.edit_time_out);
        editTextNumberOfRoom = findViewById(R.id.edit_roomID);
        btnSave = findViewById(R.id.button_save_setting);
        int numberWifiExisted = sharedPreferences.getInt(PreferenceKeyConst.KEY_MAX_NUMBER_OF_WIFI, 0);
        int timeOutExisted = sharedPreferences.getInt(PreferenceKeyConst.KEY_TIMEOUT_TO_SYNC, 0);
        String numberOfRoom = sharedPreferences.getString(PreferenceKeyConst.KEY_ROOM_ID, "None");

        editTextNumberOfWifi.setText(String.valueOf(numberWifiExisted));
        editTextTimeOut.setText(String.valueOf(timeOutExisted));
        editTextNumberOfRoom.setText(numberOfRoom);
        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            numberWifi = Integer.parseInt(editTextNumberOfWifi.getText().toString());
            timeOut = Integer.parseInt(editTextTimeOut.getText().toString());
            roomID = editTextNumberOfRoom.getText().toString();
            editor.putInt(PreferenceKeyConst.KEY_MAX_NUMBER_OF_WIFI, numberWifi);
            editor.putInt(PreferenceKeyConst.KEY_TIMEOUT_TO_SYNC, timeOut);
            editor.putString(PreferenceKeyConst.KEY_ROOM_ID, roomID);
            editor.apply();
            Toast.makeText(SettingActivity.this, "Cập nhật setting thành công!", Toast.LENGTH_SHORT).show();
        });
    }
}
