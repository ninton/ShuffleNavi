package jp.co.ninton.shufflenavi.util;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
    Timer mTimer;
    Handler mHandler;
    Runnable mRunnable;

    public MyTimer(Handler i_handler) {
        mHandler = i_handler;
    }

    public void start(long i_firstTime, long i_period, final Runnable i_runnable) {
        mRunnable = i_runnable;

        mTimer = new Timer(true);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(mRunnable);
            }
        }, i_firstTime, i_period);
    }

    public void cancel() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}

