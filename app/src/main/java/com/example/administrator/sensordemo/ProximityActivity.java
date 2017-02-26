package com.example.administrator.sensordemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/26.
 */

public class ProximityActivity extends AppCompatActivity implements SensorEventListener {
    private TextView proximity_tv;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);
        proximity_tv = (TextView) findViewById(R.id.proximity_tv);
        //获取传感器对象
        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    //传感器数据改变时回调
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //判断传感器类别
        switch (sensorEvent.sensor.getType()){
            case Sensor.TYPE_PROXIMITY://邻近传感器
                proximity_tv.setText(String.valueOf(sensorEvent.values[0]));
                break;
            default:
                break;
        }
    }

    //页面获取焦点时回调
    protected void onResume(){
        super.onResume();
        //注册邻近传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_UI);//采集率
    }

    //暂停activity时回调
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    //传感器精度改变时回调
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
