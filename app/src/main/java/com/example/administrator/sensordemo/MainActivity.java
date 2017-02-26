package com.example.administrator.sensordemo;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView sensor_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensor_text = (TextView)findViewById(R.id.sensor_text);

    }

    public void getAllSensor(View v){
        //获取传感器SensorManager对象
        SensorManager sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        //获取手机里的所有传感器种类
        List<Sensor> sensors=sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor:sensors){
            sensor_text.append(sensor.getName()+"\n");
        }
    }
    //加速度传感器
    public void getAccelerometer(View v){
        Intent intent=new Intent(this,AccelerometerActivity.class);
        startActivity(intent);
    }

    //邻近传感器
    public void getProximity(View v){
        Intent intent=new Intent(this,ProximityActivity.class);
        startActivity(intent);
    }

}
