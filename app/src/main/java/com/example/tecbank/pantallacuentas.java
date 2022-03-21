package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class pantallacuentas extends AppCompatActivity {

    ImageButton ahorro, historial, info, salir, tranfers;
    TextView numCuenta, montoCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallacuentas);
        Bundle bundle = getIntent().getExtras();
        numCuenta = (TextView) findViewById(R.id.numCuenta);
        montoCuenta = (TextView) findViewById(R.id.montoCuenta);
        //Mostar la cuenta y la plata que tiene
        String cuenta = bundle.getString("cuenta");
        String monto = bundle.getString("monto");
        numCuenta.setText(cuenta);
        montoCuenta.setText(monto);


        ahorro = (ImageButton) findViewById(R.id.botonAhorro);
        ahorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        tranfers = (ImageButton) findViewById(R.id.botonTransfer);
        tranfers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i= new Intent(getApplicationContext(),pantallatransferencias.class);
                startActivity(i);
            }
        });

        historial = (ImageButton) findViewById(R.id.botonHistorial);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent i= new Intent(getApplicationContext(),pantallahistorial.class);
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
    }
}