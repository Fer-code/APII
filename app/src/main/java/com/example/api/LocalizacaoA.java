package com.example.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocalizacaoA extends AppCompatActivity implements
        FetchAddressTask.OnTaskCompleted {

    public final static String ENDERECO = "com.example.api.END";
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    private static final String FILE_NAME = "example.txt";

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";

    private Button btnBuscaLocal; //Usado para carregar a geolocalização
    private TextView txtResultado;

    private String lastLatitude = "";
    private String lastLongitude = "";
    private String lastAdress = "";


    private static final String LASTADRESS_KEY = "adress";
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private boolean mTrackingLocation;

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao);
        getSupportActionBar().hide();

        btnBuscaLocal = findViewById(R.id.btncarregarlocal);
        txtResultado = findViewById(R.id.txtlocalizacao);
        pb = findViewById(R.id.progressBar2);

        //
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //Chamar o metodo de começar a buscar
        //iniciarChamada();

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
                // If tracking is turned on, reverse geocode into an address
                if (mTrackingLocation) {
                    new FetchAddressTask(LocalizacaoA.this, LocalizacaoA.this)
                            .execute(locationResult.getLastLocation());
                }
            }
        };
    }


    public void buscarMuseus(View b) {
        String localPreenchido = txtResultado.getText().toString();
        if(localPreenchido==""){
            Toast.makeText(this, getString(R.string.temquecarregar) , Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "ido ido ido Gabriel é fidido" , Toast.LENGTH_LONG).show();

        }
    }

    //Método para verificar se a localização foi permitida ou não, e dependendo fará uma coisa ou outra
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

    //Botao para chamar a localização-btncarregarlocal
    /*public void iniciarChamada(){
        if (!mTrackingLocation) {
            startTrackingLocation();
        } else {
            stopTrackingLocation();
        }
            }*/

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