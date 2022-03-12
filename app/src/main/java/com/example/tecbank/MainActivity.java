package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import BaseDeDatos.SQLiteConnection;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    public SQLiteConnection db;
    Button registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Para ejecutar la prueba de google maps  tienen que cambiar activity_main por googlemaps
        // Ademas tiene que descomentar la lineas 28 y 29
        setContentView(R.layout.activity_main);

        registrarse = (Button) findViewById(R.id.registrarse);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(), register.class);
                startActivity(i);
            }
        });

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        //db = new SQLiteConnection(this);
        //SQLiteDatabase conection = db.getWritableDatabase();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydeney = new LatLng(-34,151);
        mMap.addMarker(new MarkerOptions()
                .position(sydeney)
                .title("Maker in sydeney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydeney));

    }
}