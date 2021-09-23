package com.example.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.database.DBHelper;
import com.example.api.interfaces.MuseumInterface;
import com.example.api.interfaces.UserInterface;
import com.example.api.models.MuseuClass;
import com.example.api.models.UserClass;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.security.cert.CertificateException;

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

public class CadMuseu extends AppCompatActivity implements
        FetchAddressTask.OnTaskCompleted {

    //------------------------Localização------------
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private Button btnBuscaLocal, btnSendAPI;

    private String lastLatitude = "";
    private String lastLongitude = "";
    private String lastAdress = "";
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private boolean mTrackingLocation;
    ProgressBar pb;

    //----------------------API---------------
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String COD_KEY = "cod_key";
    SharedPreferences sharedpreferences;
    int codBancoApi;

    private TextView txtResultado;
    private EditText edtName;
    private EditText txtImputResultado;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_museu);
        getSupportActionBar().hide();
        //-----------------Sharedpreferencies: Pegar codigo do usuário----------------------

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        String codU = sharedpreferences.getString(COD_KEY, null);

        //------------------------------------------------------------------------------------

        btnBuscaLocal = findViewById(R.id.BuscarLM);
        edtName = findViewById(R.id.txtNomeMuseu);
        txtResultado = findViewById(R.id.txtlocalM);
        txtImputResultado = findViewById(R.id.txtAdressMuseu);
        pb = findViewById(R.id.progressBar2);
        btnSendAPI = findViewById(R.id.SendMuseuToAPI);

        //----------LOCALIZAÇÃO-------------------------------------------------------------
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        btnBuscaLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTrackingLocation) {
                    startTrackingLocation();
                } else {
                    stopTrackingLocation();
                }
            }
        });

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (mTrackingLocation) {
                    new FetchAddressTask(CadMuseu.this, CadMuseu.this)
                            .execute(locationResult.getLastLocation());
                }
            }
        };
        //-----------API-----------------------------------------------------------------
        btnSendAPI.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (txtResultado.getText().toString().isEmpty() || edtName.getText().toString().isEmpty()) {
                    if(txtImputResultado.getText().toString().isEmpty() || edtName.getText().toString().isEmpty()){
                        Toast.makeText(CadMuseu.this, "Insira todos os valores", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        codBancoApi = Integer.parseInt(codU);
                        //-------------api
                        postMuseu(edtName.getText().toString(), txtImputResultado.getText().toString(), codBancoApi);

                        //-------------banco
                        db.addMuseu(new MuseuClass(edtName.getText().toString(), txtImputResultado.getText().toString(), codBancoApi));
                        Toast.makeText(CadMuseu.this, "adicionado com sucesso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CadMuseu.this, Museu.class);
                        startActivity(intent);
                        finish();

                }
                }
                else {
                    codBancoApi = Integer.parseInt(codU);
                    //-------------api
                    postMuseu(edtName.getText().toString(), txtResultado.getText().toString(), codBancoApi);

                    //-------------banco
                    db.addMuseu(new MuseuClass(edtName.getText().toString(), txtResultado.getText().toString(), codBancoApi));
                    Toast.makeText(CadMuseu.this, "adicionado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadMuseu.this, Museu.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

        private void postMuseu(String name, String address, int user) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://192.168.0.6:23114/api/V1/")
                    .client(getUnsafeOkHttpClient().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MuseumInterface retrofitAPI = retrofit.create(MuseumInterface.class);

            MuseuClass modal = new MuseuClass(name, address, user);

            Call<MuseuClass> call = retrofitAPI.createPost(modal);

            call.enqueue(new Callback<MuseuClass>() {
                @Override
                public void onResponse(Call<MuseuClass> call, Response<MuseuClass> response) {
                    Toast.makeText(CadMuseu.this, "Data added to API", Toast.LENGTH_SHORT).show();

                    MuseuClass responseFromAPI = response.body();

                    String responseString = " idM: " + "nameM : " + responseFromAPI.getNameM() +
                            "\n" + "addressM : " + responseFromAPI.getAddressM() +
                            "\n" + "userM : " + responseFromAPI.getUserM();

                    Toast.makeText(CadMuseu.this, responseString, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<MuseuClass> call, Throwable t) {

                    Toast.makeText(CadMuseu.this, "Error found is : " + t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
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


    //----------------LOCALIZAÇÃO-----------------------------------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // Permissão garantida
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    startTrackingLocation();
                } else {
                    Toast.makeText(this,
                            "Localização negada",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void stopTrackingLocation() {
    }

    private void startTrackingLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(),
                            mLocationCallback,
                            null);

            //Se for true roda o visible, se for false roda o gone
            pb.setVisibility(true ? View.VISIBLE : View.GONE);
        }
    }

    //Atualizacoes de localização
    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @Override
    public void onTaskCompleted(String[] result) {
        if (mTrackingLocation) {
            // Update the UI
            lastLatitude = result[1];
            lastLongitude = result[2];
            lastAdress = result[0];
            txtResultado.setText(getString(R.string.address_text,
                    lastAdress));

            //Esconder a progressBar
            pb.setVisibility(false ? View.VISIBLE : View.GONE);
        }
    }
}