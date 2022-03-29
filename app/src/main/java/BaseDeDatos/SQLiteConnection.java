package BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.tecbank.CuentaExterna;
import com.example.tecbank.Customer;
import com.example.tecbank.SobreAhorro;

    /*
    * Esta clase es la encargada de hacer la conexión
    * con la base de datos para así lograr hacer
    * todas las consultas necesarias para el proyecto
    * */

public class SQLiteConnection extends SQLiteOpenHelper {

    final String CREATE_TABLE = "CREATE TABLE Customer (ID integer primary key autoincrement,name TEXT," +
            "correo TEXT,cuenta TEXT, contraseña TEXT, monto integer)";

    final String CREATE_TABLE_VOUCHER = "CREATE TABLE Voucher (ID integer primary key autoincrement, name TEXT," +
        "cuentaDebitar TEXT, cuentaAcreditar TEXT, monto integer, motivo TEXT, fecha DATETIME)";

    final  String CREATE_TABLE_SOBREAHORRO = "CREATE TABLE SobreAhorro (ID integer primary key autoincrement, cuenta TEXT," +
        "namesobre TEXT, monto integer)";
    final  String CREATE_TABLE_HISTORIALSOBRES = "CREATE TABLE HistorialSobres (ID integer primary key autoincrement, cuenta TEXT," +
            "namesobre TEXT, tipo TEXT, monto integer)";

    final String CREATE_TABLE_CUENTAEXTERNA = "CREATE TABLE CuentaExterna (ID integer primary key autoincrement,name TEXT," +
            "correo TEXT,cuenta TEXT, monto integer)";


