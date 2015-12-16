package com.kitware.arcticviewer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
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
public class DownloadCell extends LinearLayout {
    ImageView image;
    TextView datasetName;
    TextView datasetSize;

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView image;

        public DownloadImageTask(ImageView image) {
            this.image = image;
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap result = null;
            try {
                InputStream in = (new URL(urls[0])).openStream();
                result = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.d("Arctic Viewer", e.getMessage());
            }
            return result;
        }

        protected void onPostExecute(Bitmap result) {
            float dp = Resources.getSystem().getDisplayMetrics().density;
            float width = result.getWidth();
            float height = result.getHeight();
            int targetWidth = (int)(128 * dp);
            int targetHeight = (int)(128 * dp);
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
            canvas.drawBitmap(result, transformation, paint);
            image.setImageBitmap(rescaled);
        }
    }

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
        new DownloadImageTask(image).execute(url);
    }
}
