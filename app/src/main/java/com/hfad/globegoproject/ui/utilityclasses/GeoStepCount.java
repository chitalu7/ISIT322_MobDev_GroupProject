package com.hfad.globegoproject.ui.utilityclasses;

import android.location.Location;

import androidx.annotation.NonNull;

import java.time.Instant;
import java.util.Date;

public class GeoStepCount {
    public GeoStepCount(Location where, float steps) {
        lattitude = where.getLatitude();
        longitude = where.getLongitude();
        stepsCount = (int)steps;
        timestamp = where.getTime();
        date = Date.from(Instant.ofEpochSecond(timestamp));
    }

    public double getLattitude() {
        return lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getStepsCount() {
        return stepsCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Date getDate() {
        return date;
    }

    private double lattitude;
    private double longitude;
    private int stepsCount;
    private long timestamp;
    @NonNull
    private final Date date;
}
