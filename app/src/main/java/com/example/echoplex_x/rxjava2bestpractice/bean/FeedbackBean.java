package com.example.echoplex_x.rxjava2bestpractice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wangting31 on 2017/10/16.
 */

public class FeedbackBean implements Parcelable{
    public int type;
    public String content;
    public List<FileBean> files;
    public String contactPhone;
    public int status;

    public static class FileBean implements Parcelable{
        public int type;
        public String url;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.type);
            dest.writeString(this.url);
        }

        public FileBean() {
        }

        protected FileBean(Parcel in) {
            this.type = in.readInt();
            this.url = in.readString();
        }

        public static final Creator<FileBean> CREATOR = new Creator<FileBean>() {
            @Override
            public FileBean createFromParcel(Parcel source) {
                return new FileBean(source);
            }

            @Override
            public FileBean[] newArray(int size) {
                return new FileBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.content);
        dest.writeTypedList(this.files);
        dest.writeString(this.contactPhone);
        dest.writeInt(this.status);
    }

    public FeedbackBean() {
    }

    protected FeedbackBean(Parcel in) {
        this.type = in.readInt();
        this.content = in.readString();
        this.files = in.createTypedArrayList(FileBean.CREATOR);
        this.contactPhone = in.readString();
        this.status = in.readInt();
    }

    public static final Creator<FeedbackBean> CREATOR = new Creator<FeedbackBean>() {
        @Override
        public FeedbackBean createFromParcel(Parcel source) {
            return new FeedbackBean(source);
        }

        @Override
        public FeedbackBean[] newArray(int size) {
            return new FeedbackBean[size];
        }
    };
}
