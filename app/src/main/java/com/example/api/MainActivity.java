package com.example.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import static android.hardware.Sensor.TYPE_LIGHT;


public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lighEventListener;
    private View root;
    private float maxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(TYPE_LIGHT);

        if (lightSensor == null) {
            Toast.makeText(this, "O dispositivo não tem sensor de luminosidade", Toast.LENGTH_SHORT).show();
            //finish();
        }

        maxValue = lightSensor.getMaximumRange();

        lighEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                int newValue = (int) (255f * value / maxValue);
                root.setBackgroundColor(Color.rgb(newValue, newValue, newValue));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

        @Override
        protected  void onResume(){
        super.onResume();
        sensorManager.registerListener(lighEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }

    @Override
    protected  void onPause(){
        super.onPause();
        sensorManager.unregisterListener(lighEventListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //-------------------------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.local:
                Intent intent = new Intent(MainActivity.this, LocalizacaoA.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void Expo(View v){
        Intent intent = new Intent(MainActivity.this, Exposicao.class);
        startActivity(intent);
    }
    public void Art(View v){
        Intent intent = new Intent(MainActivity.this, Artista.class);
        startActivity(intent);
    }
}