package com.example.sayac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorPrivacyManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    int counter = 0;
    Settings settings;
    Button buttonMinus, buttonPlus, buttonSettings;
    TextView sayac;

    Vibrator vibrator = null;
    MediaPlayer mediaPlayer = null;
    Sensor sensor = null;
    SensorManager sensorManager = null;
    SensorEventListener sensorEventListener = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = getApplicationContext();
        settings = Settings.getSettings(context);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        sayac = (TextView) findViewById(R.id.sayac);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(context, R.raw.song);

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValue(1);
            }
        });
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValue(-1);
            }
        });
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                float sum = Math.abs(x) +Math.abs(y)+Math.abs(z);
                if(sum > 16){
                    settings.currentValue = settings.lowerLimit;
                    sayac.setText(String.valueOf(settings.currentValue));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }
        public void updateValue(int fark){
            if(fark < 0) {
                if (settings.currentValue + fark < settings.lowerLimit) {
                    settings.currentValue = settings.lowerLimit;
                    if(settings.lowerVib){
                        alertVib();
                    }
                    if(settings.lowerSound){
                        alertSound();
                    }
                } else
                    settings.currentValue += fark;
                }
            if(fark > 0){
                if(settings.currentValue + fark > settings.upperLimit){
                        settings.currentValue = settings.upperLimit;
                    if(settings.upperVib){
                        alertVib();
                    }
                    if(settings.upperSound){
                        alertSound();
                    }
                }
                else
                    settings.currentValue += fark;
            }
            sayac.setText(String.valueOf(settings.currentValue));
        }
        @Override
        protected void onResume() {
            super.onResume();
            sayac.setText(String.valueOf(settings.currentValue));
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        @Override
        protected void onPause() {
            super.onPause();
            settings.SaveValues();
            sensorManager.unregisterListener(sensorEventListener,sensor);
        }
        public void alertSound(){
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }
        public void alertVib(){
            vibrator.vibrate(1000);
        }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if(action == KeyEvent.ACTION_DOWN)
                    updateValue(-5);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if(action == KeyEvent.ACTION_UP)
                    updateValue(5);
                return true;
        }
        return super.dispatchKeyEvent(event);
    }
}