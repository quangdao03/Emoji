package com.example.emojitest;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ActivityManager {
    private static ActivityManager instance;
    private HashMap<String, WeakReference<Activity>> activityMap;

    private ActivityManager() {
        activityMap = new HashMap<>();
    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activityMap.put(activity.getClass().getSimpleName(), new WeakReference<>(activity));
    }

    public void removeActivity(Activity activity) {
        activityMap.remove(activity.getClass().getSimpleName());
    }

    public Activity getActivity(Class<?> activityClass) {
        WeakReference<Activity> activityRef = activityMap.get(activityClass.getSimpleName());
        if (activityRef != null) {
            return activityRef.get();
        }
        return null;
    }
}

