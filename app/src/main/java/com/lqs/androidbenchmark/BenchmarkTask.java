package com.lqs.androidbenchmark;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by lqs on 16-5-28.
 */
public class BenchmarkTask extends AsyncTask<Integer, Void, Long> {

    public static final int INTEGER_BENCHMARK = 0;
    public static final int FLOAT_BENCHMARK = 1;

    private ProgressDialog progressDialog;
    private MainActivity.ResultProcessor resultProcessor;
    private BenchmarkMethod benchmarkMethod;

    public BenchmarkTask(ProgressDialog progressDialog, MainActivity.ResultProcessor resultProcessor, BenchmarkMethod benchmarkMethod) {
        this.progressDialog = progressDialog;
        this.resultProcessor = resultProcessor;
        this.benchmarkMethod = benchmarkMethod;
    }

    @Override
    protected Long doInBackground(Integer[] params) {
        int threadNumber = params[0];
        int period = params[1];
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
