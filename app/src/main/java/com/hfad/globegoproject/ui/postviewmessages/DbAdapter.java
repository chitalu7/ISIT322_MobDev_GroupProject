package com.hfad.globegoproject.ui.postviewmessages;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;



// This class controls the basic CRUD of the application
public class DbAdapter {

    DbHelper myHelper;

    public DbAdapter(Context context){
        myHelper = new DbHelper(context);
    }

    public long insertData(String message) {
        SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.MESSAGE, message);
        //contentValues.put(DbHelper.DATE, date);

        long id = sqLiteDatabase.insert(DbHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public String readData(){
        SQLiteDatabase db = myHelper.getWritableDatabase();

        String[] columns = {DbHelper.UID, DbHelper.MESSAGE, DbHelper.DATE};
        Cursor cursor=db.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null);

        StringBuffer stringBuffer = new StringBuffer();

        while (cursor.moveToNext()){
            @SuppressLint("Range") int cursorid = cursor.getInt(cursor.getColumnIndex(DbHelper.UID));
            @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex(DbHelper.MESSAGE));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DbHelper.DATE));

            stringBuffer.append("Cursorid: " + cursorid + "\n" + "Message: " + message + "\n" + date + "\n");
        }

        return stringBuffer.toString();
    }

    static class DbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME="sqLiteDatabase";
        private static final String TABLE_NAME="myTable";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String MESSAGE = "Message";
        private static final String DATE = "getDateCreated";

        private static final String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME +
                " ("+ UID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + MESSAGE + " VARCHAR(255), " +
                DATE + "VARCHAR(255));";

        private Context context;

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS" + TABLE_NAME;

        public DbHelper (Context context){
            super (context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Toast.makeText(context, "Error" + e, Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

            try {
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);
            } catch (Exception e) {
                Toast.makeText(context, "error" + e, Toast.LENGTH_LONG).show();
            }

        }

    }
}
