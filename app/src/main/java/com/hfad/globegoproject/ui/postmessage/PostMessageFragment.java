package com.hfad.globegoproject.ui.postmessage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.example.sqlitefragments.R;
//import com.example.sqlitefragments.ui.utilityclasses.DatabaseHelper;
import com.hfad.globegoproject.R;
import com.hfad.globegoproject.ui.utilityclasses.DatabaseHelper;


public class PostMessageFragment extends Fragment {

    private EditText geoMessage;
    private Button postMessage;
    private SQLiteOpenHelper dbHelper;
    DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_message, container, false);

        geoMessage = view.findViewById(R.id.geoMessageText);
        postMessage = view.findViewById(R.id.post_message);

        dbHelper = new DatabaseHelper(getContext());

        postMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        return view;
    }

    private void insertData() {
        String message = geoMessage.getText().toString().trim();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("message", message);

        long result = db.insert("my_table", null, values);

        if (result == -1) {
            Toast.makeText(getContext(), "Failed to insert data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
            geoMessage.setText("");
        }

        db.close();
    }

}