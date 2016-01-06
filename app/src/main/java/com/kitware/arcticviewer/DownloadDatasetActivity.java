package com.kitware.arcticviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tim on 12/17/15.
 */
public class DownloadDatasetActivity extends ActionBarActivity {
    DownloadCell selectedCell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_dataset);

        // Parse dataset JSON
        String jsonString = null;
        try {
            InputStream input = getAssets().open("sample-data.json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
        }

        // Fetch all datasets
        TableLayout downloadables = (TableLayout) findViewById(R.id.download_table);
        try {
            JSONArray json = new JSONArray(jsonString);
            for (int i = 0; i < json.length(); i += 2) {
                TableRow row = new TableRow(this);

                JSONObject first = json.getJSONObject(i);
                DownloadCell firstCell = new DownloadCell(this);
                firstCell.setActivity(this);
                firstCell.setJSON(first);
                row.addView(firstCell);

                if (i < json.length() - 1) {
                    JSONObject second = json.getJSONObject(i + 1);
                    DownloadCell secondCell = new DownloadCell(this);
                    secondCell.setActivity(this);
                    secondCell.setJSON(second);
                    row.addView(secondCell);
                }
                downloadables.addView(row);
            }
        } catch (Exception e) {
            Log.d("Arctic Viewer", e.getMessage());
        }
    }

    public void setSelectedCell(DownloadCell cell) {
        selectedCell = cell;

        try {
            TextView urlView = (TextView) findViewById(R.id.url);
            urlView.setText(cell.getJSON().getString("url"));
        } catch (Exception e) {
        }
    }

    public void onDownload(View view) {
        ProgressBar progress = (ProgressBar)findViewById(R.id.download_progress);

        DownloadDatasetTask task = new DownloadDatasetTask();
        task.setProgress(progress);
        task.setJSON(selectedCell.getJSON());
        task.execute();
    }

    public void onDone(View view) {
        Intent intent = new Intent(this, SelectDatasetActivity.class);
        startActivity(intent);
    }
}
