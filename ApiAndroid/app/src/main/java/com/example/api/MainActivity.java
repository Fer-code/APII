package com.example.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
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
import android.widget.TextView;
import android.widget.Toast;
import static android.hardware.Sensor.TYPE_LIGHT;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Aplicação do sensor
        sensorManager=(SensorManager)getSystemService(Service.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()== TYPE_LIGHT){
            //Caso o ambiente estiver escuro
            if(event.values[0]==0){
                Toast.makeText(this, "Por favor, diminua o brilho do celular", Toast.LENGTH_SHORT).show();

                //Caso o ambiente esteja claro
            }else{

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
    public void Mus(View v){
        Intent intent = new Intent(MainActivity.this, Museu.class);
        startActivity(intent);
    }
}