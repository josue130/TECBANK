package com.example.tecbank;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.ArrayList;
import Adapter.MyAdapter;
import BaseDeDatos.SQLiteConnection;
public class pantallaahorro4 extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> nombre, tipo , monto;
    MyAdapter adapter;
    SQLiteConnection db = new SQLiteConnection(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallaahorro4);
        nombre = new ArrayList<>();
        tipo = new ArrayList<>();
        monto = new ArrayList<>();
        recyclerView = findViewById(R.id.lista);
        adapter = new MyAdapter(this,nombre,tipo,monto);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
    }
    private void displaydata(){
        Bundle bundle = getIntent().getExtras();
        String cuenta = bundle.getString("cuenta");
        Cursor cursor = db.getSobres(cuenta);
        if(cursor.getCount()==0){
            Toast.makeText(this, "No hay sobres registrados", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while (cursor.moveToNext()){
                nombre.add(cursor.getString(2));
                tipo.add(cursor.getString(3));
                int mon = cursor.getInt(4);
                monto.add(String.valueOf(mon));
            }
        }
    }
}