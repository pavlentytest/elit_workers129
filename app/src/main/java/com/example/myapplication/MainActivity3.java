package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private OneTimeWorkRequest workRequest, workRequest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Data send_data = new Data.Builder().putString("key3","Hello from activity!").build();

        workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInputData(send_data)
                .build();
        workRequest2 = new OneTimeWorkRequest.Builder(MyWorker.class).build();

        List<OneTimeWorkRequest> list = new ArrayList<>();
        list.add(workRequest);
        list.add(workRequest2);
        // параллельно
        WorkManager.getInstance(this).enqueue(list);
        // последовательно
        WorkManager.getInstance(this).beginWith(list).enqueue();
        WorkManager.getInstance(this).beginWith(workRequest).then(workRequest2).enqueue();

        WorkManager.getInstance(this).enqueue(workRequest);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(
                this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.d("RRR","State = "+workInfo.getState());
                        String result = workInfo.getOutputData().getString("key1");
                        int result2 = workInfo.getOutputData().getInt("key2",0);
                        Log.d("RRR","key1="+ result + ", key2="+result2);
                    }
                }
        );

    }
}