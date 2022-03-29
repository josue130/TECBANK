package com.example.tecbank;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import BaseDeDatos.SQLiteConnection;

public class TranferenciaExtenerna extends AppCompatActivity {
    ImageButton ahorro, historial, info, salir, botonCodConfirm;
    SQLiteConnection db = new SQLiteConnection(this);

    /*
    En esta clase TransferenciaExterna se llaman a los botones necesarios  para obtener la informacióm
    que el usuario ha digitado.
    La clase presenta las funciones necesarias para poder moverse entre la aplicación y hacer la conexión con la base de datos para posteriomente
    hacer los cambios necesarios.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallatransferencias2);

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

        botonCodConfirm = (ImageButton) findViewById(R.id.botonCodConfirm);
        botonCodConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                String usuario = bundle.getString("usuario");
                String correo_usuario = bundle.getString("correo_usuario");
                String codigo_verificacion = bundle.getString("codigo_verificacion");
                String cuentaDebitar = bundle.getString("cuentaDebitar");
                String cuentaAcreditar = bundle.getString("cuentaAcreditar");
                String montoTrasfer = bundle.getString("monto");
                String motivo = bundle.getString("motivo");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String fecha = simpleDateFormat.format(new Date());

                if (!(montoT.getText().toString().equals(""))){
                    if (montoT.getText().toString().equals(codigo_verificacion)){
                        int monto_int = new Integer(montoTrasfer).intValue() ;
                        Customer customerDebitar = new Customer();
                        CuentaExterna cuenta = new CuentaExterna();
                        db.buscar_monto(customerDebitar,cuentaDebitar);
                        db.debitar_monto(cuentaDebitar,customerDebitar.getMonto(),monto_int +1294 );
                        Customer customerAcreditar = new Customer();
                        db.buscar_monto_CuentaExterna(cuenta,cuentaAcreditar);
                        db.depositar_monto_CuentaExterna(cuentaAcreditar,cuenta.getMonto(),monto_int);
                        String voucher_email = "Buen dia '"+usuario+"'\nVoucher Transaccion\nFecha:'"+fecha+"'\nCuenta origen:'"+cuentaDebitar+"'\nCuenta destino:'"+cuentaAcreditar+"'\nMonto:'"+montoTrasfer+"'\nMotivo:'"+motivo+"'\n\nGracias por su preferencia!!\n\n\nAtentamente, TECBANK Costa Rica\n'";
                        Toast.makeText(getApplicationContext(), "Exitoso", Toast.LENGTH_SHORT).show();
                        sendMailVoucher(correo_usuario,"Comprobante transaccion",voucher_email);
                        Intent i = new Intent(getApplicationContext(),pantallatransferencias3.class);
                        i.putExtra("usuario",usuario);
                        i.putExtra("cuentaDebitar",cuentaDebitar);
                        i.putExtra("cuentaAcreditar",cuentaAcreditar);
                        i.putExtra("monto",montoTrasfer);
                        i.putExtra("motivo",motivo);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "Codigo incorrecto", Toast.LENGTH_SHORT).show();
                        montoT.setText("");
                        montoT.findFocus();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Porfavor ingrese el codigo de verificacion", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void sendMailVoucher(String correo,String subject,String message){
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,correo,subject,message);
        javaMailAPI.execute();

    }
}