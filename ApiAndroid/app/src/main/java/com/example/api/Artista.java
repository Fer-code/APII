package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.database.DBHelper;
import com.example.api.models.ArtClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Artista extends AppCompatActivity{

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private EditText cont;
    int contador;
    String vazio;
    ProgressBar progressBar;

    DBHelper db = new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artista);

        getSupportActionBar().hide();

        mTextViewResult = findViewById(R.id.text_view_result);
        cont = findViewById(R.id.pag);

        mQueue = Volley.newRequestQueue(this);

        progressBar = findViewById(R.id.progressBar3);
    }

    public void list(View v){
        Intent intent = new Intent(Artista.this, ListarArtista.class);
        startActivity(intent);
    }

    public void vai(View v){
        vazio = cont.getText().toString();

        if(vazio.isEmpty()) {
            Toast.makeText(Artista.this, "Informe uma página", Toast.LENGTH_SHORT).show();

        }else{
            jsonParse();
            cont.setText("");

        }
    }


    private void jsonParse() {

            contador = Integer.parseInt(cont.getText().toString());

            if (contador < 10) {


                String result = mTextViewResult.getText().toString();
                if(result==""){
                    progressBar.setVisibility(true ? View.VISIBLE : View.GONE);
                }else {

                }

                String url = "https://api.harvardartmuseums.org/Person?apikey=d66a16d0-0943-4495-a75d-7eca183f2c4f&page=" + contador;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONArray jsonArray = response.getJSONArray("records");

                                    for (int i = 0; i < 10; i++) {
                                        /*if(mTextViewResult==null){

                                        }*/
                                        JSONObject artist = jsonArray.getJSONObject(i);

                                        String alphasort = artist.getString("alphasort");
                                        String displayname = artist.getString("displayname");
                                        String birthplace = artist.getString("birthplace");
                                        String culture = artist.getString("culture");
                                        String datebegin = artist.getString("datebegin");
                                        String gender = artist.getString("gender");
                                        String deathplace = artist.getString("deathplace");

                                        mTextViewResult.append(alphasort + "\n");

                                        progressBar.setVisibility(false ? View.VISIBLE : View.GONE);

                                        try {
                                            db.addArtista(new ArtClass(alphasort, displayname, gender, birthplace, datebegin, deathplace, culture));
                                        } catch (Exception e) {

                                        }

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                mQueue.add(request);
            }
             else {
                Toast.makeText(Artista.this, "Página não disponível", Toast.LENGTH_SHORT).show();
                cont.setText("");
                mTextViewResult.setText("");
            }
    }
}