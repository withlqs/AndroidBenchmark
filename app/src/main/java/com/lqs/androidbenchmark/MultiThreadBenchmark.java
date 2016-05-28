package com.lqs.androidbenchmark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lqs on 16-5-27.
 */

public class MultiThreadBenchmark {

    long startTime;
    ExecutorService fixedThreadPool;
    BenchmarkMethod benchmarkMethod;
    private int threadNumber;
    private int period;
    private int alreadyOver = 0;

    public MultiThreadBenchmark(int threadNumber, int period, final BenchmarkMethod benchmarkMethod) {
        this.threadNumber = threadNumber;
        this.period = period;
        this.benchmarkMethod = benchmarkMethod;
    }

    private synchronized void addOver() {
        alreadyOver++;
    }

    private void singleThreadRun() {
        BenchmarkJNI.singleThreadFloatBenchmark(period);
        addOver();
    }

    public long excuteBenchmark() {
        alreadyOver = 0;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                benchmarkMethod.run(MultiThreadBenchmark.this.period);
                MultiThreadBenchmark.this.addOver();
            }
        };

        fixedThreadPool = Executors.newCachedThreadPool();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            fixedThreadPool.execute(runnable);
        }
        while (alreadyOver < threadNumber) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return System.currentTimeMillis() - startTime;
    }
}
