package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import BaseDeDatos.SQLiteConnection;

public class pantallatransferencias3 extends AppCompatActivity {

    ImageButton ahorro, historial, info, salir, botonCodConfirm;
    TextView vFecha, vNombre, vMonto, vCD, vCA, vMotivo;
    SQLiteConnection db = new SQLiteConnection(this);

    /*
    La clase pantallatransferencia3 es la pantalla que muestra toda la estructura del
    voucher generada en la clase pantallatransferencia2.

    Tambien cuenta con todos los botones necesarios para navegar en la app
    */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallatransferencias3);

        vFecha = (TextView) findViewById(R.id.vFecha);
        vNombre = (TextView) findViewById(R.id.vNombre);
        vMonto = (TextView)findViewById(R.id.vMonto);
        vCD = (TextView)  findViewById(R.id.vCD);
        vCA = (TextView) findViewById(R.id.vCA);
        vMotivo = (TextView) findViewById(R.id.vMotivo);

        Bundle bundle = getIntent().getExtras();
        String usuario = bundle.getString("usuario");
        String cuentaDebitar = bundle.getString("cuentaDebitar");
        String cuentaAcreditar = bundle.getString("cuentaAcreditar");
        String motivo = bundle.getString("motivo");
        String monto = bundle.getString("monto");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = simpleDateFormat.format(new Date());

        vFecha.setText(currentDateandTime);
        vNombre.setText(usuario);
        vCD.setText(cuentaDebitar);
        vCA.setText(cuentaAcreditar);
        vMonto.setText(monto);
        vMotivo.setText(motivo);
        int monto_int = new Integer(monto).intValue();
        db.DataInsertVoucher(usuario,cuentaDebitar,cuentaAcreditar,monto_int,motivo,currentDateandTime);

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
                //
                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }


}