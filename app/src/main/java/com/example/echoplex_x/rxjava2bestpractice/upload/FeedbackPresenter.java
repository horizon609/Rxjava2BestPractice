package com.example.echoplex_x.rxjava2bestpractice.upload;

import android.net.Uri;
import android.util.Log;

import com.example.echoplex_x.rxjava2bestpractice.bean.ApiResponse;
import com.example.echoplex_x.rxjava2bestpractice.bean.FeedbackBean;
import com.example.echoplex_x.rxjava2bestpractice.bean.UploadResponseImgBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangting31 on 2017/10/16.
 */

public class FeedbackPresenter {
    private VenusService mVenusService;
    private FeedbackView mView;
    private ImageUploader mImageUploader;

    public void setmVenusService(VenusService mVenusService) {
        this.mVenusService = mVenusService;
    }

    public void setmView(FeedbackView mView) {
        this.mView = mView;
    }

    public void setmImageUploader(ImageUploader mImageUploader) {
        this.mImageUploader = mImageUploader;
    }

    public void commitFeedbackInfo(int type, String commitContent, String phone, List<Uri> uriList) {
        mView.setUIStateToLoading();
        mImageUploader.upload(uriList)
                .toList()
                .toObservable()
                .flatMap(list -> {
                    if (list == null || list.size() == 0) {
                        return mVenusService.commitFeedback(getFeedbackBean(null, phone, type, commitContent));
                    } else {
                        FeedbackBean bean = getFeedbackBean(list.size() > 0 ? list : null, phone, type, commitContent);
                        return mVenusService.commitFeedback(bean);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResponse -> {
                            if (apiResponse.isSuccess()) {
                                if (mView.isAlive()) mView.commitComplete(true);
                            } else {
                                if (mView.isAlive()) mView.commitComplete(false);
                            }
                        }, throwable -> {
                            if (mView.isAlive()) mView.commitComplete(false);
                        }
                );
    }

    @NonNull
    private FeedbackBean getFeedbackBean(List<UploadResponseImgBean> uploadList, String phone, int type, String content) {
        FeedbackBean bean = new FeedbackBean();
        bean.contactPhone = phone;
        bean.type = type;
        bean.content = content;
        bean.status = 1;
        if (uploadList != null) {
            bean.files = new ArrayList<>();
            for (UploadResponseImgBean imgBean : uploadList) {
                FeedbackBean.FileBean fileBean = new FeedbackBean.FileBean();
                fileBean.type = 1;
                fileBean.url = imgBean.getOriginalLink();
                bean.files.add(fileBean);
            }
        }
        return bean;
    }
}
