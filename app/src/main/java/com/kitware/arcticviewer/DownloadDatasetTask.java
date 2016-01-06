package com.kitware.arcticviewer;

import android.app.ExpandableListActivity;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by tim on 12/21/15.
 */
public class DownloadDatasetTask extends AsyncTask<Void, Integer, Long> {
    ProgressBar progressBar;
    JSONObject dataset;

    public DownloadDatasetTask() {}

    public void setJSON(JSONObject json) { dataset = json; }

    public void setProgress(ProgressBar progress) {
        progressBar = progress;
    }

    protected Long doInBackground(Void... args) {
        InputStream input = null;
        FileOutputStream output = null;
        HttpURLConnection connection = null;
        long total = 0;

        try {
            String imageUrl = dataset.getString("url");
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return (long)0;
            }

            int fileLength = connection.getContentLength();

            String filename = imageUrl.substring(imageUrl.lastIndexOf('/'));
            String fullpath = Environment.getExternalStorageDirectory() + filename;

            dataset.put("path", fullpath);

            input = connection.getInputStream();
            output = new FileOutputStream(new File(fullpath));

            byte data[] = new byte[4096];
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
            Log.d("Arctic Viewer", e.getMessage());
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
        return total;
    }

    @Override
    protected void onProgressUpdate(Integer... percentage) {
        progressBar.setProgress(percentage[0]);
    }

    protected void onPostExecute(Long bytesReceived) {
        progressBar.setProgress(0);

        // Add downloaded dataset to json
        try {
            String jsonPath = Environment.getExternalStorageDirectory() + "/datasets.json";

            // Parse dataset JSON, if it exists
            JSONArray array = null;
            try {
                InputStream input = new FileInputStream(jsonPath);
                int size = input.available();
                byte[] buffer = new byte[size];
                input.read(buffer);
                input.close();
                String jsonString = new String(buffer, "UTF-8");
                array = new JSONArray(jsonString);
            } catch (Exception e) {
            }

            // Add the new dataset to the array
            if (array == null) {
                array = new JSONArray();
            }
            array.put(dataset);

            // Write the JSON back to disk
            FileWriter writer = new FileWriter(jsonPath);
            writer.write(array.toString());
            writer.close();
        } catch (Exception e) {
            Log.d("Arctic Viewer", e.getMessage());
        }
    }
}