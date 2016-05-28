package com.lqs.androidbenchmark;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by lqs on 16-5-28.
 */
public class BenchmarkTask extends AsyncTask<Integer, Void, Long> {
    private ProgressDialog progressDialog;
    private MainActivity.ResultProcessor resultProcessor;

    public BenchmarkTask(ProgressDialog progressDialog, MainActivity.ResultProcessor resultProcessor) {
        this.progressDialog = progressDialog;
        this.resultProcessor = resultProcessor;
    }

    @Override
    protected Long doInBackground(Integer[] params) {
        int threadNumber = params[0];
        int period = params[1];
        MultiThreadBenchmark multiThreadBenchmark = new MultiThreadBenchmark(threadNumber, period);
        return multiThreadBenchmark.excuteBenchmark();
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
