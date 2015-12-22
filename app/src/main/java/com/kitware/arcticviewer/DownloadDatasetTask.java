package com.kitware.arcticviewer;

import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tim on 12/21/15.
 */
public class DownloadDatasetTask extends AsyncTask<String, Integer, String> {
    ProgressBar progress;

    public DownloadDatasetTask() {}

    public void setProgress(ProgressBar progress) {
        this.progress = progress;
    }

    protected String doInBackground(String... urls) {
        InputStream input = null;
        FileOutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " +
                        connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            int fileLength = connection.getContentLength();

            String filename = urls[0].substring(urls[0].lastIndexOf('/'));
            String fullpath = Environment.getExternalStorageDirectory() + filename;

            input = connection.getInputStream();
            output = new FileOutputStream(new File(fullpath));

            byte data[] = new byte[4096];
            long total = 0;
            int count = 0;
            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                if (fileLength > 0) {
                    int percentage = (int)(total * 100.0f / fileLength);
                    publishProgress(percentage);
                }
                output.write(data, 0, count);
            }
            publishProgress(100);
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... percentage) {
        progress.setProgress(percentage[0]);
    }

    protected void onPostExecute(Long bytesReceived) {
        progress.setProgress(0);
    }
}