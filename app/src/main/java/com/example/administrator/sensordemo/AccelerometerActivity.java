package com.example.administrator.sensordemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/26.
 */

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    private TextView accelerometer_tv;
    private SensorManager sensorManager;
    private float[] gravity=new float[3];//定义大小为3的float数组分别存放x,y,z的值
    private Vibrator vibrator;//震动
    Float x = null;
    Float y = null;
    Float z = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        accelerometer_tv = (TextView)findViewById(R.id.accelerometer_tv);
        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    //传感器精度变化时回调
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //传感器数据变化时回调
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        switch (sensorEvent.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER://加速度传感器
                final float alpha= (float) 0.8;
                gravity[0] =alpha*gravity[0]+(1-alpha)*sensorEvent.values[0];
                gravity[1] =alpha*gravity[1]+(1-alpha)*sensorEvent.values[1];
                gravity[2] =alpha*gravity[2]+(1-alpha)*sensorEvent.values[2];

                //减掉重力加速度
                x=sensorEvent.values[0]-gravity[0];
                y=sensorEvent.values[1]-gravity[1];
                z=sensorEvent.values[2]-gravity[2];

                String accelerometer="加速度传感器\n"+"x:"
                        +(sensorEvent.values[0]-gravity[0])+"\n"+"y:"
                        +(sensorEvent.values[1]-gravity[1])+"\n"+"z:"
                        +(sensorEvent.values[2]-gravity[2]);
                accelerometer_tv.setText(accelerometer);
                care();
                break;
            //重力加速度9.81m/s^2，只受到重力作用的情况下，自由下落的加速度
            case Sensor.TYPE_GRAVITY://重力传感器
                gravity[0]=sensorEvent.values[0];//单位m/^2
                gravity[1]=sensorEvent.values[1];
                gravity[2]=sensorEvent.values[2];
                break;
            default:
                break;
        }

    }

    public void care(){
        //绝对值
        float x1=Math.abs(x);
        float y1=Math.abs(y);
        float z1=Math.abs(z);
        //达到一定值时震动
        if(x1>10||y1>10||z1>10){
        /*
         * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
         * */
            vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern={100,400,100,400};// 停止 开启 停止 开启
            vibrator.vibrate(pattern,-1);
        }
    }
    protected void onResume(){
        super.onResume();
        //注册加速度传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),//传感器类型，加速度传感器
                SensorManager.SENSOR_DELAY_UI);//采集频率
        //注册重力传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    //暂停Activity,回调
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    protected void onStop() {
        super.onStop();
        vibrator.cancel();
    }
}
