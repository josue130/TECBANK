package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    public SQLiteConnection db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Para ejecutar la prueba de google maps  tienen que cambiar activity_main por googlemaps
        // Ademas tiene que descomentar la lineas 27 y 28
        //setContentView(R.layout.googlemaps);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        db = new SQLiteConnection(this);
        SQLiteDatabase conection = db.getWritableDatabase();
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