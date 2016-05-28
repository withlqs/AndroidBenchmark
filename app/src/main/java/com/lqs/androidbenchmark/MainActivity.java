package com.lqs.androidbenchmark;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<BenchmarkTask> taskList = new ArrayList<>();
    List<Long> resultList = new ArrayList<>();
    private TextView textView;
    private EditText periodEditText;
    private EditText threadNumberEditText;
    private int nowTaskPtr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.testView);
        textView.setText(BenchmarkJNI.getDemoString(1));

        periodEditText = (EditText) findViewById(R.id.periodEditText);
        periodEditText.setText("100000000");
        threadNumberEditText = (EditText) findViewById(R.id.threadNumberEditText);
        threadNumberEditText.setText("1");

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int period = new Integer(periodEditText.getText().toString());
                int threadNumber = new Integer(threadNumberEditText.getText().toString());
                ResultProcessor resultProcessor = new ResultProcessor();
                BenchmarkTask benchmarkTask = new BenchmarkTask(
                        getProgressDialog(),
                        resultProcessor,
                        BenchmarkMethod.floatBenchmarkMethod,
                        threadNumber, period
                );
                benchmarkTask.execute();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int period = new Integer(periodEditText.getText().toString());
                int threadNumber = new Integer(threadNumberEditText.getText().toString());
                ResultProcessor resultProcessor = new ResultProcessor();
                BenchmarkTask benchmarkTask = new BenchmarkTask(
                        getProgressDialog(),
                        resultProcessor,
                        BenchmarkMethod.integerBenchmarkMethod,
                        threadNumber,
                        period
                );
                benchmarkTask.execute();
            }
        });


        taskList.add(new BenchmarkTask(
                getProgressDialog("正在进行CPU单线程整数运算性能测试..."),
                new ResultProcessor(),
                BenchmarkMethod.integerBenchmarkMethod,
                1,
                100000000
        ));

        taskList.add(new BenchmarkTask(
                getProgressDialog("正在进行CPU单线程浮点数运算性能测试..."),
                new ResultProcessor(),
                BenchmarkMethod.floatBenchmarkMethod,
                1,
                100000000
        ));

        taskList.add(new BenchmarkTask(
                getProgressDialog("正在进行CPU多线程整数运算性能测试..."),
                new ResultProcessor(),
                BenchmarkMethod.integerBenchmarkMethod,
                8,
                3000000
        ));

        taskList.add(new BenchmarkTask(
                getProgressDialog("正在进行CPU多线程浮点数运算性能测试..."),
                new ResultProcessor(),
                BenchmarkMethod.floatBenchmarkMethod,
                8,
                3000000
        ));


        taskList.get(0).execute();
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
        private long calculateSingle(long time) {
            return 22679757 / time;
        }

        private long calculateMulti(long time) {
            return 191489600 / time;
        }

        public void sendResult(long time) {

            if (nowTaskPtr < 2) {
                resultList.add(calculateSingle(time));
            } else {
                resultList.add(calculateMulti(time));
            }
            textView.setText(textView.getText().toString() + "\n" + resultList.get(nowTaskPtr));

            ++nowTaskPtr;
            if (nowTaskPtr < taskList.size()) {
                taskList.get(nowTaskPtr).execute();
            }
        }
    }
}
