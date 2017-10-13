package com.example.echoplex_x.rxjava2bestpractice.upload;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.echoplex_x.rxjava2bestpractice.bean.UploadResponseBean;
import com.example.echoplex_x.rxjava2bestpractice.bean.VenusInfo;
import com.example.echoplex_x.rxjava2bestpractice.utils.UriUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangting31 on 2017/10/12.
 */

public enum ImageUploader {
    INSTANCE;
    private final OkHttpClient mClient;
    private final int PIC_NUM = 5;

    private static final String TAG = "ImageUploader";
    private static final String BASE_URL = "http://pic.meituan.com";
    private static final String DEFAULT_PIC_NAME = "imagepick.jpg";

    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");
    private VenusService venusService;
    private ExecutorService es = Executors.newFixedThreadPool(PIC_NUM, new ThreadFactoryBuilder().setNameFormat("Htttt-%d").build());

    ImageUploader() {
        mClient = new OkHttpClient.Builder()
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(new VenusHeaderInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        venusService = retrofit.create(VenusService.class);

    }

    public void setVenusService(VenusService venusService) {
        this.venusService = venusService;
    }

    /**
     * 上传单个文件
     */
    public void upload(Uri fileUri) {
        List<Uri> uris = new ArrayList<>();
        if (fileUri != null) {
            uris.add(fileUri);
            upload(uris);
        }
    }

    /**
     * 伪批量上传，一次上传多个文件,不同于批量上传，venus不支持，所以采用并发上传文件。
     *
     * @param fileUris
     */
    public Observable<UploadResponseBean> upload(List<Uri> fileUris) {
        //1.获取图片服务器的URL和token
        final VenusInfo.DataBean[] dataBean = {null};
        return venusService.getVenusInfo(UploadConfig.getUploadUrl())
                .concatMapIterable(venusInfo -> {
                    //保存VenusInfo
                    Log.d(TAG, "[venusInfo.code=" + venusInfo.code + ", venusInfo.data=" + venusInfo.getData());
                    dataBean[0] = venusInfo.getData();
                    return fileUris;
                })
                .concatMap(uri -> {
                    RequestBody fileBody = RequestBody.create(MEDIA_OBJECT_STREAM, new File(uri.getPath()));
                    String fileName = UriUtils.getFileName(uri);
                    if (TextUtils.isEmpty(fileName)) {
                        fileName = DEFAULT_PIC_NAME;
                    }
                    MultipartBody.Part body = MultipartBody.Part.createFormData("image", fileName, fileBody);
                    RequestBody description = RequestBody.create(MultipartBody.FORM, "description");
                    //2.开始上传图片
                    return venusService.uploadImg(dataBean[0].getUrl(), dataBean[0].getToken(), String.valueOf(dataBean[0].getExpireTime()), description, body);
                });
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//                .subscribe(uploadResponse -> {
//                    if (uploadResponse.success) {
//                        Log.d(TAG, "upload response suc");
//                        if (uploadResponse != null && uploadResponse.data != null) {
//                            String picName = uploadResponse.data.getOriginalFileName() == null ? "图片" : uploadResponse.data.getOriginalFileName();
//                            Log.d(TAG, picName + "上传成功");
//                        }
//                    }
//                }, throwable -> {
//                    Log.e(TAG, throwable.getMessage());
//                });
    }
}
