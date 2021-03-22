package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListarExposicao extends AppCompatActivity {

    DBHelper db = new DBHelper(this);

    ListView listViewExp;
    List<ExpoClass> exp;

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_exposicao);
        getSupportActionBar().hide();
        listViewExp = (ListView) findViewById(R.id.listExpo);

        ListarExp();
        listViewExp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                ExpoClass expoClass = new ExpoClass();

                int id_To_Search = exp.get(position).CodExpo;

                Blundle dataBundle = new Bundle();
            }
        });
    }

    private void ListarExp() {
    }
}