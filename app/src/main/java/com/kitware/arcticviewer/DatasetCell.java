package com.kitware.arcticviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by tim on 12/16/15.
 */
public class DatasetCell extends LinearLayout {
    SelectDatasetActivity activity;
    ImageView image;
    TextView datasetName;
    TextView datasetSize;

    JSONObject dataset;

    public DatasetCell(Context context) {
        super(context);
        initialize(context);
    }

    public DatasetCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public DatasetCell(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        View.inflate(context, R.layout.dataset_cell, this);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        image = (ImageView) findViewById(R.id.dataset_cell_image);
        datasetName = (TextView) findViewById(R.id.dataset_cell_dataset_name);
        datasetSize = (TextView) findViewById(R.id.dataset_cell_dataset_size);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DatasetCell c = (DatasetCell) v;
                if (c != null) {
                    activity.selectDataset(c);
                    return true;
                }
                return false;
            }
        });
    }

    public void setActivity(SelectDatasetActivity selectActivity) {
        activity = selectActivity;
    }

    public void setJSON(JSONObject json) {
        dataset = json;

        try {
            datasetSize.setText("Size: " + dataset.getString("filesize"));
            datasetName.setText(dataset.getString("title"));
            new DownloadImageTask(image, 64, 64).execute(dataset.getString("thumbnail"));
        } catch (JSONException e) {
        }
    }
    public JSONObject getJSON() { return dataset; }
}
