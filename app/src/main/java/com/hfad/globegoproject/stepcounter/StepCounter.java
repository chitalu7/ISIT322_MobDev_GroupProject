package com.hfad.globegoproject.stepcounter;

import androidx.appcompat.app.AppCompatActivity;

interface StepCounter {
    void start(StepCounterEventListener listener);
    void stop();

    static StepCounter create(AppCompatActivity activity) {
        StepCounterNativeImpl nativeImpl = new StepCounterNativeImpl(activity);
        if (nativeImpl.nativeStepCounterSupported()) {
            return nativeImpl;
        }
        return new StepCounterAccelerometerImpl(activity);
    }
}
