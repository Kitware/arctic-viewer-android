package com.kitware.arcticviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by tim on 1/15/16.
 */
public class ViewDatasetActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dataset);
    }

    public void onSelectDataset(View view) {
        Intent intent = new Intent(this, SelectDatasetActivity.class);
        startActivity(intent);
    }
}
