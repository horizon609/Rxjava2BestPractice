package com.example.echoplex_x.rxjava2bestpractice.upload;

import com.example.echoplex_x.rxjava2bestpractice.BuildConfig;

/**
 * Created by wangting31 on 2017/10/12.
 */

public class UploadConfig {
    private static String BASE_URL = BuildConfig.BASE_URL;

    // 获取上传文件地址：域名＋api
    public static String getUploadUrl() {
        String baseUrl = BASE_URL;

        // 处理线上域名和线下域名有区别
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }

        return baseUrl + "api/venus/v1/imgToken";
    }
}
