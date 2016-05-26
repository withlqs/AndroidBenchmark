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

        Button button = (Button) findViewById(R.id.button);
        periodEditText = (EditText) findViewById(R.id.periodEditText);
        periodEditText.setText("1000");
        threadNumberEditText = (EditText) findViewById(R.id.threadNumberEditText);
        threadNumberEditText.setText("2");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Integer integer = new Integer(periodEditText.getText().toString());
//                Date now1 = new Date();
//                double pi = BenchmarkJNI.CalculatePi(integer);
//                Date now2 = new Date();
//                textView.setText(new Double(pi).toString() + " Time:" + (now2.getTime() - now1.getTime()));
                int period = new Integer(periodEditText.getText().toString());
                int threadNumber = new Integer(threadNumberEditText.getText().toString());
                MultiThreadBenchmark multiThreadBenchmark = new MultiThreadBenchmark(threadNumber, period);
                textView.setText("" + multiThreadBenchmark.excuteBenchmark());
            }
        });

    }
}
