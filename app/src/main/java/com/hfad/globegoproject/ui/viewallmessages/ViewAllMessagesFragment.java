package com.hfad.globegoproject.ui.viewallmessages;

import androidx.lifecycle.ViewModelProvider;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.globegoproject.R;
import com.hfad.globegoproject.ui.utilityclasses.DatabaseHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.widget.Button;
import android.widget.TextView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.widget.Toast;
import androidx.core.app.ActivityCompat;

public class ViewAllMessagesFragment extends Fragment {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private LocationManager locationManager;
    private SQLiteOpenHelper dbHelper;
    private TextView dbResultTextView;
    private ViewAllMessagesViewModel mViewModel;

    public static ViewAllMessagesFragment newInstance() {
        return new ViewAllMessagesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_all_messages, container, false);

        // Initialize your database helper
        dbHelper = new DatabaseHelper(getActivity());

        dbResultTextView = view.findViewById(R.id.getMessageView);

        // Read data from the database
        Button fetchDataButton = view.findViewById(R.id.fetchData);
        fetchDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read data from the database
                readData();
            }
        });
        return view;
    }

    // Reads data from Database
    private void readData() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        // Define the columns you want to fetch from the table
        String[] projection = {
                "message"
                //"latitude",
                //"longitude"
                // Add more columns as needed
        };

        // Specify the table to query
        String tableName = "my_table";

        // Query the database
        Cursor cursor = database.query(
                tableName,         // The table name to query
                projection,        // The columns to return
                null,              // The columns for the WHERE clause
                null,              // The values for the WHERE clause
                null,              // Don't group the rows
                null,              // Don't filter by row groups
                null               // The sort order
        );

        // Iterate through the cursor to retrieve the data
        StringBuilder resultText = new StringBuilder();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                //String latitude = cursor.getString(cursor.getColumnIndexOrThrow("latitude"));
                //String longitude = cursor.getString(cursor.getColumnIndexOrThrow("longitude"));

                // Append the data to the result text
                resultText.append("Message: ").append(message).append("\n");//.append(latitude).append("\n").append(longitude);


                // Do something with the data (e.g., display in UI)
                // ...
            }
            cursor.close();
        }

        // Close the database connection
        database.close();

        dbResultTextView.setText(resultText.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Close the database helper
        if (dbHelper != null) {
            dbHelper.close();
        }
    }



//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ViewAllMessagesViewModel.class);
//        // TODO: Use the ViewModel
//    }

}
//
//import androidx.lifecycle.ViewModelProvider;
//
//import android.location.LocationListener;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.hfad.globegoproject.R;
//
//import android.annotation.SuppressLint;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.database.sqlite.SQLiteStatement;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.hfad.globegoproject.R;
//import com.hfad.globegoproject.ui.utilityclasses.DatabaseHelper;
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//
//import android.widget.Toast;
//import androidx.core.app.ActivityCompat;
//
//public class ViewAllMessagesFragment extends Fragment implements LocationListener {
//
//    private ViewAllMessagesViewModel mViewModel;
//    //    private static final int REQUEST_LOCATION_PERMISSION = 1;
////    private LocationManager locationManager;
//    private LocationManager locationManager;
//    private LocationListener locationListener;
//    private TextView geoMessage;
//    private Button fetchMessages;
//    private SQLiteOpenHelper dbHelper;
//    DatabaseHelper databaseHelper;
//
//    @SuppressLint("MissingInflatedId")
//
//    public static ViewAllMessagesFragment newInstance() {
//        return new ViewAllMessagesFragment();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_view_all_messages, container, false);
//
//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        databaseHelper = new DatabaseHelper(getActivity());
//
//        geoMessage = view.findViewById(R.id.getMessageView);
//        fetchMessages = view.findViewById(R.id.fetchData);
//
//        dbHelper = new DatabaseHelper(getContext());
//
//        fetchMessages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                getLocation();
//                //requestLocation();
//                insertData();
//            }
//        });
//
//        return view;
//    }
////        @Override
////    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
////                             @Nullable Bundle savedInstanceState) {
////        return inflater.inflate(R.layout.fragment_view_all_messages, container, false);
////    }
//
//    private void getLocation() {
//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//
//                saveLocationToDatabase(latitude, longitude);
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//            @Override
//            public void onProviderEnabled(String provider) {}
//
//            @Override
//            public void onProviderDisabled(String provider) {}
//        };
//
//        // Request location updates
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        locationManager.removeUpdates(this);
//    }
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ViewAllMessagesViewModel.class);
//        // TODO: Use the ViewModel
//    }
//
//    private void insertData() {
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
//    }
//
//
//    private void saveLocationToDatabase(double latitude, double longitude) {
//        SQLiteDatabase database = getActivity().openOrCreateDatabase("my_database", Context.MODE_PRIVATE, null);
//        database.execSQL("CREATE TABLE IF NOT EXISTS locations (message STRING, latitude REAL, longitude REAL)");
//        String insertQuery = "INSERT INTO locations (latitude, longitude) VALUES (?, ?)";
//        SQLiteStatement statement = database.compileStatement(insertQuery);
//        statement.bindDouble(1, latitude);
//        statement.bindDouble(2, longitude);
//        statement.executeInsert();
//        database.close();
//    }
//
//    @Override
//    public void onLocationChanged(@NonNull Location location) {
//
//    }
//}