package com.hfad.globegoproject.stepcounter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class GeoStepCounter implements StepCounterEventListener {
    private static final int SECONDS_PER_DAY = 60 * 60 * 24;

    public GeoStepCounter(Activity activity) {
        stepCounter = StepCounter.create(activity);
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
        stepCounter.start(this);
    }

    public void stop() {
        stepCounter.stop();
    }

    public List<GeoStepCount> getGeoSteps() {
        return geoSteps;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onStepCounterChange(int stepCount) {
        locationManager.getCurrentLocation(
                geoLocationProviderName,
                null,
                executor,
                (location) -> {
                    GeoStepCount geoStepCount = new GeoStepCount(location, stepCount);
                    geoSteps.add(geoStepCount);
                });

    }

    public boolean checkLocationPermissions() {
        return ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @NonNull
    private final StepCounter stepCounter;

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
