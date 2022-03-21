package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import BaseDeDatos.SQLiteConnection;

public class pantallatransferencias2 extends AppCompatActivity {

    ImageButton ahorro, historial, info, salir, botonCodConfirm;
    SQLiteConnection db = new SQLiteConnection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallatransferencias2);

        ahorro = (ImageButton) findViewById(R.id.botonAhorro);
        ahorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        historial = (ImageButton) findViewById(R.id.botonHistorial);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        info = (ImageButton) findViewById(R.id.botonInfo);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        salir = (ImageButton) findViewById(R.id.botonSalir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        salir = (ImageButton) findViewById(R.id.botonCodConfirm);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),pantallatransferencias3.class);
                startActivity(i);
            }
        });
    }
}