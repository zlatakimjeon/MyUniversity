package com.aplication.myuniversity.utils;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class PermissionUtils {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_RECORD_AUDIO = 2;
    private static final int REQUEST_CALENDAR = 3;

    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String[] PERMISSIONS_RECORD = {
            Manifest.permission.RECORD_AUDIO
    };
    private static final String[] PERMISSIONS_CALENDAR = {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
    };


    public static boolean getStoragePermissions(@NonNull Activity activity) {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // if api >= 30
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestStoragePermissions(@NonNull Activity activity) {
        if (!getStoragePermissions(activity))
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
    }

    public static boolean recordPermissionsGranted(@NonNull Activity activity) {
        return getRecordPermissions(activity) == PackageManager.PERMISSION_GRANTED;
    }

    public static int getRecordPermissions(@NonNull Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
    }

    public static void requestRecordPermissions(@NonNull Activity activity) {
        if (!recordPermissionsGranted(activity))
            ActivityCompat.requestPermissions(activity, PERMISSIONS_RECORD, REQUEST_RECORD_AUDIO);
    }

    public static boolean calendarPermissionsGranted(@NonNull Activity activity) {
        return getCalendarPermissions(activity) == PackageManager.PERMISSION_GRANTED;
    }

    public static int getCalendarPermissions(@NonNull Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CALENDAR);
    }

    public static void requestCalendarPermissions(@NonNull Activity activity) {
        if (!calendarPermissionsGranted(activity))
            ActivityCompat.requestPermissions(activity, PERMISSIONS_CALENDAR, REQUEST_CALENDAR);
    }

    /*@SuppressLint("BatteryLife")
    public static void requestBatteryOptimization(@NonNull Activity activity) {
        PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        String packageName = activity.getPackageName();
        if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_BATTERY_SAVER_SETTINGS);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("package:" + packageName));
            activity.startActivity(intent);
        }
    }

    public static boolean getIgnoreBatteryOptimization(@NonNull Activity activity) {
        String packageName = activity.getPackageName();
        PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        return powerManager.isIgnoringBatteryOptimizations(packageName);
    }*/
}

