package BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SQLiteConnection extends SQLiteOpenHelper {

    final String CREATE_TABLE = "CREATE TABLE Customer (ID integer primary key autoincrement,name TEXT," +
            "correo TEXT,cuenta TEXT, contraseña TEXT)";
    public SQLiteConnection(@Nullable Context context) {
        super(context, "Customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    // Si queremos escribir algo en la base de datos
    public void write(){
        this.getWritableDatabase();

    }
    // Si queremos buscar algo en la base de datos
    public void read(){
        this.getReadableDatabase();
    }

    // Metodo que me permite insetar valores en la tabla usuarios

    public void DataInsert(String usuario, String correo, String cuenta, String contraseña){
        ContentValues data = new ContentValues();
        data.put("name",usuario);
        data.put("correo",correo);
        data.put("cuenta",cuenta);
        data.put("contraseña",contraseña);
        this.getWritableDatabase().insert("Customer",null,data);
    }
    // Permite validar si usario existe

    public Cursor login(String usuario,String contraseña) throws SQLException{
        Cursor cursor = null;

        cursor = this.getReadableDatabase().query("Customer",new String[]{"ID","name","correo","cuenta","contraseña"},
                "name like '"+usuario+"' and contraseña like '"+contraseña+"'"
                ,null,null,null,null);

        return cursor;
    }




    public void CLOSE(){
        this.close();
    }
}
