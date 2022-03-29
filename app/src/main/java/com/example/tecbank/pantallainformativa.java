package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import BaseDeDatos.SQLiteConnection;

public class pantallainformativa extends AppCompatActivity {
    ImageButton ahorro, historial, info, salir, tranfers;
    Button ubi ;

    SQLiteConnection db = new SQLiteConnection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallainformativa);

        //Bundle bundle = getIntent().getExtras();
        //Obtener la cuenta del usuario
        //String cuenta = bundle.getString("cuenta");

        ahorro = (ImageButton) findViewById(R.id.botonAhorro);
        ahorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                //Obtener la cuenta del usuario
                String cuenta = bundle.getString("cuenta");
                Toast.makeText(getApplicationContext(),"cuenta es:'"+cuenta+"'",Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),pantallaahorro.class);
                i.putExtra("cuenta",cuenta);
                startActivity(i);
            }
        });

        tranfers = (ImageButton) findViewById(R.id.botonTransfer);
        tranfers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                //Obtener la cuenta del usuario
                String cuenta = bundle.getString("cuenta");
                Intent i= new Intent(getApplicationContext(),pantallatransferencias.class);
                i.putExtra("cuenta",cuenta);
                startActivity(i);
            }
        });

        historial = (ImageButton) findViewById(R.id.botonHistorial);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                //Obtener la cuenta del usuario
                String cuenta = bundle.getString("cuenta");
                Customer customer = new Customer();
                db.monto_cuenta(customer, cuenta);
                int monto_int = customer.getMonto();
                String monto_string = new String(String.valueOf(monto_int)).toString();
                Intent i= new Intent(getApplicationContext(),pantallacuentas.class);
                i.putExtra("cuenta",cuenta);
                i.putExtra("monto",monto_string);
                startActivity(i);
            }
        });

        info = (ImageButton) findViewById(R.id.botonInfo);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                //Obtener la cuenta del usuario
                String cuenta = bundle.getString("cuenta");
                Intent i= new Intent(getApplicationContext(),pantallainformativa.class);
                i.putExtra("cuenta",cuenta);
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

        ubi = (Button) findViewById(R.id.botonUGM);
        ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                //Obtener la cuenta del usuario
                String cuenta = bundle.getString("cuenta");
                Intent i= new Intent(getApplicationContext(), GoogleMaps.class);
                i.putExtra("cuenta",cuenta);
                startActivity(i);
            }
        });


    }
}