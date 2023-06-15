package com.hfad.globegoproject.ui.postmessage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hfad.globegoproject.R;
import com.hfad.globegoproject.ui.utilityclasses.DatabaseHelper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.widget.Toast;
import androidx.core.app.ActivityCompat;

public class PostMessageFragment extends Fragment implements LocationListener {

    private static final int PERMISSION_REQUEST_CODE = 1001;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private EditText geoMessage;
    private Button postMessage;
    private SQLiteOpenHelper dbHelper;
    DatabaseHelper databaseHelper;
    private String message = "";
    private double latitude;
    private double longitude;
    private boolean locationObtained = false;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_message, container, false);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        databaseHelper = new DatabaseHelper(getActivity());
        geoMessage = view.findViewById(R.id.geoMessageText);
        postMessage = view.findViewById(R.id.post_message);

        dbHelper = new DatabaseHelper(getContext());

        postMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestLocationPermission();

            }
        });

        return view;
    }

    private void requestLocationPermission() {

        if (locationObtained) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);

            return;


        }

        getLocation();

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                //saveToDatabase(latitude, longitude);
                insertData(latitude, longitude);

                //locationObtained = true;


            }

        };
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
        locationObtained = true;
    }

    private void insertData (double latitude, double longitude) {

        String message = geoMessage.getText().toString().trim();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("message", message);
        values.put("latitude", latitude);
        values.put("longitude", longitude);

        long result = db.insert("my_table", null, values);

        if (result == -1) {
            Toast.makeText(getContext(), "Failed to insert data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "LA: " + latitude + "LO: " + longitude, Toast.LENGTH_LONG).show();
            geoMessage.setText("");
        }

        db.close();
//        String message = geoMessage.getText().toString().trim();
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put("message", message);
//
//        long result = db.insert("my_table", null, values);
//
//        if (result == -1) {
//            Toast.makeText(getContext(), "Failed to insert data", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
//            geoMessage.setText("");
//        }
//
//        db.close();
    }



    private void saveLocationToDatabase(double latitude, double longitude) {
        SQLiteDatabase database = getActivity().openOrCreateDatabase("my_database", Context.MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS locations (message STRING, latitude DOUBLE, longitude DOUBLE)");
        String insertQuery = "INSERT INTO locations (latitude, longitude) VALUES (?, ?)";
        SQLiteStatement statement = database.compileStatement(insertQuery);
        statement.bindDouble(1, latitude);
        statement.bindDouble(2, longitude);
        statement.executeInsert();
        database.close();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }


    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

}