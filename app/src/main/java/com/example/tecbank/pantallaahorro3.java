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
                Bundle bundle = getIntent().getExtras();
                String cuenta = bundle.getString("cuenta");
                Customer customer = new Customer();
                db.monto_cuenta(customer, cuenta);
                int monto_int = customer.getMonto();
                String monto_string = new String(String.valueOf(monto_int)).toString();
                Intent i = new Intent(getApplicationContext(), pantallacuentas.class);
                i.putExtra("cuenta",cuenta);
                i.putExtra("monto",monto_string);
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
        /*
        Funcion Eliminar sobre
        Entrada = nombre del sobre
         */
        eliminarSobre = (Button) findViewById(R.id.botonCargarD);
        eliminarSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //obtiene la cuenta del usuario
                Bundle bundle = getIntent().getExtras();
                String cuenta = bundle.getString("cuenta");

                //validaciones
                if (!(nombreSobre.getText().toString().equals(""))){
                    //verifica que el sobre si exista
                    SobreAhorro sobreAhorro = new SobreAhorro();
                    Cursor cursor =  db.check_sobre_ahorro(cuenta,nombreSobre.getText().toString());
                    if (cursor.getCount() > 0){
                        //se busca el monto del usuario
                        Customer customer = new Customer();
                        db.buscar_monto(customer,cuenta);
                        //se obtiene la info del sobre a eliminar
                        db.obtener_info_sobreAhorro(sobreAhorro,cuenta,nombreSobre.getText().toString());
                        //se reintegra el monto del sobre a el total de la cuenta
                        db.depositar_monto(cuenta,customer.getMonto(),sobreAhorro.getMonto());
                        //insertar el registro del sobre a eliminar
                        db.DataInsertHistorialSobres(nombreSobre.getText().toString(),cuenta,"Eliminar",sobreAhorro.getMonto());
                        //se elimina el sobre
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