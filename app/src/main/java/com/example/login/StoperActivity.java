package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class StoperActivity extends AppCompatActivity {
    Chronometer chronometer;
    long pause;
    boolean running;
    Button btnS, btnP, btnR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoper);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        btnS = findViewById(R.id.btnStart);
        btnP = findViewById(R.id.btnPause);
        btnR = findViewById(R.id.btnReset);

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pause);
                    chronometer.start();
                    running = true;
                }
            }
        });

        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running) {
                    chronometer.stop();
                    pause = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
            }
        });

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pause = 0;
            }
        });
    }
}