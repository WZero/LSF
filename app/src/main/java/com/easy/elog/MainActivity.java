package com.easy.elog;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.socks.library.KLog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private NestedScrollView scrollView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KLog.d("qqq");
        handler = new Handler();
        textView = findViewById(R.id.main_print_text);
        scrollView = findViewById(R.id.main_ScrollView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                KLog.d("currentTimeMillis --- " + System.currentTimeMillis());
                break;
            case R.id.button3:
                Integer.parseInt("aaa");
                break;
        }
    }

    private void setTextView(final CharSequence text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(String.format("%s\n%s", textView.getText().toString(), text));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
                    }
                }, 50);
            }
        });
    }
}