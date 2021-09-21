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
import com.example.api.models.ExpoClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Exposicao extends AppCompatActivity {

    private TextView txtViewResult;
    private RequestQueue mQueue;
    private EditText contE;
    int contador;
    String vazioE;
    ProgressBar progressBar;

    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposicao);

        getSupportActionBar().hide();

        txtViewResult = findViewById(R.id.text_view_result);
        contE = findViewById(R.id.pag);


        mQueue = Volley.newRequestQueue(this);

        progressBar = findViewById(R.id.progressBar4);

    }

    public void list(View v) {
        Intent intent = new Intent(Exposicao.this, ListarExposicao.class);
        startActivity(intent);
    }

    public void vai(View v) {
        vazioE = contE.getText().toString();
        if (vazioE.isEmpty()) {
            Toast.makeText(Exposicao.this, "Informe uma página", Toast.LENGTH_SHORT).show();

        } else {
            jsonParse();
            contE.setText("");
        }

    }

    private void jsonParse() {
        contador = Integer.parseInt(contE.getText().toString());

            if (contador < 10) {

                String result = txtViewResult.getText().toString();
                if (result == "") {
                    progressBar.setVisibility(true ? View.VISIBLE : View.GONE);
                } else {}

                String url = "https://api.harvardartmuseums.org/exhibition?apikey=d66a16d0-0943-4495-a75d-7eca183f2c4f&page=" + contador;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("records");

                                    for (int i = 0; i < 10; i++) {
                                        JSONObject exposicao = jsonArray.getJSONObject(i);

                                        String title = exposicao.getString("title");
                                        String begindate = exposicao.getString("begindate");
                                        String enddate = exposicao.getString("enddate");
                                        String description = exposicao.getString("description");
                                        String temporalorder = exposicao.getString("temporalorder");

                                        txtViewResult.append(title + "\n");

                                        progressBar.setVisibility(false ? View.VISIBLE : View.GONE);

                                        try {
                                            db.addExpo(new ExpoClass(title, begindate, enddate, description, temporalorder));
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
            } else {
                Toast.makeText(this, "Página não disponível", Toast.LENGTH_SHORT).show();
                contE.setText("");
                txtViewResult.setText("");
            }
        }
    }
