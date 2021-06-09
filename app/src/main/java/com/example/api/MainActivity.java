package com.example.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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