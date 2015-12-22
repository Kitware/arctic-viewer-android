package com.kitware.arcticviewer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by tim on 12/21/15.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView image;
    int targetWidth;
    int targetHeight;

    private static Bitmap Resize(Bitmap input, int targetWidth, int targetHeight) {
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

    public DownloadImageTask(ImageView image, int targetWidth, int targetHeight) {
        this.image = image;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
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
        if (result == null) {
            return;
        }
        image.setImageBitmap(Resize(result, targetWidth, targetHeight));
    }
}