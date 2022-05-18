package com.aplication.myuniversity.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.jetbrains.annotations.NotNull;

public class Preferences {
    protected final Context context;
    protected final SharedPreferences sharedPreferences;

    public Preferences(@NotNull Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getActiveStudentId() {
        return sharedPreferences.getInt(Prefs.ACTIVE_STUDENT, 0);
    }

    public void updateActiveStudentId(int value) {
        sharedPreferences.edit().putInt(Prefs.ACTIVE_STUDENT, value).apply();
    }

    public boolean showTip() {
        return sharedPreferences.getBoolean(Prefs.SHOW_TIP, true);
    }

    public void updateShowTip(boolean value) {
        sharedPreferences.edit().putBoolean(Prefs.SHOW_TIP, value).apply();
    }
}
