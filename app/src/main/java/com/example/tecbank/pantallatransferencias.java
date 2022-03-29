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

import java.util.Random;

import BaseDeDatos.SQLiteConnection;

public class pantallatransferencias extends AppCompatActivity {

    ImageButton ahorro, historial, info, salir;
    Button siguienteT, cuentaExterna;
    SQLiteConnection db = new SQLiteConnection(this);

    /*
    En esta clase pantallatranferencias se recolecta la información que usuario digitó para ser validada y así
    poder continuar con la transferencia.
    La clase presenta las funciones necesarias para poder moverse entre la aplicación y hacer la conexión con la base de datos para posteriormente
    hacer los cambios necesarios.


    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallatransferencias);

        ahorro = (ImageButton) findViewById(R.id.botonAhorro);
        ahorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Bundle bundle = getIntent().getExtras();
                String cuenta = bundle.getString("cuenta");
                Intent i= new Intent(getApplicationContext(),pantallaahorro.class);
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

                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        EditText montoT = findViewById(R.id.nombreSobreCRD);
        EditText cuentaAcreditar = findViewById(R.id.cantidadDineroCRD);
        EditText motivoT = findViewById(R.id.motivoT);
        //EditText usuario_login = findViewById(R.id.usuario);

        siguienteT = (Button) findViewById(R.id.botonSigTransfer);
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

                    }else{
                        Bundle bundle = getIntent().getExtras();
                        String cuenta = bundle.getString("cuenta");
                        Customer customer = new Customer();
                        int monto_customer=0;
                        String cantidad= montoT.getText().toString();
                        int monto = new Integer(cantidad).intValue();

                        db.buscar_monto(customer,cuenta);
                        monto_customer = customer.getMonto();

                        if (monto <= monto_customer){

                            Toast.makeText(getApplicationContext(),"Correcto'"+customer.getNombre()+"'",Toast.LENGTH_LONG).show();
                            int codigo = generarCodigo();
                            sendMail(customer.getCorreo(),"codigo","El código es= '"+codigo+"'");

                            String codigo_verficicacion = new String(String.valueOf(codigo)).toString();
                            Intent i = new Intent(getApplicationContext(),pantallatransferencias2.class);
                            i.putExtra("usuario",customer.getNombre());
                            i.putExtra("correo_usuario",customer.getCorreo());
                            i.putExtra("cuentaDebitar",customer.getCuenta());
                            i.putExtra("cuentaAcreditar",cuentaAcreditar.getText().toString());
                            i.putExtra("monto",montoT.getText().toString());
                            i.putExtra("motivo",motivoT.getText().toString());
                            i.putExtra("codigo_verificacion",codigo_verficicacion);
                            startActivity(i);

                        }else{
                            Toast.makeText(getApplicationContext(),"Fondos insuficientes",Toast.LENGTH_LONG).show();
                            montoT.setText("");
                            montoT.findFocus();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Ningún campo puede quedar vacío", Toast.LENGTH_LONG).show();
                }
            }
        });

        cuentaExterna = (Button) findViewById(R.id.botonCuentaExterna);
        cuentaExterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(montoT.getText().toString().equals("")) && !(cuentaAcreditar.getText().toString().equals(""))
                        && !(motivoT.getText().toString().equals("")))
                {
                    Cursor cursor = db.check_cuentaAcreditarExterna(cuentaAcreditar.getText().toString());
                    if (cursor.getCount() < 1) {
                        Toast.makeText(getApplicationContext(), "Cuenta a acreditar no existente ", Toast.LENGTH_LONG).show();
                        cuentaAcreditar.setText("");
                        cuentaAcreditar.findFocus();

                    }else{
                        Bundle bundle = getIntent().getExtras();
                        String cuen = bundle.getString("cuenta");
                        Customer customer = new Customer();
                        int monto_customer=0;
                        String cantidad= montoT.getText().toString();
                        int monto = new Integer(cantidad).intValue();
                        db.buscar_monto(customer,cuen);
                        monto_customer = customer.getMonto();
                        if (monto + 1294 <= monto_customer){
                            Toast.makeText(getApplicationContext(),"Correcto'"+customer.getNombre()+"'",Toast.LENGTH_LONG).show();
                            int codigo = generarCodigo();
                            sendMail(customer.getCorreo(),"codigo","El código es= '"+codigo+"'");
                            String codigo_verficicacion = new String(String.valueOf(codigo)).toString();
                            Intent i = new Intent(getApplicationContext(),TranferenciaExtenerna.class);
                            i.putExtra("usuario",customer.getNombre());
                            i.putExtra("correo_usuario",customer.getCorreo());
                            i.putExtra("cuentaDebitar",customer.getCuenta());
                            i.putExtra("cuentaAcreditar",cuentaAcreditar.getText().toString());
                            i.putExtra("monto",montoT.getText().toString());
                            i.putExtra("motivo",motivoT.getText().toString());
                            i.putExtra("codigo_verificacion",codigo_verficicacion);
                            startActivity(i);

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Fondos insuficientes",Toast.LENGTH_LONG).show();
                            montoT.setText("");
                            montoT.findFocus();
                        }



                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Ningún campo puede quedar vacío", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private void sendMail(String correo,String subject,String message){
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,correo,subject,message);
        javaMailAPI.execute();

    }
    private  int generarCodigo(){
        int max = 10000;
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int codigo = random.nextInt(max);
        return codigo;
    }
}