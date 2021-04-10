package com.example.getwifiscan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    Button btnRepeatTutorial;
    Button btnGetWifiTutorial;
    Button btnSetting;
    Button buttonProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnRepeatTutorial = findViewById(R.id.button_repeat_tutorial);
        btnGetWifiTutorial = findViewById(R.id.button_get_wifi_tutorial);
        btnSetting = findViewById(R.id.button_setting);
        buttonProgressBar = findViewById(R.id.button_progress);
        btnRepeatTutorial.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RepeatingActivity.class);
            startActivity(intent);
        });
        btnGetWifiTutorial.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });
        btnSetting.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
            startActivity(intent);
        });
        btnSetting.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
            startActivity(intent);
        });
        buttonProgressBar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProgressBarActivity.class);
            startActivity(intent);
        });
    }
}
