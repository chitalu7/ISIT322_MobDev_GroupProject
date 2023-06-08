package com.hfad.globegoproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.hfad.globegoproject.stepcounter.GeoStepCounter;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class GeoStepCounterDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_step_counter_demo);

        geoStepCounter = new GeoStepCounter(this);

        Button buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener((v) -> {
            TextView tvw = (TextView)findViewById(R.id.geoStepsLog);
            tvw.append("Start clicked" + System.lineSeparator());
            geoStepCounter.start();
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Button buttonStop = findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener((v) -> {
            TextView tvw = (TextView)findViewById(R.id.geoStepsLog);
            tvw.append("Stop clicked" + System.lineSeparator());
            geoStepCounter.stop();
            geoStepCounter.getGeoSteps().forEach(geoStepCount ->
                    tvw.append(String.format(Locale.US, "%d steps at %s, location: (%f, %f)\n",
                        geoStepCount.getStepsCount(),
                        sdf.format(geoStepCount.getDate()),
                        geoStepCount.getLattitude(),
                        geoStepCount.getLongitude())));
            geoStepCounter.getGeoSteps().clear();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Objects.requireNonNull(geoStepCounter).checkLocationPermissions()) {
            permissionRequester.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Nullable
    private GeoStepCounter geoStepCounter;

    private ActivityResultLauncher<String> permissionRequester =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        TextView tvw = (TextView)findViewById(R.id.geoStepsLog);
                        if (isGranted) {
                            tvw.append("Permission granted" + System.lineSeparator());
                        } else {
                            tvw.append("Permission not granted" + System.lineSeparator());
                        }
                    });
}