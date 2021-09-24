package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.database.DBHelper;
import com.example.api.interfaces.MuseumInterface;
import com.example.api.models.ArtClass;
import com.example.api.models.MuseuClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Museu extends AppCompatActivity {
    //----------------------API---------------
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String COD_KEY = "cod_key";
    SharedPreferences sharedpreferences;
    DBHelper db = new DBHelper(this);

    TextView mTextViewResult;
    List<MuseuClass> museu;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    int codUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museu);

        getSupportActionBar().hide();

        mTextViewResult = findViewById(R.id.MuseusUser);

        //----------------------------------
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        String codU = sharedpreferences.getString(COD_KEY, null);
        codUser=Integer.parseInt(codU);

        jsonParse();

        //-------------------------------------
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.0.6:1078/api/V1/")
                .client(getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MuseumInterface museumInterface = retrofit.create(MuseumInterface.class);
        Call<List<MuseuClass>> call = museumInterface.getMuseu();

        call.enqueue(new Callback<List<MuseuClass>>() {
            @Override
            public void onResponse(Call<List<MuseuClass>> call, Response<List<MuseuClass>> response) {
              if(!response.isSuccessful())  {
                  mTextViewResult.setText("Name" + response.code());
              }

              List<MuseuClass> posts = response.body();
              for(MuseuClass post : posts){
                  String content = "";
                  content += " Nome: " + post.getNameM() + "\n";
                  content += " Address " + post.getAddressM() + "\n" + "\n";

                  mTextViewResult.append(content);
              }
            }

            @Override
            public void onFailure(Call<List<MuseuClass>> call, Throwable t) {
                mTextViewResult.setText(t.getMessage());
            }
        });

    }
    private void jsonParse() {
            String result = mTextViewResult.getText().toString();


    }

    //------Método para o erro de segurança
    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void CadM(View v){
        Intent intent = new Intent(this, CadMuseu.class);

        startActivity(intent);
    }
}