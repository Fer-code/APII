package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.api.database.DBHelper;
import com.example.api.models.ExpoClass;

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
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                ExpoClass ec = new ExpoClass();

                int id_To_Search = exp.get(position).CodExpo;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), DadosExp.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
                finish();

            }
        });
    }

    private void ListarExp() {
        exp = db.listaTodosExpo();
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(ListarExposicao.this, android.R.layout.simple_expandable_list_item_1, arrayList);

        listViewExp.setAdapter(adapter);

        for(ExpoClass ec : exp){
            arrayList.add(ec.getTitleExpo());
            adapter.notifyDataSetChanged();
        }
    }
}