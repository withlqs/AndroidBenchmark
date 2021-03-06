package com.lqs.androidbenchmark;

public class BenchmarkMethod {
    public static final BenchmarkMethod integerBenchmarkMethod = new BenchmarkMethod() {
        @Override
        public void run(int period) {
            BenchmarkJNI.singleThreadIntergerBenchmark(period);
        }
    };
    public static final BenchmarkMethod floatBenchmarkMethod = new BenchmarkMethod() {
        @Override
        public void run(int period) {
            BenchmarkJNI.singleThreadFloatBenchmark(period);
        }
    };

    public static BenchmarkMethod getIntegerBenchmarkMethod() {
        return integerBenchmarkMethod;
    }

    public static BenchmarkMethod getFloatBenchmarkMethod() {
        return floatBenchmarkMethod;
    }

    public void run(int period) {
    }
}
