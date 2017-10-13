package com.example.echoplex_x.rxjava2bestpractice.upload;

import com.example.echoplex_x.rxjava2bestpractice.bean.UploadResponseBean;
import com.example.echoplex_x.rxjava2bestpractice.bean.VenusInfo;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by wangting31 on 2017/10/12.
 */

public interface VenusService {
    /**
     * 获取venus的上传地址和token
     */
    @GET
    Observable<VenusInfo> getVenusInfo(@Url String url);

    /**
     * 上传图片
     */
    @Multipart
    @POST
    Observable<UploadResponseBean> uploadImg(@Url String url, @Header("Authorization") String token, @Header("time") String expireTime, @Part("description") RequestBody description, @Part MultipartBody.Part body);
}
