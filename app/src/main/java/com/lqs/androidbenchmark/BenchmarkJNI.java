package com.lqs.androidbenchmark;

/**
 * Created by lqs on 16-5-25.
 */
public class BenchmarkJNI {
    static {
        System.loadLibrary("CalculatePiJNI");
    }

    public static native double multiThreadBenchmark(int threadNumber, int period);

    public static native void singleThreadFloatBenchmark(int period);

    public static native void singleThreadIntergerBenchmark(int period);

    public static native String getDemoString(int i);
}
