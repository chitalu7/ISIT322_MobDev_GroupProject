package com.hfad.globegoproject.stepcounter;

 class AccelerationDataPoint {
    int value;
    long time;

    AccelerationDataPoint(float x, float y, float z, long time) {
        this.value = (int)Math.round(Math.sqrt(x * x + y * y + z * z) * 100.0f);
        this.time = System.currentTimeMillis() + (time - System.nanoTime()) / 1000000L;
    }
 }
