package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.database.DBHelper;
import com.example.api.models.ArtClass;

public class DadosArt extends AppCompatActivity {

    TextView nomeArt, nomeArtArt, genero, localNasc, localMorte, anoNasc, culture;
    int id_To_Update = 0;

    ArtClass artClass;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_art);
        getSupportActionBar().hide();

        nomeArt = findViewById(R.id.nomeArt);
        nomeArtArt = findViewById(R.id.nomeartart);
        genero = findViewById(R.id.genero);
        localNasc = findViewById(R.id.localNasc);
        localMorte = findViewById(R.id.localMorte);
        anoNasc = findViewById(R.id.anoNasc);
        culture = findViewById(R.id.cultura);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            artClass = new ArtClass();

            if (Value > 0) {

                Cursor rs = db.getData(Value);
                id_To_Update = Value;
                if (rs.moveToFirst()) {
                    artClass.setNomeArt(rs.getString(rs.getColumnIndex(db.ARTISTA_COLUMN_NAME)));
                    artClass.setNomeArtArt(rs.getString(rs.getColumnIndex(db.ARTISTA_COLUMN_NAMEART)));
                    artClass.setGenero(rs.getString(rs.getColumnIndex(db.ARTISTA_COLUMN_GENDER)));
                    artClass.setLocalNasc(rs.getString(rs.getColumnIndex(db.ARTISTA_COLUMN_LOCALNASC)));
                    artClass.setLocalMorte(rs.getString(rs.getColumnIndex(db.ARTISTA_COLUMN_LOCALMORT)));
                    artClass.setAnoNasc(rs.getString(rs.getColumnIndex(db.ARTISTA_COLUMN_ANONASC)));
                    artClass.setCulArt(rs.getString(rs.getColumnIndex(db.ARTISTA_COLUMN_CULTURA)));

                }

            } else {
                Toast.makeText(DadosArt.this, "Algo deu errado", Toast.LENGTH_SHORT).show();
            }

            nomeArt.setText(artClass.getNomeArt());

            nomeArtArt.setText(artClass.getNomeArtArt());

            genero.setText(artClass.getGenero());

            localNasc.setText(artClass.getLocalNasc());

            localMorte.setText(artClass.getLocalMorte());

            anoNasc.setText(artClass.getAnoNasc());

            culture.setText(artClass.getCulArt());


        }
    }
}