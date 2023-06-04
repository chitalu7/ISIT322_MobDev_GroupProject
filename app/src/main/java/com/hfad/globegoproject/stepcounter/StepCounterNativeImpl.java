package com.hfad.globegoproject.stepcounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

class StepCounterNativeImpl implements StepCounter, SensorEventListener {

    public StepCounterNativeImpl(AppCompatActivity activity) {
        sensorManager = activity.getSystemService(SensorManager.class);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    @Override
    public void start(@NonNull StepCounterEventListener listener) {
        eventListener = listener;
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void stop() {
        eventListener = null;
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (Objects.isNull(eventListener)) {
            return;
        }
        int stepCount = (int)sensorEvent.values[0];
        eventListener.onStepCounterChange(stepCount);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public boolean nativeStepCounterSupported() {
        return Objects.nonNull(stepCounterSensor);
    }

    @NonNull
    private final SensorManager sensorManager;

    @NonNull
    private final Sensor stepCounterSensor;

    private StepCounterEventListener eventListener = null;
}
