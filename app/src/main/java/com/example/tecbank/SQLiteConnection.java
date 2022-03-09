package com.example.tecbank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SQLiteConnection extends SQLiteOpenHelper {

    final String CREATE_TABLE = "CREATE TABLE Customer (name TEXT)";
    public SQLiteConnection(@Nullable Context context) {
        super(context, "Prueba.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
