package com.example.echoplex_x.rxjava2bestpractice.upload;

import android.net.Uri;

import com.example.echoplex_x.rxjava2bestpractice.bean.ApiResponse;
import com.example.echoplex_x.rxjava2bestpractice.bean.FeedbackBean;
import com.example.echoplex_x.rxjava2bestpractice.bean.UploadResponseImgBean;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by wangting31 on 2017/10/16.
 */
public class FeedbackPresenterTest {
    @Test
    public void commitFeedbackInfo() throws Exception {
        VenusService venusService = mock(VenusService.class);
        FeedbackView feedbackView = mock(FeedbackView.class);
        ImageUploader imageUploader = mock(ImageUploader.class);

        UploadResponseImgBean data1 = new UploadResponseImgBean();
        data1.index = 1;
        data1.setOriginalFileName("图片1");

        UploadResponseImgBean data3 = new UploadResponseImgBean();
        data3.index = 3;
        data3.setOriginalFileName("图片3");

//        when(ImageUploader.INSTANCE.upload(anyList())).thenReturn(Observable.just(data1, data3));
        when(imageUploader.upload(anyList())).thenReturn(Observable.just(data1, data3));

        ApiResponse mockApiResponse = new ApiResponse(200, "success", null, true);
        when(venusService.commitFeedback(any(FeedbackBean.class))).thenReturn(Observable.just(mockApiResponse));

        when(feedbackView.isAlive()).thenReturn(true);

        FeedbackPresenter feedbackPresenter = new FeedbackPresenter();
        feedbackPresenter.setmVenusService(venusService);
        feedbackPresenter.setmView(feedbackView);
        feedbackPresenter.setmImageUploader(imageUploader);
        List<Uri> list = new ArrayList<>();
        list.add(Uri.parse("aa"));
        feedbackPresenter.commitFeedbackInfo(3, "zz", "zz", list);
        //在单侧结束前停一小会，否则无法执行完程序
        Thread.currentThread().sleep(500);

        verify(feedbackView,times(1)).commitComplete(true);

    }


}