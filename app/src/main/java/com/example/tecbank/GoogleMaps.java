package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;

public class GoogleMaps extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Sanjose_centro = new LatLng(9.929287558472666, -84.09154474934195);
        LatLng Sanjose_muli_este = new LatLng(9.946158361694895, -84.14534897772423);
        LatLng Sanjose_LP = new LatLng(9.962589999927257, -84.05558046661459);
        LatLng Alajuela_centro = new LatLng(10.018699637705014, -84.21463094063427);
        LatLng Heredia_Oxi = new LatLng(9.994221041796814, -84.13080626107266);
        LatLng Cartago_MallPM = new LatLng(9.867269185178674, -83.94224679545036);

        mMap.addMarker(new MarkerOptions().position(Sanjose_centro).title("Sucursal TecBank"));
        mMap.addMarker(new MarkerOptions().position(Sanjose_muli_este).title("Sucursal TecBank"));
        mMap.addMarker(new MarkerOptions().position(Sanjose_LP).title("Sucursal TecBank"));
        mMap.addMarker(new MarkerOptions().position(Alajuela_centro).title("Sucursal TecBank"));
        mMap.addMarker(new MarkerOptions().position(Heredia_Oxi).title("Sucursal TecBank"));
        mMap.addMarker(new MarkerOptions().position(Cartago_MallPM).title("Sucursal TecBank"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(Sanjose_centro));

    }
}