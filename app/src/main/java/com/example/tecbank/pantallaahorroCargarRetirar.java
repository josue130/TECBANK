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

        cargarSobre = (Button) findViewById(R.id.botonCargarD);
        cargarSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                String cuenta = bundle.getString("cuenta");

                if (!(nombreSobre.getText().toString().equals("")) && !(cantidadDinero.getText().toString().equals(""))){
                    String cant = cantidadDinero.getText().toString();
                    int cantidadD = new Integer(cant).intValue();
                    Cursor cursor = db.check_sobre_ahorro(cuenta,nombreSobre.getText().toString());
                    if (cursor.getCount() > 0){
                        Customer customer = new Customer();
                        db.buscar_monto(customer,cuenta);

                        if (cantidadD <= customer.getMonto()){
                            SobreAhorro sobreAhorro = new SobreAhorro();

                            db.obtener_info_sobreAhorro(sobreAhorro,cuenta,nombreSobre.getText().toString());
                            int monto_sobre = sobreAhorro.getMonto();

                            db.cargar_sobre(cuenta,nombreSobre.getText().toString(),monto_sobre,cantidadD);
                            db.DataInsertHistorialSobres(nombreSobre.getText().toString(),cuenta,"Cargar",cantidadD);
                            db.debitar_monto(cuenta,customer.getMonto(),cantidadD);

                            Toast.makeText(getApplicationContext(),"Carga exitosa",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(),pantallaahorro.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"Fondos insuficientes",Toast.LENGTH_LONG).show();
                            cantidadDinero.setText("");
                            cantidadDinero.findFocus();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Sobre no encontrado",Toast.LENGTH_LONG).show();
                        nombreSobre.setText("");
                        nombreSobre.findFocus();
                    }

                }else{
                    Toast.makeText(pantallaahorroCargarRetirar.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }

            }
        });
        retirarSobre = (Button) findViewById(R.id.button2);
        retirarSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                String cuenta = bundle.getString("cuenta");

                if (!(nombreSobre.getText().toString().equals("")) && !(cantidadDinero.getText().toString().equals(""))){
                    String cant = cantidadDinero.getText().toString();
                    int cantidadD = new Integer(cant).intValue();

                    Cursor cursor = db.check_sobre_ahorro(cuenta,nombreSobre.getText().toString());
                    if (cursor.getCount() > 0){
                        SobreAhorro sobreAhorro = new SobreAhorro();
                        db.obtener_info_sobreAhorro(sobreAhorro,cuenta,nombreSobre.getText().toString());

                        if (cantidadD <= sobreAhorro.getMonto()){
                            db.retirar_sobre(cuenta,nombreSobre.getText().toString(),sobreAhorro.getMonto(),cantidadD);
                            db.DataInsertHistorialSobres(nombreSobre.getText().toString(),cuenta,"Retirar",cantidadD);
                            Customer customer = new Customer();
                            db.buscar_monto(customer,cuenta);
                            db.depositar_monto(cuenta,customer.getMonto(),cantidadD);
                            Toast.makeText(getApplicationContext(),"Retiro exitoso",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(),pantallaahorro.class);
                            i.putExtra("cuenta",cuenta);
                            startActivity(i);

                        }else{
                            Toast.makeText(getApplicationContext(),"Fondos insuficientes en el sobre",Toast.LENGTH_LONG).show();
                            cantidadDinero.setText("");
                            cantidadDinero.findFocus();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Sobre no encontrado",Toast.LENGTH_LONG).show();
                        nombreSobre.setText("");
                        nombreSobre.findFocus();
                    }


                }else{
                    Toast.makeText(pantallaahorroCargarRetirar.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}