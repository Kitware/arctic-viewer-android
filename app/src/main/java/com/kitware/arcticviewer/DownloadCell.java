package com.kitware.arcticviewer;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by tim on 12/16/15.
 */
public class DownloadCell extends LinearLayout {
    DownloadDatasetActivity activity;
    ImageView image;
    String datasetName;
    String datasetSize;
    String datasetUrl;

    public DownloadCell(Context context) {
        super(context);
        initialize(context);
    }

    public DownloadCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public DownloadCell(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        View.inflate(context, R.layout.download_cell, this);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        image = (ImageView)findViewById(R.id.download_cell_image);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DownloadCell c = (DownloadCell)v;
                if (c != null) {
                    activity.setSelectedUrl(c.datasetUrl);
                    return true;
                }
                return false;
            }
        });
    }

    public void setActivity(DownloadDatasetActivity downloadActivity) {
        activity = downloadActivity;
    }

    public void setImageURL(String url) {
        new DownloadImageTask(image, 160, 160).execute(url);
    }

    public void setDatasetURL(String url) {
        datasetUrl = url;
    }
}