    public SQLiteConnection(@Nullable Context context) {
        super(context, "Customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_VOUCHER);
        sqLiteDatabase.execSQL(CREATE_TABLE_SOBREAHORRO);
        sqLiteDatabase.execSQL(CREATE_TABLE_HISTORIALSOBRES);
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


    /*
     * Esta función inserta un voucher
     * a su respectiva tabla en la base
     * de datos
     * */

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


    /*
     * Esta función inserta un sobre de ahorro
     * a la tabla respectiva en la base de datos
     * */

    public void DataInsertSobreAhorro(String namesobre, String cuenta,  int monto){
        ContentValues data = new ContentValues();
        data.put("namesobre",namesobre);
        data.put("cuenta",cuenta);
        data.put("monto",monto);

        this.getWritableDatabase().insert("SobreAhorro",null,data);
    }

    /*
     * Esta función inserta información importante
     * para el historial de sobres
     * */

    public void DataInsertHistorialSobres(String namesobre, String cuenta, String tipo,  int monto){
        ContentValues data = new ContentValues();
        data.put("namesobre",namesobre);
        data.put("cuenta",cuenta);
        data.put("tipo",tipo);
        data.put("monto",monto);

        this.getWritableDatabase().insert("HistorialSobres",null,data);
    }


    // Esta función valida la existencia de un usuario y contraseña repetidos en la base de datos

    public Cursor login(String usuario,String contraseña) throws SQLException{
        Cursor cursor = null;

        cursor = this.getReadableDatabase().query("Customer",new String[]{"ID","name","correo","cuenta","contraseña","monto"},
                "name like '"+usuario+"' and contraseña like '"+contraseña+"'"
                ,null,null,null,null);

        return cursor;
    }


    /*
     * Esta función valida la existencia de un cliente con el mismo usuario
     * */

    public Cursor check_if_user_exists(String usuario) throws SQLException{
        Cursor cursor = null;

        cursor = this.getReadableDatabase().query("Customer",new String[]{"ID","name","correo","cuenta","contraseña","monto"},
                "name like '"+usuario+"'"
                ,null,null,null,null);

        return cursor;
    }


    /*
     * Esta función valida la existencia de una cuenta externa con el mismo número de cuenta
     * */

    public Cursor check_cuentaAcreditarExterna(String cuenta) throws  SQLException{
        Cursor cursor = null;

        cursor = this.getReadableDatabase().query("CuentaExterna", new String[]{"ID","name","correo","cuenta","monto"},
                "cuenta like '"+cuenta+"'",
                null, null,null,null);

        return cursor;
    }

    /*
     * Función que se encarga de buscar el monto de una cuenta externa
     * */

    public void buscar_monto_CuentaExterna(CuentaExterna cuentaExterna, String cuenta){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor_monto = db.rawQuery("SELECT * FROM CUENTAEXTERNA WHERE CUENTA ='"+cuenta+"'",null);
        if(cursor_monto.moveToFirst()){
            cuentaExterna.setId(cursor_monto.getInt(0));
            cuentaExterna.setNombre(cursor_monto.getString(1));
            cuentaExterna.setCorreo(cursor_monto.getString(2));
            cuentaExterna.setCuenta(cursor_monto.getString(3));
            cuentaExterna.setMonto(cursor_monto.getInt(4));
        }
    }

    /*
     * Esta función se encarga de depositar un monto en una cuenta externa a la tabla en la base de datos
     * */

    public void depositar_monto_CuentaExterna( String cuenta, int monto,int monto_depositar){
        SQLiteDatabase db = getReadableDatabase();
        int res = 0;
        res = monto + monto_depositar;
        if (db != null){
            db.execSQL("UPDATE CUENTAEXTERNA SET MONTO ='"+res+"' WHERE CUENTA='"+cuenta+"'");
            db.close();
        }
    }

    /*
     * Esta función se encarga de debitar un monto en una cuenta externa a la tabla en la base de datos
     * */

    public void debitar_monto_CuentaExterna(String cuenta, int monto, int monto_debitar){
        SQLiteDatabase db = getReadableDatabase();
        int res = 0;
        res = monto - monto_debitar;
        if (db != null){
            db.execSQL("UPDATE CUENTAEXTERNA SET MONTO ='"+res+"'WHERE CUENTA ='"+cuenta+"'");
            db.close();
        }
    }


    /*
     * Esta función se encarga de insertar cuentas externas en tablas de la base de datos
     * */

    public void DataInsertCuentaExterna(String usuario, String correo, String cuenta, Integer monto){
        ContentValues data = new ContentValues();
        data.put("name",usuario);
        data.put("correo",correo);
        data.put("cuenta",cuenta);
        data.put("monto",monto);
        this.getWritableDatabase().insert("CuentaExterna",null,data);
    }


    /*
     * Esta función se encarga de validar la existencia de la cuenta a acreditar
     * */

    public Cursor check_cuentaAcreditar(String cuenta) throws  SQLException{
        Cursor cursor = null;

        cursor = this.getReadableDatabase().query("Customer", new String[]{"ID","name","correo","cuenta","contraseña","monto"},
                "cuenta like '"+cuenta+"'",
                null, null,null,null);

        return cursor;
    }


    /*
     * Esta función se encarga de validar que el sobre de ahorro exista en la base de datos
     * */

    public Cursor check_sobre_ahorro(String cuenta,String namesobre) throws  SQLException{
        Cursor cursor = null;

        cursor = this.getReadableDatabase().query("SobreAhorro", new String[]{"ID","cuenta","namesobre","monto"},
                "cuenta like '"+cuenta+"'and namesobre like'"+namesobre+"'",
                null, null,null,null);

        return cursor;
    }


    /*
     * Esta función se encarga de buscar montos en la base de datos
     * */

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


    /*
     * Esta función se encarga de depositar un monto
     * */

    public void depositar_monto( String cuenta, int monto,int monto_depositar){
        SQLiteDatabase db = getReadableDatabase();
        int res = 0;
        res = monto + monto_depositar;
        if (db != null){
            db.execSQL("UPDATE CUSTOMER SET MONTO ='"+res+"' WHERE CUENTA='"+cuenta+"'");
            db.close();
        }
    }


    /*
     * Esta función se encarga de debitar un monto en una cuenta en la base de datos
     * */

    public void debitar_monto(String cuenta, int monto, int monto_debitar){
        SQLiteDatabase db = getReadableDatabase();
        int res = 0;
        res = monto - monto_debitar;
        if (db != null){
            db.execSQL("UPDATE CUSTOMER SET MONTO ='"+res+"'WHERE CUENTA ='"+cuenta+"'");
            db.close();
        }
    }


    /*
     * Esta función se encarga de obtener en una cuenta de la base de datos
     * */

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


    /*
     * Esta función se encarga de obtener información respecto a un sobre de ahorro
     * */

    public  void obtener_info_sobreAhorro(SobreAhorro sobreAhorro, String cuenta, String namesobre){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor_sobres = db.rawQuery("SELECT * FROM SobreAhorro WHERE NAMESOBRE='"+namesobre+"' AND CUENTA ='"+cuenta+"'",null);
        if (cursor_sobres.moveToFirst()){
            sobreAhorro.setId(cursor_sobres.getInt(0));
            sobreAhorro.setCuenta(cursor_sobres.getString(1));
            sobreAhorro.setNamesobre(cursor_sobres.getString(2));
            sobreAhorro.setMonto(cursor_sobres.getInt(3));
        }
    }


    /*
     * Esta función se encarga de cargar un sobre en la base de datos
     * */

    public void cargar_sobre(String cuenta, String namesobre, int montoSobre, int montoCargar){
        SQLiteDatabase db = getReadableDatabase();
        int res = 0;
        res = montoSobre + montoCargar;
        if (db != null){
            db.execSQL("UPDATE SobreAhorro SET MONTO ='"+res+"'WHERE CUENTA ='"+cuenta+"' AND NAMESOBRE ='"+namesobre+"'");
            db.close();
        }

    }


    /*
     * Esta función se encarga de retirar sobres de la base de datos
     * */

    public  void retirar_sobre(String cuenta, String namesobre, int monto, int montoRetirar){
        SQLiteDatabase db = getReadableDatabase();
        int res = 0;
        res = monto - montoRetirar;
        if (db != null){
            db.execSQL("UPDATE SobreAhorro SET MONTO ='"+res+"'WHERE CUENTA ='"+cuenta+"' AND NAMESOBRE ='"+namesobre+"'");
            db.close();
        }
    }


    /*
     * Esta función se encarga de eliminar sobres de la base de datos
     * */

    public void eliminar_sobre(String cuenta, String namesobre){
        SQLiteDatabase db = getReadableDatabase();
        if (db != null){
            db.execSQL("DELETE FROM SobreAhorro WHERE CUENTA='"+cuenta+"'AND NAMESOBRE='"+namesobre+"'");
            db.close();
        }
    }

    public Cursor getSobres(String cuenta){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from HistorialSobres where cuenta='"+cuenta+"'",null);
        return cursor;
    }


    /*
     * Esta función se encarga de cerrar la base de datos
     * */

    public void CLOSE(){
        this.close();
    }
}
