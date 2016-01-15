package com.kitware.arcticviewer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStream;

public class SelectDatasetActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dataset);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        // Check for existing datasets
        String jsonPath = Environment.getExternalStorageDirectory() + "/datasets.json";
        try {
            // Parse dataset JSON
            String jsonString = null;
            InputStream input = new FileInputStream(jsonPath);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            jsonString = new String(buffer, "UTF-8");

            LinearLayout datasets = (LinearLayout)findViewById(R.id.dataset_list);
            JSONArray json = new JSONArray(jsonString);
            for (int i = 0; i < json.length(); ++i) {
                JSONObject jsonObject = json.getJSONObject(i);

                DatasetCell cell = new DatasetCell(this);
                cell.setActivity(this);
                cell.setJSON(jsonObject);

                datasets.addView(cell);
            }
        } catch (Exception e) {
            Log.d("Arctic Viewer", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arctic_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddDataset(View view) {
        Intent intent = new Intent(this, DownloadDatasetActivity.class);
        startActivity(intent);
    }

    public void selectDataset(DatasetCell cell) {
        Intent intent = new Intent(this, ViewDatasetActivity.class);
        startActivity(intent);
    }
}
