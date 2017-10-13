package com.example.echoplex_x.rxjava2bestpractice.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by wangting31 on 2017/10/12.
 */

public class UploadResponseImgBean implements Parcelable, Comparable{
    /**
     * originalLink : http://p1.meituan.net/erpbossapi/8be8111b730ef1cbc3000c3f0f5c99e5393958.jpg
     * height : 2048
     * fileKey : /erpbossapi/8be8111b730ef1cbc3000c3f0f5c99e5393958.jpg
     * width : 1536
     * originalFileName : 20170922_105624.jpg
     * originalFileSize : 393958
     */

    private String originalLink;
    private int height;
    private String fileKey;
    private int width;
    private String originalFileName;
    private int originalFileSize;
    public int index;

    public String getOriginalLink() {
        return originalLink;
    }

    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public int getOriginalFileSize() {
        return originalFileSize;
    }

    public void setOriginalFileSize(int originalFileSize) {
        this.originalFileSize = originalFileSize;
    }

    public UploadResponseImgBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalLink);
        dest.writeInt(this.height);
        dest.writeString(this.fileKey);
        dest.writeInt(this.width);
        dest.writeString(this.originalFileName);
        dest.writeInt(this.originalFileSize);
        dest.writeInt(this.index);
    }

    protected UploadResponseImgBean(Parcel in) {
        this.originalLink = in.readString();
        this.height = in.readInt();
        this.fileKey = in.readString();
        this.width = in.readInt();
        this.originalFileName = in.readString();
        this.originalFileSize = in.readInt();
        this.index = in.readInt();
    }

    public static final Creator<UploadResponseImgBean> CREATOR = new Creator<UploadResponseImgBean>() {
        @Override
        public UploadResponseImgBean createFromParcel(Parcel source) {
            return new UploadResponseImgBean(source);
        }

        @Override
        public UploadResponseImgBean[] newArray(int size) {
            return new UploadResponseImgBean[size];
        }
    };

    @Override
    public int compareTo(@NonNull Object o) {
        UploadResponseImgBean next = (UploadResponseImgBean) o;
        //-1则index由低到高排队
        return next.index > index ? -1 : 1;
    }
}
