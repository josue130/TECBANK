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

public class pantallaahorro2 extends AppCompatActivity {

    ImageButton ahorro, historial, info, salir;
    Button crearSobre;
    EditText nombreSobre, cantidadDinero;

    SQLiteConnection db = new SQLiteConnection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallaahorro2);

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
                //
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        /*
        Funcion Crear sobre
        Entradas= nombre del sobre y el monto con que quiere crearse

         */
        crearSobre = (Button) findViewById(R.id.botonCargarD);
        crearSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //obtener la cuenta del usuario
                Bundle bundle = getIntent().getExtras();
                String cuenta = bundle.getString("cuenta");
                //validaciones
                if (!(nombreSobre.getText().toString().equals("")) && !(cantidadDinero.getText().toString().equals(""))){
                    //verificar si el sobre ya existe o no
                    Cursor cursor = db.check_sobre_ahorro(cuenta,nombreSobre.getText().toString());
                    if (cursor.getCount() == 0){
                        //busca el monto del usuario
                        Customer customer = new Customer();
                        db.buscar_monto(customer,cuenta);
                        String cantidad  = cantidadDinero.getText().toString();
                        int cantidad_int = new Integer(cantidad).intValue();
                        //verifica que el monto del sobre a crear no sea mayor al monto actual que tiene la cuenta del usuario
                        if (cantidad_int <= customer.getMonto()){
                            //debitar el monto de la cuenta - la sustraccion del monto del sobre
                            db.debitar_monto(cuenta,customer.getMonto(),cantidad_int);
                            //insertar el registro del sobre a crear
                            db.DataInsertSobreAhorro(nombreSobre.getText().toString(),cuenta,cantidad_int);
                            //insertar el registro del sobre a crear en el historial de sobres
                            db.DataInsertHistorialSobres(nombreSobre.getText().toString(),cuenta,"Crear",cantidad_int);
                            Toast.makeText(getApplicationContext(),"Creado exitosamente",Toast.LENGTH_LONG).show();

                            Intent i = new Intent(getApplicationContext(),pantallaahorro.class);
                            i.putExtra("cuenta",cuenta);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"Fondos insuficientes",Toast.LENGTH_LONG).show();
                            cantidadDinero.setText("");
                            cantidadDinero.findFocus();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"El sobre ya existe!",Toast.LENGTH_LONG).show();
                        nombreSobre.setText("");
                        nombreSobre.findFocus();
                    }


                }else{
                    Toast.makeText(pantallaahorro2.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}