package com.example.tecbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import BaseDeDatos.SQLiteConnection;

public class register extends AppCompatActivity {
    Button Rcontinuar;
    EditText Rusuario, Rcorreo, Rcuenta, Rcontraseña;
    public SQLiteConnection db = new SQLiteConnection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Rcontinuar = (Button) findViewById(R.id.Rcontinuar);
        Rusuario = (EditText) findViewById(R.id.Rusuario);
        Rcorreo = (EditText) findViewById(R.id.Rcorreo);
        Rcuenta = (EditText) findViewById(R.id.RCuenta);
        Rcontraseña = (EditText) findViewById(R.id.Rcontraseña);

        Rcontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.write();
                db.DataInsert(String.valueOf(Rusuario.getText()),String.valueOf(Rcorreo.getText()),
                        String.valueOf(Rcuenta.getText()),String.valueOf(Rcontraseña.getText()));
                db.CLOSE();

                Toast.makeText(getApplicationContext(),"Registro almacenado con exito",Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });



    }



}