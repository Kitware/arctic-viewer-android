package com.kitware.arcticviewer;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by tim on 1/18/16.
 */
public class Paths {
    private static void EnsureFolderExists(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    private static void EnsureFileExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Log.d("Arctic Viewer", e.getMessage());
            }
        }
    }

    public static String AppRoot() {
        String path = Environment.getExternalStorageDirectory() + "/arctic_viewer/";
        EnsureFolderExists(path);
        return path;
    }

    public static String WebContentDirectory() {
        String path = AppRoot() + "web_content/";
        EnsureFolderExists(path);
        return path;
    }

    public static String DatasetDirectory() {
        String path = AppRoot() + "datasets/";
        EnsureFolderExists(path);
        return path;
    }

    public static String DatasetJson() {
        String path = AppRoot() + "datasets.json";
        EnsureFileExists(path);
        return path;
    }
}
