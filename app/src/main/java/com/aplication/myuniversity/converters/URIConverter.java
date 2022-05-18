package com.aplication.myuniversity.converters;

import android.net.Uri;

import androidx.room.TypeConverter;

public class URIConverter {
    @TypeConverter
    public static String fromUri(Uri uri) {
        String strUri = "";
        if (uri != null)
            strUri = uri.toString();
        return strUri;
    }

    @TypeConverter
    public static Uri uriFromString(String string) {
        return Uri.parse(string);
    }
}

