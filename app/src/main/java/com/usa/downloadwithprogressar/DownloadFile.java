package com.usa.downloadwithprogressar;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFile extends AsyncTask<String, Integer, String> {
    // Declare variables
    ProgressDialog mProgressDialog;

    // Insert image URL
    String URL = "https://api.androidhive.info/progressdialog/hive.jpg";

    Context context;

    public DownloadFile(Context context) {
        this.context = context;
        execute(URL);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create progress dialog
        mProgressDialog = new ProgressDialog(context);
        // Set your progress dialog Title
        mProgressDialog.setTitle("Progress Bar Tutorial");
        // Set your progress dialog Message
        mProgressDialog.setMessage("Downloading, Please Wait!");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Show progress dialog
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... Url) {
        try {
            java.net.URL url = new URL(Url[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            // Detect the file lenghth
            int fileLength = connection.getContentLength();

            // Locate storage location
            String filepath = Environment.getExternalStorageDirectory()
                    .getPath();

            // Download the file
            InputStream input = new BufferedInputStream(url.openStream());

            // Save the downloaded file
            OutputStream output = new FileOutputStream(filepath + "/"
                    + "testimage.jpg");

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // Publish the progress
                publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }

            // Close connection
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            // Error Log
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // Update the progress dialog
        mProgressDialog.setProgress(progress[0]);
        // Dismiss the progress dialog
        //mProgressDialog.dismiss();
    }
}

