package com.aplication.myuniversity.utils;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class UrlUtils {
    public static final Pattern URL_PATTERN = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    public static void openSite(@NotNull Context context, String text) {
        Uri uri = Uri.parse(text);
        try {
            Intent searchIntent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(searchIntent);
        } catch (Exception ignored) {
            Intent searchIntent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(searchIntent);
        }
    }

    public static void searchInGoogle(@NotNull Context context, String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, text);
            context.startActivity(intent);
        } catch (Exception ignored) {
            Uri uri = Uri.parse("https://www.google.com/search?q=" + text);
            Intent searchIntent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(searchIntent);
        }
    }

    public static void searchInGoogleMaps(@NotNull Context context, String text) {
        try {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + text);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        } catch (Exception ignored) {
        }
    }
}
