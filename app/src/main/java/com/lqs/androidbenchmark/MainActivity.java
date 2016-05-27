package com.lqs.androidbenchmark;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText periodEditText;
    EditText threadNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.testView);
        textView.setText(BenchmarkJNI.getDemoString(1));

        Button button1 = (Button) findViewById(R.id.button1);
        periodEditText = (EditText) findViewById(R.id.periodEditText);
        periodEditText.setText("1000");
        threadNumberEditText = (EditText) findViewById(R.id.threadNumberEditText);
        threadNumberEditText.setText("2");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int period = new Integer(periodEditText.getText().toString());
                int threadNumber = new Integer(threadNumberEditText.getText().toString());
                double time = BenchmarkJNI.multiThreadBenchmark(threadNumber, period);
                textView.setText("" + time);
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int period = new Integer(periodEditText.getText().toString());
                int threadNumber = new Integer(threadNumberEditText.getText().toString());
                MultiThreadBenchmark multiThreadBenchmark = new MultiThreadBenchmark(threadNumber, period);
                textView.setText("" + multiThreadBenchmark.excuteBenchmark());
            }
        });


    }
}
