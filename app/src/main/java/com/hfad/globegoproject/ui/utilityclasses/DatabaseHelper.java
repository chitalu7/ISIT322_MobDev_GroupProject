package com.hfad.globegoproject.ui.utilityclasses;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_table";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + COLUMN_MESSAGE + " TEXT, "
            + COLUMN_LATITUDE + " DOUBLE, "
            + COLUMN_LONGITUDE + " DOUBLE)";
//    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
//            COLUMN_NAME + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
//    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create the table
        db.execSQL(CREATE_TABLE_QUERY);

        // Create your database tables here
//        String createTableQuery = "CREATE TABLE IF NOT EXISTS my_table (ID INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT)";
////        String createTableQuery = "CREATE TABLE IF NOT EXISTS my_table (id INTEGER PRIMARY KEY, message TEXT)";
//        db.execSQL(createTableQuery);
    }


//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//        // Create the table
//        db.execSQL(CREATE_TABLE_QUERY);
//
//    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS my_table");
            onCreate(db);
        }
    }
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Handle database upgrades here
//        if (newVersion > oldVersion) {
//            db.execSQL("DROP TABLE IF EXISTS my_table");
//            onCreate(db);
//        }
//    }
}

