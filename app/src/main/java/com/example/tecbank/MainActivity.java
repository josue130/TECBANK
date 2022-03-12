package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import BaseDeDatos.SQLiteConnection;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    SQLiteConnection db = new SQLiteConnection(this);

    Button registrarse, login;

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

        login = findViewById(R.id.button2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usuario = findViewById(R.id.usuario);
                EditText password = findViewById(R.id.password);
                try{
                    Cursor cursor = db.login(usuario.getText().toString(),password.getText().toString());
                    if(cursor.getCount()>0){
                        // Si el usuario y contraseña existe desde aqui se manda al otro
                        Toast.makeText(getApplicationContext(),"Acceso concedido",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Usuario no encontrado",Toast.LENGTH_LONG).show();
                    }
                    usuario.setText("");
                    usuario.findFocus();
                }
                catch (SQLException e){

                }



            }
        });


//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);


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