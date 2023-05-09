package com.example.sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    int whip=0;
    SensorManager sensorManager2;
    Sensor sensor2;
    SensorEventListener sensorEventListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensor==null)
            finish();

        sensorManager2=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor2=sensorManager2.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(sensor2==null)
            finish();
        sensorEventListener2=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0]<sensor2.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);

                }else{
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        start();

        sensorEventListener= new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x=sensorEvent.values[0];
                System.out.println("Valor giro" +x);
                if(x<-5 && whip==0){
                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.CYAN);


                }else if (x>5 && whip==1){
                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
                if(whip==2){
                    sound();
                    whip=0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        start();
    }


    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }

    private void sound(){
        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.among);
        mediaPlayer.start();
    }

    private void start(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager2.registerListener(sensorEventListener2,sensor2, 2000*1000);

    }
    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
        sensorManager2.unregisterListener(sensorEventListener2);
    }
}