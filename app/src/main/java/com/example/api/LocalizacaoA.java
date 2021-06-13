package com.example.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocalizacaoA extends AppCompatActivity implements
        FetchAddressTask.OnTaskCompleted{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao);
        getSupportActionBar().hide();

        btnBuscaLocal = findViewById(R.id.btncarregarlocal);
        txtResultado = findViewById(R.id.txtlocalizacao);

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
        }
    }
}

/*

btnmLocation.setOnClickListener(new View.OnClickListener() {

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
                    new FetchAddressTask(Local.this, Local.this)
                            .execute(locationResult.getLastLocation());
                }
            }
        };

        mPreferences = getSharedPreferences(PREFERENCIAS_NAME, MODE_PRIVATE);
        recuperar();

    }



    public void switchClick(View v)
    {
        podeGeo = switcher.isChecked();
        SharedPreferences settings = getSharedPreferences(PREFERENCIAS_NAME , 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREFERENCIAS_VALOR, podeGeo);
        editor.commit();

        if(podeGeo == false){
            Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Permissão aceita", Toast.LENGTH_SHORT).show();

        }

    }
    private void recuperar()
    {
        SharedPreferences settings = getSharedPreferences(PREFERENCIAS_NAME , 0);
        podeGeo = settings.getBoolean(PREFERENCIAS_VALOR, false);
        switcher.setChecked(podeGeo);
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

            txtMLocation.setText(getString(R.string.address_text,
                    getString(R.string.carregando), null, null));
            btnmLocation.setText(R.string.buscar);

        }
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void stopTrackingLocation() {
        if (mTrackingLocation) {
            mTrackingLocation = false;
            btnmLocation.setText(R.string.iniciar);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TRACKING_LOCATION_KEY, mTrackingLocation);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startTrackingLocation();
                } else {
                    Toast.makeText(this, R.string.permissao_negada,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onTaskCompleted(String[] result) {
        if (mTrackingLocation) {
            // Update the UI
            lastLatitude = result[1];
            lastLongitude = result[2];
            lastAdress = result[0];
            txtMLocation.setText(getString(R.string.address_text,
                    lastAdress, lastLatitude, lastLongitude));
        }
    }



    public void continuar(View continuar){
        txtMLocation = findViewById(R.id.txtLocal);

        String txtLocal = txtMLocation.getText().toString();

            Toast.makeText(this, "Endereço Registrado com sucesso estamos a caminho", Toast.LENGTH_SHORT).show();

    }

 */