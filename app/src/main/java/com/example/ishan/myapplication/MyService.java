package com.example.ishan.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.annotation.SuppressLint;
import android.util.Log;
public class MyService extends Service {
    public MyService() {
    }
    public static final String TAG = "com.example.ishan.myapplication";
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // default - return super.onStartCommand(intent, flags, startId);
        Log.i(TAG,"L42 onStartCommand called");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // What do you want the thread to do
                for (int i = 0; i < 5; i++) {
                    long futureTime = (System.currentTimeMillis() + 5000);// 5 sec

                    while (System.currentTimeMillis() < futureTime) {
                        // Don't need to sync when you are not using a thread e,g, Tutorial 39
                        synchronized (this) {
                            try {
                                wait(futureTime - System.currentTimeMillis());
                                Log.i(TAG, "L42 loop "+ i + " finished");
                            } catch (Exception e) {

                            }
                        }
                    }
                }
            }
        };
        // start the thread
        Thread less42Thread = new Thread(r);
        less42Thread.start();
        return Service.START_STICKY; // restarts the service if it gets destroyed.
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDestroy() {
        //Removed default super
        Log.i(TAG,"L42 onDestroy called");
    }



}

