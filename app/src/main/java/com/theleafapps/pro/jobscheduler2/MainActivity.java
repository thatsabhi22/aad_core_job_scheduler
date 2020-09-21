package com.theleafapps.pro.jobscheduler2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button sch_job,cncl_job;
    public static final String TAG = "MainActivity";
    public static final int job_id = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sch_job = findViewById(R.id.schedule_job_btn);
        cncl_job = findViewById(R.id.cancel_job_btn);

        sch_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentName componentName = new ComponentName(MainActivity.this,ExampleJobService.class);
                JobInfo info = new JobInfo.Builder(job_id,componentName)
                        .setRequiresCharging(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true)
                        .build();

                JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                int resultCode = jobScheduler.schedule(info);
                if(resultCode == JobScheduler.RESULT_SUCCESS){
                    Log.d(TAG, "Job Scheduled...");
                }
                else{
                    Log.d(TAG, "onClick: Job Scheduling Failed");
                }
            }
        });

        cncl_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                scheduler.cancel(job_id);
                Log.d(TAG, "Job Cancelled...");
            }
        });
    }

}