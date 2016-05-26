package com.lqs.androidbenchmark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lqs on 16-5-27.
 */
public class MultiThreadBenchmark {
    long startTime;
    ExecutorService fixedThreadPool;
    private int threadNumber;
    private int period;
    private int alreadyOver = 0;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            BenchmarkJNI.CalculatePi(period);
            addOver();
        }
    };

    public MultiThreadBenchmark(int threadNumber, int period) {
        this.threadNumber = threadNumber;
        this.period = period;
    }

    private synchronized void addOver() {
        alreadyOver++;
    }

    private void singleThreadRun() {
        BenchmarkJNI.CalculatePi(period);
        addOver();
    }

    public long excuteBenchmark() {
        //fixedThreadPool = Executors.newFixedThreadPool(threadNumber+1);
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
