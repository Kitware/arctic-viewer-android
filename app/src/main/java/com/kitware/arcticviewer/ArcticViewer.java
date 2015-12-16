package com.kitware.arcticviewer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class ArcticViewer extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dataset);

        // Fetch all datasets
        LinearLayout datasetList = (LinearLayout)findViewById(R.id.dataset_list);

        {
            DownloadCell cell1 = new DownloadCell(this);
            cell1.setDatasetName("Diskout Ref - Composite");
            cell1.setDatasetSize("40 MB");
            cell1.setImageURL("http://tonic.kitware.com/arctic-viewer/diskout-composite.png");
            datasetList.addView(cell1);

            DownloadCell cell2 = new DownloadCell(this);
            cell2.setDatasetName("Ensemble - Demo");
            cell2.setDatasetSize("94.2 MB");
            cell2.setImageURL("http://tonic.kitware.com/arctic-viewer/ensemble.jpg");
            datasetList.addView(cell2);

            DownloadCell cell3 = new DownloadCell(this);
            cell3.setDatasetName("Garfield Comic");
            cell3.setDatasetSize("292 KB");
            cell3.setImageURL("http://tonic.kitware.com/arctic-viewer/garfield.jpg");
            datasetList.addView(cell3);
        }

        {
            DownloadCell cell1 = new DownloadCell(this);
            cell1.setDatasetName("Diskout Ref - Composite");
            cell1.setDatasetSize("40 MB");
            cell1.setImageURL("http://tonic.kitware.com/arctic-viewer/diskout-composite.png");
            datasetList.addView(cell1);

            DownloadCell cell2 = new DownloadCell(this);
            cell2.setDatasetName("Ensemble - Demo");
            cell2.setDatasetSize("94.2 MB");
            cell2.setImageURL("http://tonic.kitware.com/arctic-viewer/ensemble.jpg");
            datasetList.addView(cell2);

            DownloadCell cell3 = new DownloadCell(this);
            cell3.setDatasetName("Garfield Comic");
            cell3.setDatasetSize("292 KB");
            cell3.setImageURL("http://tonic.kitware.com/arctic-viewer/garfield.jpg");
            datasetList.addView(cell3);
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
