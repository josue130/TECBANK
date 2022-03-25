package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import BaseDeDatos.SQLiteConnection;

public class pantallaahorro extends AppCompatActivity {
    ImageButton ahorro, historial, info, salir;
    Button crearS, eliminarS, cargarRetirar, historialS;

    SQLiteConnection db = new SQLiteConnection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallaahorro);

        ahorro = (ImageButton) findViewById(R.id.botonAhorro);
        ahorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i= new Intent(getApplicationContext(),pantallaahorro.class);
                startActivity(i);
            }
        });

        historial = (ImageButton) findViewById(R.id.botonHistorial);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i= new Intent(getApplicationContext(),pantallacuentas.class);
                startActivity(i);
            }
        });

        info = (ImageButton) findViewById(R.id.botonInfo);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i= new Intent(getApplicationContext(),pantallainformativa.class);
                startActivity(i);
            }
        });

        salir = (ImageButton) findViewById(R.id.botonSalir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
        crearS = (Button) findViewById(R.id.botonCS);
        crearS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i= new Intent(getApplicationContext(),pantallaahorro2.class);
                startActivity(i);
            }
        });
        cargarRetirar = (Button) findViewById(R.id.botonCRD);
        cargarRetirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i= new Intent(getApplicationContext(),pantallaahorroCargarRetirar.class);
                startActivity(i);
            }
        });
        eliminarS = (Button) findViewById(R.id.botonES);
        eliminarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i= new Intent(getApplicationContext(),pantallaahorro3.class);
                startActivity(i);
            }
        });
        historialS = (Button) findViewById(R.id.botonHS);
        historialS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i= new Intent(getApplicationContext(),pantallaahorro4.class);
                startActivity(i);
            }
        });
    }

}