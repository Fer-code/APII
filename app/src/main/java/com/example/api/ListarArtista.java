package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListarArtista extends AppCompatActivity {

    DBHelper db = new DBHelper(this);

    ListView listViewArt;
    List<ArtClass> art;


    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_artista);
        getSupportActionBar().hide();
        listViewArt = (ListView) findViewById(R.id.listArt);

        ListarArt();

        listViewArt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                int id_To_Search = art.get(position).codArt;


                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), DadosArt.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
                finish();
            }
        });

    }

    private void ListarArt() {

            art = db.listaTodosArtistas();

            arrayList = new ArrayList<String>();

            adapter = new ArrayAdapter<String>(ListarArtista.this, android.R.layout.simple_list_item_1, arrayList);

            listViewArt.setAdapter(adapter);

            for(ArtClass c  : art){

                arrayList.add(c.getNomeArtArt() + " - " + c.getAnoNasc());
                adapter.notifyDataSetChanged();
            }
    }

}