package com.aplication.myuniversity.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static String getFilename(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static Uri copyFileToPicturesFolder(@NotNull Context context, Uri inputUri) {
        final String extDirPictures =
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        final String strInputPath = UriUtils.getPathFromUri(context, inputUri);
        if (strInputPath != null) {
            File inputFile = new File(strInputPath); // получаем входящий файл
            final String inputUriFilename = getFilename(strInputPath); // получаем имя файла
            // формируем путь до конечного файла
            final String outputPath = extDirPictures + inputUriFilename;
            File outputFile = new File(outputPath);
            try {
                FileUtils.copyFile(inputFile, outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Uri.fromFile(outputFile); // возвращаем путь Uri до скопированного файла
        }
        return null;
    }
}
