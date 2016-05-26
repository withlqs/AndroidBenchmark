package com.lqs.androidbenchmark;

/**
 * Created by lqs on 16-5-25.
 */
public class BenchmarkJNI {
    static {
        System.loadLibrary("CalculatePiJNI");
    }

    public static native double CalculatePi(int i);

    public static native String getDemoString(int i);
}
