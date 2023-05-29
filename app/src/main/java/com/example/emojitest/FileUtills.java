package com.example.emojitest;

import android.app.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileUtills {

    public static List<String> getAssetItems(Activity activity, String str) {
        ArrayList arrayList = new ArrayList();
        try {
            arrayList.addAll(Arrays.asList(activity.getAssets().list(str)));
            for (int i = 0; i < arrayList.size(); i++) {
                String str2 = (String) arrayList.get(i);
                String filepath = "file:///android_asset/" + str + "/" + str2;
                arrayList.set(i, filepath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

}


