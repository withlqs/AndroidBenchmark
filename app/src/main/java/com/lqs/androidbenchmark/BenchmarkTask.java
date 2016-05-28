package com.lqs.androidbenchmark;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class BenchmarkTask extends AsyncTask<Void, Void, Long> {

    public static final int INTEGER_BENCHMARK = 0;
    public static final int FLOAT_BENCHMARK = 1;

    private ProgressDialog progressDialog;
    private MainActivity.ResultProcessor resultProcessor;
    private BenchmarkMethod benchmarkMethod;
    private int threadNumber;
    private int period;

    public BenchmarkTask(ProgressDialog progressDialog, MainActivity.ResultProcessor resultProcessor, BenchmarkMethod benchmarkMethod, int threadNumber, int period) {
        this.progressDialog = progressDialog;
        this.resultProcessor = resultProcessor;
        this.benchmarkMethod = benchmarkMethod;
        this.threadNumber = threadNumber;
        this.period = period;
    }

    @Override
    protected Long doInBackground(Void[] params) {
        MultiThreadFramework multiThreadFramework = new MultiThreadFramework(threadNumber, period, benchmarkMethod);
        return multiThreadFramework.excuteBenchmark();
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Long time) {
        progressDialog.dismiss();
        resultProcessor.sendResult(time);
    }
}
