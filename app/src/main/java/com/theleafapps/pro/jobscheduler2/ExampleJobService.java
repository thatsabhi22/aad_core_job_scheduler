package com.theleafapps.pro.jobscheduler2;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class ExampleJobService extends JobService {

    public static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: Job Started");
        doBackgroundWork(jobParameters);

        return true;
    }

    private void doBackgroundWork(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i<10 ; i++ ){
                    Log.d(TAG, "run: " + i);
                    if(jobCancelled){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Job Finished ");
                jobFinished(jobParameters,false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob: Job Cancelled before completion");

        jobCancelled = true;


        //In case of using AsyncTask, we will use asynctask.cancel


        // When the job is cancelled in between it was running it can be rescheduled for later time
        // If we need to reschedule the job later return true
        // If the job is not so important and doesn't need to be rescheduled,
        // we can return false
        return true;
    }

}
