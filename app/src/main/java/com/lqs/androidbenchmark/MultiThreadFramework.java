package com.lqs.androidbenchmark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by lqs on 16-5-27.
 */

public class MultiThreadFramework {

    long startTime;
    ExecutorService fixedThreadPool;
    BenchmarkMethod benchmarkMethod;
    private int threadNumber;
    private int period;
    private Runnable runnable;
//    private int alreadyOver = 0;

    public MultiThreadFramework(int threadNumber, int period, final BenchmarkMethod benchmarkMethod) {
        this.threadNumber = threadNumber;
        this.period = period;
        this.benchmarkMethod = benchmarkMethod;
        this.runnable = new Runnable() {
            @Override
            public void run() {
                benchmarkMethod.run(MultiThreadFramework.this.period);
                //MultiThreadFramework.this.addOver();
            }
        };
    }

//    private synchronized void addOver() {
//        alreadyOver++;
//    }

//    private void singleThreadRun() {
//        BenchmarkJNI.singleThreadFloatBenchmark(period);
//        addOver();
//    }


    public long excuteBenchmark() {
//        alreadyOver = 0;

//        Thread[] threads = new Thread[threadNumber];
//        for (int i = 0; i < threadNumber; i++) {
//            threads[i] = new Thread(runnable);
//        }

        fixedThreadPool = Executors.newFixedThreadPool(threadNumber);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            fixedThreadPool.execute(runnable);
        }
        fixedThreadPool.shutdown();
        try {
            fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        while (alreadyOver < threadNumber) {
//            try {
//                Thread.sleep(50);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return System.currentTimeMillis() - startTime;
    }
}
