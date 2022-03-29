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
                Bundle bundle = getIntent().getExtras();
                String cuenta = bundle.getString("cuenta");
                Intent i = new Intent(getApplicationContext(), pantallaahorro.class);
                i.putExtra("cuenta",cuenta);
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
                Bundle bundle = getIntent().getExtras();
                String cuenta = bundle.getString("cuenta");
                Intent i = new Intent(getApplicationContext(), pantallainformativa.class);
                i.putExtra("cuenta",cuenta);
                startActivity(i);
            }
        });

        salir = (ImageButton) findViewById(R.id.botonSalir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(i);
            }
        });
        /*
        Funcion Cargar sobre
        Entrada = nombre del sobre y el monto

         */
        cargarSobre = (Button) findViewById(R.id.botonCargarD);
        cargarSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //obtiene la cuenta del usuario
                Bundle bundle = getIntent().getExtras();
                String cuenta = bundle.getString("cuenta");

                //validaciones
                if (!(nombreSobre.getText().toString().equals("")) && !(cantidadDinero.getText().toString().equals(""))){
                    String cant = cantidadDinero.getText().toString();
                    int cantidadD = new Integer(cant).intValue();
                    //verifica si el sobre existe
                    Cursor cursor = db.check_sobre_ahorro(cuenta,nombreSobre.getText().toString());
                    if (cursor.getCount() > 0){
                        //obtiene datos del usuario
                        Customer customer = new Customer();
                        db.buscar_monto(customer,cuenta);
                        //verifica que el monto a cargar no exceda el monto de la cuenta
                        if (cantidadD <= customer.getMonto()){
                            //obtiene la info del sobre a cargar
                            SobreAhorro sobreAhorro = new SobreAhorro();

                            db.obtener_info_sobreAhorro(sobreAhorro,cuenta,nombreSobre.getText().toString());
                            int monto_sobre = sobreAhorro.getMonto();
                            //carga el monto al sobre
                            db.cargar_sobre(cuenta,nombreSobre.getText().toString(),monto_sobre,cantidadD);
                            //inserta el registro de carga al historial de sobres
                            db.DataInsertHistorialSobres(nombreSobre.getText().toString(),cuenta,"Cargar",cantidadD);
                            //sustraer el monto de la carga a la cuenta del usuario
                            db.debitar_monto(cuenta,customer.getMonto(),cantidadD);

                            Toast.makeText(getApplicationContext(),"Carga exitosa",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(),pantallaahorro.class);
                            i.putExtra("cuenta",cuenta);
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
        /*
        Funcion Retirar sobre
        Entrada = nombre del sobre y el monto

         */

        retirarSobre = (Button) findViewById(R.id.button2);
        retirarSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //obtener la cuenta del usuario
                Bundle bundle = getIntent().getExtras();
                String cuenta = bundle.getString("cuenta");
                //validaciones
                if (!(nombreSobre.getText().toString().equals("")) && !(cantidadDinero.getText().toString().equals(""))){
                    String cant = cantidadDinero.getText().toString();
                    int cantidadD = new Integer(cant).intValue();
                    //validar si el sobre existe

                    Cursor cursor = db.check_sobre_ahorro(cuenta,nombreSobre.getText().toString());
                    if (cursor.getCount() > 0){
                        //obtener la info del sobre
                        SobreAhorro sobreAhorro = new SobreAhorro();
                        db.obtener_info_sobreAhorro(sobreAhorro,cuenta,nombreSobre.getText().toString());
                        //verifica que el monto a retirar no exceda la cantidad del monto del sobre
                        if (cantidadD <= sobreAhorro.getMonto()){
                            //se realiza la sustraccion del monto del sobre
                            db.retirar_sobre(cuenta,nombreSobre.getText().toString(),sobreAhorro.getMonto(),cantidadD);
                            //se registra el retiro en el historial
                            db.DataInsertHistorialSobres(nombreSobre.getText().toString(),cuenta,"Retirar",cantidadD);
                            //se obtiene los datos del usuario
                            Customer customer = new Customer();
                            db.buscar_monto(customer,cuenta);
                            //se reintegra el monto del retiro a la cuenta
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