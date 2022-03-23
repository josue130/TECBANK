package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import BaseDeDatos.SQLiteConnection;

public class pantallaahorroCargarRetirar extends AppCompatActivity {

    ImageButton ahorro, historial, info, salir;
    Button cargarSobre, retirarSobre;
    EditText nombreSobre, cantidadDinero;



    SQLiteConnection db = new SQLiteConnection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallaahorro_cargar_retirar);

        nombreSobre = (EditText) findViewById(R.id.nombreSobreCRD);
        cantidadDinero = (EditText) findViewById(R.id.cantidadDineroCRD);

        ahorro = (ImageButton) findViewById(R.id.botonAhorro);
        ahorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i = new Intent(getApplicationContext(), pantallaahorro.class);
                startActivity(i);
            }
        });

        historial = (ImageButton) findViewById(R.id.botonHistorial);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i = new Intent(getApplicationContext(), pantallahistorial.class);
                startActivity(i);
            }
        });

        info = (ImageButton) findViewById(R.id.botonInfo);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i = new Intent(getApplicationContext(), pantallainformativa.class);
                startActivity(i);
            }
        });

        salir = (ImageButton) findViewById(R.id.botonSalir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        cargarSobre = (Button) findViewById(R.id.botonCargarD);
        cargarSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
        retirarSobre = (Button) findViewById(R.id.button2);
        retirarSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
    }
}