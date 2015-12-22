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

import java.io.InputStream;
import java.net.URL;

/**
 * Created by tim on 12/16/15.
 */
public class DatasetCell extends LinearLayout {
    ImageView image;
    TextView datasetName;
    TextView datasetSize;

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
        View.inflate(context, R.layout.download_cell, this);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        image = (ImageView)findViewById(R.id.download_cell_image);
        datasetName = (TextView)findViewById(R.id.download_cell_dataset_name);
        datasetSize = (TextView)findViewById(R.id.download_cell_dataset_size);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Rect hitRect = new Rect();
                v.getHitRect(hitRect);
                if (hitRect.contains((int) event.getX(), (int) event.getY())) {
                    event.setLocation(0.0f, 0.0f);
                    // TODO: handle touch event
                    return true;
                }
                return false;
            }
        });
    }

    public void setDatasetName(String name) {
        datasetName.setText(name);
    }

    public void setDatasetSize(String size) {
        datasetSize.setText("Size: " + size);
    }

    public void setImageURL(String url) {
        new DownloadImageTask(image, 64, 64).execute(url);
    }
}
