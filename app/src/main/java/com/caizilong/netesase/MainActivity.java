package com.caizilong.netesase;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.caizilong.netesase.splash.TimeView;

public class MainActivity extends AppCompatActivity {

    private TimeView timeView;

    private int length = 2 * 1000;

    private int space = 250;

    private int now = 0;

    Handler mHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeView = (TimeView)findViewById(R.id.timeView);

        timeView.setD(200);


        final int total = length / space;

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        int nows = msg.arg1;

                        if (nows <= total){
                            timeView.setProgress(total, nows);
                        }else {
                            mHandler.removeCallbacks(runnable);
                        }
                        break;
                }

            }
        };

        mHandler.post(runnable);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
                Message message = mHandler.obtainMessage(0);

                message.arg1 = now;

                mHandler.sendMessage(message);

                mHandler.postDelayed(this, space);
                now++;
        }
    };

}
