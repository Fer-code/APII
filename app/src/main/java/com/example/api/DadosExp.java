package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DadosExp extends AppCompatActivity {

    TextView tituloExpo, DIExpo, DFExpo, DescExpo, TempExpo;
    int id_To_Update = 0;

    ExpoClass expoClass;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_exp);
        getSupportActionBar().hide();

        tituloExpo = findViewById(R.id.titulo);
        DIExpo = findViewById(R.id.DIesc);
        DFExpo = findViewById(R.id.DFesc);
        DescExpo = findViewById(R.id.descesc);
        TempExpo = findViewById(R.id.tempesc);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int Value = extras.getInt("id");

            expoClass = new ExpoClass();

            if (Value > 0){
                Cursor rs = db.getData(Value);
                id_To_Update = Value;
                if(rs.moveToFirst()){
                    expoClass.setTitleExpo(rs.getString(rs.getColumnIndex(db.EXPO_COLUMN_TITLE)));
                    expoClass.setDIExpo(rs.getString(rs.getColumnIndex(db.EXPO_COLUMN_DI)));
                    expoClass.setDFExpo(rs.getString(rs.getColumnIndex(db.EXPO_COLUMN_DF)));
                    expoClass.setDesc(rs.getString(rs.getColumnIndex(db.EXPO_COLUMN_DESC)));
                    expoClass.setTemp(rs.getString(rs.getColumnIndex(db.EXPO_COLUMN_ORDEMTEMP)));
                }

                tituloExpo.setText(expoClass.getTitleExpo());
                DIExpo.setText(expoClass.getDIExpo());
                DFExpo.setText(expoClass.getDFExpo());
                DescExpo.setText(expoClass.getDesc());
                TempExpo.setText(expoClass.getTemp());

            } else {
                Toast.makeText(DadosExp.this, "Algo de errado", Toast.LENGTH_SHORT).show();
            }
        }

    }
}