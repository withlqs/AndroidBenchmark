package com.lqs.androidbenchmark;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<BenchmarkTask> taskList = new ArrayList<>();
    List<Long> resultList = new ArrayList<>();
    int[] idList = {
            R.id.single_int,
            R.id.multi_int,
            R.id.single_float,
            R.id.multi_float,

    };
    private int nowTaskPtr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textView = (TextView) findViewById(R.id.testView);
//        textView.setText(BenchmarkJNI.getDemoString(1));
//
//        periodEditText = (EditText) findViewById(R.id.periodEditText);
//        periodEditText.setText("100000000");
//        threadNumberEditText = (EditText) findViewById(R.id.threadNumberEditText);
//        threadNumberEditText.setText("1");
//
//        Button button1 = (Button) findViewById(R.id.button1);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int period = new Integer(periodEditText.getText().toString());
//                int threadNumber = new Integer(threadNumberEditText.getText().toString());
//                ResultProcessor resultProcessor = new ResultProcessor();
//                BenchmarkTask benchmarkTask = new BenchmarkTask(
//                        getProgressDialog(),
//                        resultProcessor,
//                        BenchmarkMethod.floatBenchmarkMethod,
//                        threadNumber, period
//                );
//                benchmarkTask.execute();
//            }
//        });
//
//        Button button2 = (Button) findViewById(R.id.button2);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int period = new Integer(periodEditText.getText().toString());
//                int threadNumber = new Integer(threadNumberEditText.getText().toString());
//                ResultProcessor resultProcessor = new ResultProcessor();
//                BenchmarkTask benchmarkTask = new BenchmarkTask(
//                        getProgressDialog(),
//                        resultProcessor,
//                        BenchmarkMethod.integerBenchmarkMethod,
//                        threadNumber,
//                        period
//                );
//                benchmarkTask.execute();
//            }
//        });


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowTaskPtr = 0;
                resultList.clear();
                taskList.clear();
                taskList.add(new BenchmarkTask(
                        getProgressDialog("正在进行CPU单线程整数性能测试"),
                        new ResultProcessor(),
                        BenchmarkMethod.integerBenchmarkMethod,
                        1,
                        100000000
                ));

                taskList.add(new BenchmarkTask(
                        getProgressDialog("正在进行CPU多线程整数性能测试"),
                        new ResultProcessor(),
                        BenchmarkMethod.integerBenchmarkMethod,
                        8,
                        3000000
                ));

                taskList.add(new BenchmarkTask(
                        getProgressDialog("正在进行CPU单线程浮点数性能测试"),
                        new ResultProcessor(),
                        BenchmarkMethod.floatBenchmarkMethod,
                        1,
                        100000000
                ));

                taskList.add(new BenchmarkTask(
                        getProgressDialog("正在进行CPU多线程浮点数性能测试"),
                        new ResultProcessor(),
                        BenchmarkMethod.floatBenchmarkMethod,
                        8,
                        3000000
                ));
                taskList.get(0).execute();
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
        private long calculateSingle(long time) {
            return 22094550 / time;
        }

        private long calculateMulti(long time) {
            return 143617200 / time;
        }

        public void sendResult(long time) {

            if (nowTaskPtr % 2 == 0) {
                resultList.add(calculateSingle(time));
            } else {
                resultList.add(calculateMulti(time));
            }

            TextView textView = (TextView) findViewById(idList[nowTaskPtr]);
            textView.setText("" + resultList.get(nowTaskPtr));

            ++nowTaskPtr;
            if (nowTaskPtr < taskList.size()) {
                taskList.get(nowTaskPtr).execute();
            } else {
            }
        }
    }
}
