package com.hfad.globegoproject.stepcounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

class StepCounterAccelerometerImpl implements StepCounter, SensorEventListener {
    private static final int g = 981;
    private static final int MIN_SAMPLE_SIZE = 4;
    private static final long STEP_TIME_MILLIS_THRESHOLD = 100L;
    private static final int ACCELERATION_STEP_THRESHOLD = 50;

    public StepCounterAccelerometerImpl(AppCompatActivity activity) {
        states = new LinkedList<>();
        sensorManager = activity.getSystemService(SensorManager.class);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        addToStates(new AccelerationDataPoint(
                sensorEvent.values[0],
                sensorEvent.values[1],
                sensorEvent.values[2],
                sensorEvent.timestamp
        ));
        if (states.size() > MIN_SAMPLE_SIZE) {
            detectSteps();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void start(StepCounterEventListener listener) {
        eventListener = listener;
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void stop() {
        eventListener = null;
        sensorManager.unregisterListener(this);
    }

    private void detectSteps() {
        int prevStepCounterValue = stepCounterValue;
        states.forEach(state -> {
            float acceleration = state.value - g;
            if (state.positiveAcceration
                    && acceleration > ACCELERATION_STEP_THRESHOLD
                    && state.timestamp > lastStepTimestamp
            ) {
                ++stepCounterValue;
                lastStepTimestamp = state.timestamp;
            }
        });
        cleanUpStates();
        if (stepCounterValue > prevStepCounterValue && Objects.nonNull(eventListener)) {
            eventListener.onStepCounterChange(stepCounterValue);
        }
    }

    private void cleanUpStates() {
        List<StepCounterState> forRemoval = new LinkedList<>();
        states.forEach(state -> {
            if (state.timestamp <= lastStepTimestamp) {
                forRemoval.add(state);
            }
        });
        forRemoval.forEach(states::remove);
    }

    private void addToStates(AccelerationDataPoint adp) {
        StepCounterState newState = new StepCounterState(adp.time, adp.value);
        if (states.isEmpty()) {
            states.add(newState);
        } else {
            StepCounterState prevState = states.get(states.size() - 1);
            if (prevState.value != newState.value) {
                if (prevState.positiveAcceration == newState.positiveAcceration) {
                    if (prevState.positiveAcceration) {
                        if (prevState.value < newState.value) {
                            prevState.value = newState.value;
                        }
                    } else {
                        if (prevState.value > newState.value) {
                            prevState.value = newState.value;
                        }
                    }
                } else {
                    long dtMillis = newState.timestamp - prevState.timestamp;
                    if (dtMillis < STEP_TIME_MILLIS_THRESHOLD) {
                        prevState.timestamp = newState.timestamp;
                        prevState.value = newState.value;
                        prevState.positiveAcceration = newState.positiveAcceration;
                    } else {
                        states.add(newState);
                    }
                }
            }
        }
    }

    static class StepCounterState {
        StepCounterState(long t, int val) {
            timestamp = t;
            positiveAcceration = val > g;
            value = val;
        }

        long timestamp;
        boolean positiveAcceration;
        int value;
    }

    private final List<StepCounterState> states;
    private int stepCounterValue = 0;
    private long lastStepTimestamp = 0L;

    @NonNull
    private final SensorManager sensorManager;

    @NonNull
    private final Sensor accelerometerSensor;

    private StepCounterEventListener eventListener;
}
