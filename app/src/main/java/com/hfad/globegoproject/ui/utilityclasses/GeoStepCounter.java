package com.hfad.globegoproject.ui.utilityclasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class GeoStepCounter implements SensorEventListener {
    private static final int SECONDS_PER_DAY = 60 * 60 * 24;

    public GeoStepCounter(AppCompatActivity activity) {
        sensorManager = activity.getSystemService(SensorManager.class);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        locationManager = activity.getSystemService(LocationManager.class);
        geoLocationProviderName = getLocationProviderName();
        context = activity.getApplicationContext();
        executor = activity.getApplicationContext().getMainExecutor();
        geoSteps = Collections.synchronizedList(new ArrayList<GeoStepCount>(SECONDS_PER_DAY));
    }

    public void start() {
        if (!checkLocationPermissions()) {
            throw new IllegalStateException("Location services are not accessible.");
        }
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    public List<GeoStepCount> getGeoSteps() {
        return geoSteps;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Float stepsCount = sensorEvent.values[0];
        locationManager.getCurrentLocation(
                geoLocationProviderName,
                null,
                executor,
                (location) -> {
                    GeoStepCount geoStepCount = new GeoStepCount(location, stepsCount);
                    geoSteps.add(geoStepCount);
                });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public boolean checkLocationPermissions() {
        return ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    @NonNull
    private final SensorManager sensorManager;

    @NonNull
    private Sensor stepCounterSensor;

    @NonNull
    private final LocationManager locationManager;

    @NonNull
    private final String geoLocationProviderName;

    @NonNull
    private final Context context;

    @NonNull
    private final Executor executor;

    @NonNull
    private final List<GeoStepCount> geoSteps;

    private String getLocationProviderName() {
        Set<String> locationProviders = new HashSet<>(locationManager.getAllProviders());
        if (locationProviders.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        }
        if (locationProviders.contains(LocationManager.FUSED_PROVIDER)) {
            return LocationManager.FUSED_PROVIDER;
        }
        return locationProviders.stream().findFirst().get();
    }
}
