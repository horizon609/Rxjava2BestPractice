package com.example.echoplex_x.rxjava2bestpractice.utils;

import android.app.Application;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import com.example.echoplex_x.rxjava2bestpractice.BaseApplication;

/**
 * Created by wangting31 on 2017/10/12.
 */

public class UriUtils {
    public static String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = BaseApplication.getInstance().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if(cursor != null) cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
