package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Museu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museu);

        getSupportActionBar().hide();

    }
    public void CadM(View v){
        Intent intent = new Intent(this, CadMuseu.class);

        startActivity(intent);
    }
}