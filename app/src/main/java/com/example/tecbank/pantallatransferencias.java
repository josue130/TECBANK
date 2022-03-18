package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import BaseDeDatos.SQLiteConnection;

public class pantallatransferencias extends AppCompatActivity {

    ImageButton ahorro, historial, info, salir, siguienteT;
    SQLiteConnection db = new SQLiteConnection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallatransferencias);

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

        EditText montoT = findViewById(R.id.montoT);
        EditText cuentaAcreditar = findViewById(R.id.cuentaAcreditar);
        EditText motivoT = findViewById(R.id.motivoT);

        siguienteT = (ImageButton) findViewById(R.id.botonSigTransfer);
        siguienteT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(montoT.getText().toString().equals("")) && !(cuentaAcreditar.getText().toString().equals(""))
                        && !(motivoT.getText().toString().equals("")))
                {
                    Cursor cursor = db.check_cuentaAcreditar(cuentaAcreditar.getText().toString());
                    if (cursor.getCount() < 1) {
                        Toast.makeText(getApplicationContext(), "Cuenta a acreditar no existente ", Toast.LENGTH_LONG).show();
                        cuentaAcreditar.setText("");
                        cuentaAcreditar.findFocus();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ningún campo puede quedar vacío", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}