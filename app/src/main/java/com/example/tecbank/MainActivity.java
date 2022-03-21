package com.example.tecbank;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import BaseDeDatos.SQLiteConnection;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    SQLiteConnection db = new SQLiteConnection(this);

    Button registrarse, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        registrarse = (Button) findViewById(R.id.registrarse);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(), register.class);
                startActivity(i);
            }
        });

        EditText usuario = findViewById(R.id.usuario);
        EditText password = findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !(usuario.getText().toString().equals("")) && !(password.getText().toString().equals(""))) {
                    try {
                        Cursor cursor = db.login(usuario.getText().toString(), password.getText().toString());
                        if (cursor.getCount() > 0) {
                            // Si el usuario y contraseña existe desde aqui se manda a la siguiente ventana
                            Toast.makeText(getApplicationContext(), "Acceso concedido", Toast.LENGTH_LONG).show();
                            Customer customer = new Customer();
                            db.obtener_cuenta(customer,usuario.getText().toString(),password.getText().toString());

                            Intent i= new Intent(getApplicationContext(),pantallatransferencias.class);
                            i.putExtra("usuario_login",usuario.getText().toString());

                            Intent it= new Intent(getApplicationContext(),pantallacuentas.class);
                            it.putExtra("cuenta",customer.getCuenta());
                            int monto_customer = customer.getMonto();
                            String monto_string = new String(String.valueOf(monto_customer)).toString();
                            it.putExtra("monto",monto_string);

                            startActivity(it);

                            //startActivity(i);



                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_LONG).show();
                        }
                        usuario.setText("");
                        usuario.findFocus();

                    } catch (SQLException e) {

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Se detectó espacios vacíos", Toast.LENGTH_LONG).show();
                }
            }
        });


//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydeney = new LatLng(-34,151);
        mMap.addMarker(new MarkerOptions()
                .position(sydeney)
                .title("Maker in sydeney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydeney));

    }
}