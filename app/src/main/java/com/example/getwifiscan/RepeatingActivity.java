package com.example.getwifiscan;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RepeatingActivity extends AppCompatActivity {
    private final Handler mHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(RepeatingActivity.this, "Repeating", Toast.LENGTH_SHORT)
                    .show();
            mHandler.postDelayed(mRunnable, 5000);
        }
    };
    Button btnStartRepeat;
    Button btnStopRepeat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);
        btnStartRepeat = findViewById(R.id.button_start_repeat);
        btnStopRepeat = findViewById(R.id.btn_stop_repeat);
        btnStartRepeat.setOnClickListener(v -> {
            mRunnable.run();
        });
        btnStopRepeat.setOnClickListener(v -> {
            mHandler.removeCallbacks(mRunnable);
        });
    }
}
