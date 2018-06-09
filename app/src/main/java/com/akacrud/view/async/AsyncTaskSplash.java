package com.akacrud.view.async;

import android.app.Activity;
import android.os.AsyncTask;

import com.akacrud.view.activities.SplashActivity;

/**
 * Created by luisvespa on 12/13/17.
 * Background Async Task to Splash Activity
 */

public class AsyncTaskSplash extends AsyncTask<String, Void, String> {

    private static final String TAG = AsyncTaskSplash.class.getSimpleName();

    SplashActivity activity;
    static int progress;
    int progressStatus = 0;

    public AsyncTaskSplash(Activity activity) {
        this.activity = (SplashActivity) activity;
    }

    // Simulate a long-term task
    private int doSomeWork() {
        try {
            // Simulate some work
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ++progress;
    }

    /**
     * Working in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {
        while (progressStatus < 100) {
            progressStatus = doSomeWork();
        }
        return null;
    }

    /**
     * After completing background task
     * Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {

        activity.finishLoader();
    }

}
