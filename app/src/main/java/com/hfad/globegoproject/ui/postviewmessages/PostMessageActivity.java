package com.hfad.globegoproject.ui.postviewmessages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hfad.globegoproject.R;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PostMessageActivity extends AppCompatActivity {

    EditText message;
    Button insert, read;
    DbAdapter myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);

        message = findViewById(R.id.messageEditText);
        insert = findViewById(R.id.insert);

        myDbHelper = new DbAdapter(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

//        read.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                readData(view);
//            }
//        });

    }

    public void saveData(){
        String getMessage = message.getText().toString();

        if (getMessage.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a message",
                    Toast.LENGTH_LONG).show();
        } else {
            long id = myDbHelper.insertData(getMessage);
            if (id<=0){
                Toast.makeText(getApplicationContext(), "Data was not saved successfully",
                        Toast.LENGTH_LONG).show();
                message.setText("");

            } else {
                Toast.makeText(getApplicationContext(), "Data was saved successfully!",
                        Toast.LENGTH_LONG).show();
                message.setText("");

            }
        }
    }

    public void readData( View view ){
        String Data=myDbHelper.readData();
        Intent intent = new Intent(getBaseContext(), ReadData.class);
        intent.putExtra("data", Data);
        startActivity(intent);
    }
}