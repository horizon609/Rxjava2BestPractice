package com.example.echoplex_x.rxjava2bestpractice;

import android.net.Uri;


import com.example.echoplex_x.rxjava2bestpractice.bean.UploadResponseBean;
import com.example.echoplex_x.rxjava2bestpractice.bean.UploadResponseImgBean;
import com.example.echoplex_x.rxjava2bestpractice.bean.VenusInfo;
import com.example.echoplex_x.rxjava2bestpractice.upload.ImageUploader;
import com.example.echoplex_x.rxjava2bestpractice.upload.VenusService;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.example.echoplex_x.rxjava2bestpractice.upload.UploadConfig.getUploadUrl;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

/**
 * Created by wangting31 on 2017/10/12.
 */
public class ImageUploaderTest {
    @Test
    public void uploadAllSucTest() {
        List<Uri> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Uri uri = Uri.parse("myapp://home/" + i);
            list.add(uri);
        }

        VenusService venusService = Mockito.mock(VenusService.class);
        VenusInfo ve = new VenusInfo();
        VenusInfo.DataBean dataBean = new VenusInfo.DataBean();
        dataBean.setUrl("zz");
        dataBean.setToken("zz");
        dataBean.setExpireTime(1);
        ve.setData(dataBean);
        ve.code = 2;
//
        Mockito.when(venusService.getVenusInfo(getUploadUrl())).thenReturn(Observable.just(ve));
        UploadResponseBean uploadResponse1 = new UploadResponseBean();
        uploadResponse1.success = true;
        UploadResponseImgBean data1 = new UploadResponseImgBean();
        data1.index = 1;
        data1.setOriginalFileName("图片1");
        uploadResponse1.data = data1;


        UploadResponseBean uploadResponse2 = new UploadResponseBean();
        uploadResponse2.success = true;
        UploadResponseImgBean data2 = new UploadResponseImgBean();
        data2.index = 2;
        data2.setOriginalFileName("图片2");
        uploadResponse2.data = data2;

        UploadResponseBean uploadResponse3 = new UploadResponseBean();
        uploadResponse3.success = true;
        UploadResponseImgBean data3 = new UploadResponseImgBean();
        data3.index = 3;
        data3.setOriginalFileName("图片3");
        uploadResponse3.data = data3;

        Mockito.when(venusService.uploadImg(anyString(), anyString(), anyString(), any(RequestBody.class), any(MultipartBody.Part.class)))
                .thenReturn(Observable.just((uploadResponse1)))
                .thenReturn(Observable.just(uploadResponse2))
                .thenReturn(Observable.just(uploadResponse3));

        ImageUploader.INSTANCE.setVenusService(venusService);
        TestObserver<UploadResponseBean> observer = new TestObserver<>();
        ImageUploader.INSTANCE.upload(list).subscribe(observer);

        Mockito.verify(venusService, Mockito.times(1)).getVenusInfo(anyString());
        Mockito.verify(venusService, Mockito.times(3)).uploadImg(eq("zz"), eq("zz"), eq("1"), any(RequestBody.class), any(MultipartBody.Part.class));
//       verify(venusService, times(3)).uploadImg(anyString(), anyString(), anyString(), any(RequestBody.class), any(MultipartBody.Part.class));

        observer.assertNoErrors();
        observer.assertResult(uploadResponse1, uploadResponse2, uploadResponse3);
        observer.assertComplete();
    }

    @Test
    public void uploadHasErrorTest() {
        List<Uri> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Uri uri = Uri.parse("myapp://home/" + i);
            list.add(uri);
        }

        VenusService venusService = Mockito.mock(VenusService.class);
        VenusInfo ve = new VenusInfo();
        VenusInfo.DataBean dataBean = new VenusInfo.DataBean();
        dataBean.setUrl("zz");
        dataBean.setToken("zz");
        dataBean.setExpireTime(1);
        ve.setData(dataBean);
        ve.code = 2;
//
        Mockito.when(venusService.getVenusInfo(getUploadUrl())).thenReturn(Observable.just(ve));
        UploadResponseBean uploadResponse1 = new UploadResponseBean();
        uploadResponse1.success = true;
        UploadResponseImgBean data1 = new UploadResponseImgBean();
        data1.index = 1;
        data1.setOriginalFileName("图片1");
        uploadResponse1.data = data1;

        UploadResponseBean uploadResponse3 = new UploadResponseBean();
        uploadResponse3.success = true;
        UploadResponseImgBean data3 = new UploadResponseImgBean();
        data3.index = 3;
        data3.setOriginalFileName("图片3");
        uploadResponse3.data = data3;

        Throwable exception = new RuntimeException("Boom!");

        Mockito.when(venusService.uploadImg(anyString(), anyString(), anyString(), any(RequestBody.class), any(MultipartBody.Part.class)))
                .thenReturn(Observable.just((uploadResponse1)))
                .thenReturn(Observable.error(exception))
                .thenReturn(Observable.just(uploadResponse3));

        ImageUploader.INSTANCE.setVenusService(venusService);
        TestObserver<UploadResponseBean> observer = new TestObserver<>();
        ImageUploader.INSTANCE.upload(list).subscribe(observer);

        Mockito.verify(venusService, Mockito.times(1)).getVenusInfo(anyString());
        Mockito.verify(venusService, Mockito.times(2)).uploadImg(eq("zz"), eq("zz"), eq("1"), any(RequestBody.class), any(MultipartBody.Part.class));
//       verify(venusService, times(3)).uploadImg(anyString(), anyString(), anyString(), any(RequestBody.class), any(MultipartBody.Part.class));

        observer.assertNotComplete();
        observer.assertError(exception);
        observer.assertValue(uploadResponse1);

    }
}