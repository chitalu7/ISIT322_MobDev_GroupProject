package com.hfad.globegoproject.stepcounter;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

interface StepCounter {
    void start(StepCounterEventListener listener);
    void stop();

    static StepCounter create(Activity activity) {
        StepCounterNativeImpl nativeImpl = new StepCounterNativeImpl(activity);
        if (nativeImpl.nativeStepCounterSupported()) {
            return nativeImpl;
        }
        return new StepCounterAccelerometerImpl(activity);
    }
}
