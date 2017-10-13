package com.example.echoplex_x.rxjava2bestpractice.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangting31 on 2017/10/12.
 */

public class UploadResponseBean implements Parcelable {
    /**
     * data : {"originalLink":"http://p1.meituan.net/erpbossapi/705813dfedf6d045044b0a16588e4af9381060.jpg","height":2048,"fileKey":"/erpbossapi/705813dfedf6d045044b0a16588e4af9381060.jpg","width":1536,"originalFileName":"20170922_105627.jpg","originalFileSize":381060}
     * success : true
     */
    public UploadResponseImgBean data;
    public boolean success;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
    }

    public UploadResponseBean() {
    }

    protected UploadResponseBean(Parcel in) {
        this.data = in.readParcelable(UploadResponseImgBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Creator<UploadResponseBean> CREATOR = new Creator<UploadResponseBean>() {
        @Override
        public UploadResponseBean createFromParcel(Parcel source) {
            return new UploadResponseBean(source);
        }

        @Override
        public UploadResponseBean[] newArray(int size) {
            return new UploadResponseBean[size];
        }
    };
}
