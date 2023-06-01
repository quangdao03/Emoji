package com.example.emojitest.util;

import android.os.Environment;

import java.io.File;

public class SavingUtils {
    public static File getAppDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }
}
