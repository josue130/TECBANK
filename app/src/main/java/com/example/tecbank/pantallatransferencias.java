package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Random;

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

        EditText montoT = findViewById(R.id.nombreSobreE);
        EditText cuentaAcreditar = findViewById(R.id.cantidadDineroS);
        EditText motivoT = findViewById(R.id.motivoT);
        //EditText usuario_login = findViewById(R.id.usuario);

        siguienteT = (ImageButton) findViewById(R.id.botonSigTransfer);
        siguienteT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),usuario_login.getText(),Toast.LENGTH_LONG).show();
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
                        //String usuario = bundle.getString("usuario_login");
                        String cuen = bundle.getString("cuenta");
                        Customer customer = new Customer();
                        int monto_customer=0;
                        String cantidad= montoT.getText().toString();
                        int monto = new Integer(cantidad).intValue();

                        db.buscar_monto(customer,cuen);
                        monto_customer = customer.getMonto();

                        if (monto <= monto_customer){
                            //db.depositar_monto(cuentaAcreditar.getText().toString(),monto,customer.getMonto());
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