package com.example.tecbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Para que no se este ejecutando google maps solo tiene que cambiar setContentView(R.layout.activity_main);
        // Ademas comentar las lineas 23,25,26 y de la 29 a la 38 :)
        // Si le tira un error relacinado con eso me avisan porfa :)
        setContentView(R.layout.googlemaps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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