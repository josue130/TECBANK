package BaseDeDatos;

import static java.sql.DriverManager.println;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tecbank.Customer;

public class SQLiteConnection extends SQLiteOpenHelper {

    final String CREATE_TABLE = "CREATE TABLE Customer (ID integer primary key autoincrement,name TEXT," +
            "correo TEXT,cuenta TEXT, contraseña TEXT, monto integer)";

    final String CREATE_TABLE_CUENTAEXTERNA = "CREATE TABLE CuentaExterna (ID integer primary key autoincrement,name TEXT," +
            "correo TEXT,cuenta TEXT, monto integer)";

    final String CREATE_TABLE_VOUCHER = "CREATE TABLE Voucher (ID integer primary key autoincrement, name TEXT," +
        "cuentaDebitar TEXT, cuentaAcreditar TEXT, monto integer, motivo TEXT, fecha DATETIME)";

    public SQLiteConnection(@Nullable Context context) {
        super(context, "Customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_VOUCHER);
        sqLiteDatabase.execSQL(CREATE_TABLE_CUENTAEXTERNA);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    // Si queremos escribir algo en la base de datos
    public void write(){
        this.getWritableDatabase();

    }

    public SQLiteDatabase getWritable(){
        return this.getWritableDatabase();

    }
    // Si queremos buscar algo en la base de datos
    public void read(){
        this.getReadableDatabase();
    }

    // Metodo que me permite insetar valores en la tabla usuarios

    public void DataInsert(String usuario, String correo, String cuenta, String contraseña, Integer monto){
        ContentValues data = new ContentValues();
        data.put("name",usuario);
        data.put("correo",correo);
        data.put("cuenta",cuenta);
        data.put("contraseña",contraseña);
        data.put("monto",monto);
        this.getWritableDatabase().insert("Customer",null,data);
    }
    public void DataInsertCuentaExterna(String usuario, String correo, String cuenta, Integer monto){
        ContentValues data = new ContentValues();
        data.put("name",usuario);
        data.put("correo",correo);
        data.put("cuenta",cuenta);
        data.put("monto",monto);
        this.getWritableDatabase().insert("CuentaExterna",null,data);
    }

    public void DataInsertVoucher(String name, String cuentaDebitar, String cuentaAcreditar, int monto, String motivo, String fecha){
        ContentValues data = new ContentValues();
        data.put("name",name);
        data.put("cuentaDebitar",cuentaDebitar);
        data.put("cuentaAcreditar",cuentaAcreditar);
        data.put("monto",monto);
        data.put("motivo",motivo);
        data.put("fecha",fecha);
        this.getWritableDatabase().insert("Voucher",null,data);
    }

    // Permite validar si usario existe

    public Cursor login(String usuario,String contraseña) throws SQLException{
        Cursor cursor = null;

        cursor = this.getReadableDatabase().query("Customer",new String[]{"ID","name","correo","cuenta","contraseña","monto"},
                "name like '"+usuario+"' and contraseña like '"+contraseña+"'"
                ,null,null,null,null);

        return cursor;
    }

    public Cursor chech_if_user_exists(String usuario) throws SQLException{
        Cursor cursor = null;

        cursor = this.getReadableDatabase().query("Customer",new String[]{"ID","name","correo","cuenta","contraseña","monto"},
                "name like '"+usuario+"'"
                ,null,null,null,null);

        return cursor;
    }

    public Cursor check_cuentaAcreditar(String cuenta) throws  SQLException{
        Cursor cursor = null;

        cursor = this.getReadableDatabase().query("Customer", new String[]{"ID","name","correo","cuenta","contraseña","monto"},
                "cuenta like '"+cuenta+"'",
                null, null,null,null);

        return cursor;
    }
    public Cursor check_cuentaAcreditarExterna(String cuenta) throws  SQLException{
        Cursor cursor = null;

        cursor = this.getReadableDatabase().query("CuentaExterna", new String[]{"ID","name","correo","cuenta","monto"},
                "cuenta like '"+cuenta+"'",
                null, null,null,null);

        return cursor;
    }

    public void buscar_monto(Customer customer,String cuenta){
        SQLiteDatabase db = getReadableDatabase();
            Cursor cursor_monto = db.rawQuery("SELECT * FROM CUSTOMER WHERE CUENTA ='"+cuenta+"'",null);
        if(cursor_monto.moveToFirst()){
            customer.setId(cursor_monto.getInt(0));
            customer.setNombre(cursor_monto.getString(1));
            customer.setCorreo(cursor_monto.getString(2));
            customer.setCuenta(cursor_monto.getString(3));
            customer.setContrasenna(cursor_monto.getString(4));
            customer.setMonto(cursor_monto.getInt(5));

        }

    }
    public void depositar_monto( String cuenta, int monto,int monto_depositar){
        SQLiteDatabase db = getReadableDatabase();
        int res = 0;
        res = monto + monto_depositar;
        if (db != null){
            db.execSQL("UPDATE CUSTOMER SET MONTO ='"+res+"' WHERE CUENTA='"+cuenta+"'");
            db.close();
        }
    }
    public void debitar_monto(String cuenta, int monto, int monto_debitar){
        SQLiteDatabase db = getReadableDatabase();
        int res = 0;
        res = monto - monto_debitar;
        if (db != null){
            db.execSQL("UPDATE CUSTOMER SET MONTO ='"+res+"'WHERE CUENTA ='"+cuenta+"'");
            db.close();
        }
    }
    public void obtener_cuenta(Customer customer,String usuario, String contra){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor_cuenta = db.rawQuery("SELECT * FROM CUSTOMER WHERE NAME ='"+usuario+"' AND contraseña='"+contra+"'", null);
        if (cursor_cuenta.moveToFirst()){
            customer.setId(cursor_cuenta.getInt(0));
            customer.setNombre(cursor_cuenta.getString(1));
            customer.setCorreo(cursor_cuenta.getString(2));
            customer.setCuenta(cursor_cuenta.getString(3));
            customer.setContrasenna(cursor_cuenta.getString(4));
            customer.setMonto(cursor_cuenta.getInt(5));
        }
    }




    public void CLOSE(){
        this.close();
    }
}
