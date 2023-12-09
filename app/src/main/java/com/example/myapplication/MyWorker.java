package com.example.myapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        String value = workerParams.getInputData().getString("key3");
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Data data = new Data.Builder()
                .putString("key1","hello")
                .putInt("key2",100).build();
        return Result.success(data);
    }
}
