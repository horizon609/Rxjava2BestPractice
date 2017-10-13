package com.example.echoplex_x.rxjava2bestpractice.upload;

import android.os.Build;
import android.text.TextUtils;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangting31 on 2017/10/12.
 */

public class VenusHeaderInterceptor implements Interceptor {
    private static final String USER_AGENT_HEADER_NAME = "X-User-Agent";
    private static final String COOKIE = "Cookie";
    private static final String POI_ID = "poiId";
    private static final String DATE = "date";
    private String poiId;

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl newUrl = chain.request().url();
        HttpUrl.Builder builder = newUrl.newBuilder();
        builder.removeAllQueryParameters(POI_ID);
        if (poiId == null) {
            poiId = "";
        }
        builder.addQueryParameter(POI_ID, poiId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        builder.addQueryParameter(DATE, dateFormat.format(new Date()));
        Request request = chain.request().newBuilder()
                .url(builder.build())
                .removeHeader(USER_AGENT_HEADER_NAME)
                .addHeader(USER_AGENT_HEADER_NAME, userAgent())
                .addHeader("os_type", "ANDROID")
                .removeHeader(COOKIE)
                .addHeader(COOKIE, cookie())
                .build();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (SecurityException e) {
            throw new IOException(e.getMessage());
        }
        return response;
    }

    private String cookie() {
        return "xx";
    }

    private String userAgent() {
        ArrayList<String> userAgents = new ArrayList<>();
        userAgents.add("sdk" + "=" + Build.VERSION.SDK_INT);
        userAgents.add("model" + "=" + Build.MODEL);
        return TextUtils.join("&", userAgents);
    }
}



