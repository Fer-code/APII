package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
    }

    public void CadInt (View v){
        Intent cadastro = new Intent(this, CadUsuario.class);
        startActivity(cadastro);
    }
    public void GoMain (View a){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}