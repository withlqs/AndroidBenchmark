package com.lqs.androidbenchmark;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText periodEditText;
    private EditText threadNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.testView);
        textView.setText(BenchmarkJNI.getDemoString(1));

        Button button1 = (Button) findViewById(R.id.button1);
        periodEditText = (EditText) findViewById(R.id.periodEditText);
        periodEditText.setText("100000000");
        threadNumberEditText = (EditText) findViewById(R.id.threadNumberEditText);
        threadNumberEditText.setText("1");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int period = new Integer(periodEditText.getText().toString());
                int threadNumber = new Integer(threadNumberEditText.getText().toString());
                ResultProcessor resultProcessor = new ResultProcessor();
                BenchmarkTask benchmarkTask = new BenchmarkTask(getProgressDialog(), resultProcessor, BenchmarkMethod.floatBenchmarkMethod);
                benchmarkTask.execute(threadNumber, period);
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int period = new Integer(periodEditText.getText().toString());
                int threadNumber = new Integer(threadNumberEditText.getText().toString());
                ResultProcessor resultProcessor = new ResultProcessor();
                BenchmarkTask benchmarkTask = new BenchmarkTask(getProgressDialog(), resultProcessor, BenchmarkMethod.integerBenchmarkMethod);
                benchmarkTask.execute(threadNumber, period);
//                MultiThreadFramework multiThreadBenchmark = new MultiThreadFramework(threadNumber, period);
//                textView.setText("" + multiThreadBenchmark.excuteBenchmark());
            }
        });
    }

    private ProgressDialog getProgressDialog() {
        return getProgressDialog("Benchmarking...");
    }

    private ProgressDialog getProgressDialog(String message) {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public class ResultProcessor {
        public void sendResult(long time) {
            MainActivity.this.textView.setText("" + time);
        }
    }
}
