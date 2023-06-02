package com.hfad.globegoproject.ui.postviewmessages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.hfad.globegoproject.R;

public class ReadData extends AppCompatActivity {

    TextView readMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        readMessage = findViewById(R.id.readMessage);
        String readData = getIntent().getStringExtra("data");
        readMessage.setText(readData);

    }
}
