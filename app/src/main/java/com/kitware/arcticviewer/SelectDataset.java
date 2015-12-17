package com.kitware.arcticviewer;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class SelectDataset extends ActionBarActivity {

    public static Bitmap Resize(Bitmap input, int targetWidth, int targetHeight) {
        float dp = Resources.getSystem().getDisplayMetrics().density;
        float width = input.getWidth();
        float height = input.getHeight();
        targetWidth *= dp;
        targetHeight *= dp;
        Bitmap rescaled = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);
        float scale = targetWidth / width;
        float dx = 0.0f;
        float dy = (targetHeight - height * scale) * 0.5f;
        Matrix transformation = new Matrix();
        transformation.postTranslate(dx, dy);
        transformation.preScale(scale, scale);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        Canvas canvas = new Canvas(rescaled);
        canvas.drawBitmap(input, transformation, paint);
        return rescaled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dataset);

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
        LinearLayout datasetList = (LinearLayout)findViewById(R.id.dataset_list);
        try {
            JSONArray json = new JSONArray(jsonString);
            for (int i = 0; i < json.length(); ++i) {
                JSONObject obj = json.getJSONObject(i);

                DownloadCell cell = new DownloadCell(this);
                cell.setDatasetName(obj.getString("title"));
                cell.setDatasetSize(obj.getString(("filesize")));
                cell.setImageURL(obj.getString(("thumbnail")));
                datasetList.addView(cell);
            }
        } catch (Exception e) {
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
}
