package com.hfad.globegoproject.ui.utilityclasses;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//import com.example.sqlitefragments.ui.utilityclasses.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DatabaseManager(Context context) {

        dbHelper = new DatabaseHelper(context);
    }

    public void open() {

        database = dbHelper.getWritableDatabase();
    }

    public void close() {

        dbHelper.close();
    }

    public void insertData(String message, double latitude, double longitude) {
        ContentValues values = new ContentValues();
        values.put("message", message);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        database.insert("my_table", null, values);
    }

    public List<String> getAllData() {
        List<String> dataList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM my_table", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex("message"));
                dataList.add(message);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return dataList;
    }

    public void updateData(int id, String message) {
        ContentValues values = new ContentValues();
        values.put("message", message);
        database.update("my_table", values, "id=?", new String[]{String.valueOf(id)});
    }

    public void deleteData(int id) {
        database.delete("my_table", "id=?", new String[]{String.valueOf(id)});
    }
}
