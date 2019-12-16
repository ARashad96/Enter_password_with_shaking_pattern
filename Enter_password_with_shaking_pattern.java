package com.ARashad96.androidbeginnerdeveloperkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Enter_password_with_shaking_pattern extends AppCompatActivity implements SensorEventListener {
    Boolean c1 = false;
    Boolean c2 = false;
    Boolean c3 = false;
    Button reset;
    Button start;
    Button github;
    Button info;
    ProgressBar progress;
    TextView password;
    TextView timer;
    int counter1 = 0;
    int counter2 = 0;
    int counter3 = 0;
    int x;
    long progresss;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 1000;
    private static final String TAG = "MainActivity";
    CountDownTimer cTimer = null;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enter_password_with_shaking_pattern);

        password = findViewById(R.id.password);
        timer = findViewById(R.id.timer);
        progress = findViewById(R.id.progress);
        progress.setProgress(100);
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setText("Enter password");
                counter1 = 0;
                counter2 = 0;
                counter3 = 0;
                cTimer = null;
                cancelTimer();
                progress.setProgress(100);
                timer.setText("Time");
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.registerListener(Enter_password_with_shaking_pattern.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                if (c1 == false) {
                    startTimer1();
                } else if (c2 == true) {
                    startTimer2();
                } else if (c3 == true) {
                    startTimer3();
                }
            }
        });


        github = findViewById(R.id.github);
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ARashad96/Enter_password_with_shaking_pattern"));
                startActivity(intent);
            }
        });
        info = findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(Enter_password_with_shaking_pattern.this)
                        .setIcon(R.drawable.profile)
                        .setTitle("App info")
                        .setMessage("This app is authenticating the user with a password of (1 2 3) using listview, button, toast and linearlayout.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d(TAG, "onSensorChanged: X:" + sensorEvent.values[0] + "Y: " + sensorEvent.values[1] + "Z: " + sensorEvent.values[2]);
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speedx = Math.abs(x - last_x) / diffTime * 10000;
                float speedy = Math.abs(y - last_y) / diffTime * 10000;

                if (speedx > SHAKE_THRESHOLD && speedy > SHAKE_THRESHOLD) {
                    if (cTimer != null) {

                    } else {
                        startTimer1();
                    }
                    if (c1 == false) {
                        counter1++;
                    } else if (c2 == true) {
                        counter2++;
                    } else {
                        counter3++;
                    }
                    password.setText("" + counter1 + " " + counter2 + " " + counter3);
                }
            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }

    private void startTimer1() {
        start.setEnabled(false);
        reset.setEnabled(false);
        cTimer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + " sec");
                progresss = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                x = (int) progresss * 20;
                progress.setProgress(x);
            }

            public void onFinish() {
                c1 = true;
                c2 = true;

                startTimer2();
            }
        };
        cTimer.start();
    }

    private void startTimer2() {
        cTimer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + " sec");
                progresss = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                x = (int) progresss * 20;
                progress.setProgress(x);
            }

            public void onFinish() {
                //password.setText("Wrong password");
                //counter1=0;

                c2 = false;
                c3 = true;

                startTimer3();
            }
        };
        cTimer.start();
    }

    private void startTimer3() {
        cTimer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + " sec");
                progresss = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                x = (int) progresss * 20;
                progress.setProgress(x);
            }

            public void onFinish() {
                //password.setText("Wrong password");
                //counter1=0;
                c1 = false;
                c2 = false;
                c3 = false;
                if (counter1 == 1 && counter2 == 2 && counter3 == 3) {
                    password.setText("Correct password");
                } else {
                    password.setText("Wrong password");
                }
                start.setEnabled(true);
                reset.setEnabled(true);
            }
        };
        cTimer.start();
    }

    private void cancelTimer() {
        if (cTimer != null) {
            cTimer.cancel();
            progress.setProgress(x);
        }
    }
}