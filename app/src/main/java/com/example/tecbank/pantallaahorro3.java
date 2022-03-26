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

public class pantallaahorro3 extends AppCompatActivity {

    ImageButton ahorro, historial, info, salir;
    Button eliminarSobre;
    EditText nombreSobre;

    SQLiteConnection db = new SQLiteConnection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallaahorro3);

        nombreSobre = (EditText) findViewById(R.id.nombreSobreCRD);


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
                Intent i = new Intent(getApplicationContext(), pantallacuentas.class);
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

        eliminarSobre = (Button) findViewById(R.id.botonCargarD);
        eliminarSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                String cuenta = bundle.getString("cuenta");

                if (!(nombreSobre.getText().toString().equals(""))){
                    SobreAhorro sobreAhorro = new SobreAhorro();
                    Cursor cursor =  db.check_sobre_ahorro(cuenta,nombreSobre.getText().toString());
                    if (cursor.getCount() > 0){
                        Customer customer = new Customer();
                        db.buscar_monto(customer,cuenta);
                        db.obtener_info_sobreAhorro(sobreAhorro,cuenta,nombreSobre.getText().toString());
                        db.depositar_monto(cuenta,customer.getMonto(),sobreAhorro.getMonto());
                        db.DataInsertHistorialSobres(nombreSobre.getText().toString(),cuenta,"Eliminar",sobreAhorro.getMonto());
                        db.eliminar_sobre(cuenta,nombreSobre.getText().toString());

                        Toast.makeText(getApplicationContext(),"Elimnado con exito",Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getApplicationContext(),pantallaahorro.class);
                        i.putExtra("cuenta",cuenta);
                        startActivity(i);

                    }else {
                        Toast.makeText(getApplicationContext(),"Sobre no encontrado!",Toast.LENGTH_LONG).show();
                        nombreSobre.setText("");
                        nombreSobre.findFocus();
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}