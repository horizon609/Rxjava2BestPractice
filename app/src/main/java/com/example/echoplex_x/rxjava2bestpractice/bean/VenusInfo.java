package com.example.echoplex_x.rxjava2bestpractice.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangting31 on 2017/10/12.
 */

public class VenusInfo implements Parcelable{
    /**
     * code : 200
     * data : {"expireTime":1506158158808,"token":"MWS erpbossapi:ireIpqqKZ8M8WzqkTuElJb+27vU=","url":"http://pic.meituan.com/extrastorage/erpbossapi"}
     */

    public int code;
    public DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable{
        /**
         * expireTime : 1506158158808
         * token : MWS erpbossapi:ireIpqqKZ8M8WzqkTuElJb+27vU=
         * url : http://pic.meituan.com/extrastorage/erpbossapi
         */

        private long expireTime;
        private String token;
        private String url;

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.expireTime);
            dest.writeString(this.token);
            dest.writeString(this.url);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.expireTime = in.readLong();
            this.token = in.readString();
            this.url = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeParcelable(this.data, flags);
    }

    public VenusInfo() {
    }

    protected VenusInfo(Parcel in) {
        this.code = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<VenusInfo> CREATOR = new Parcelable.Creator<VenusInfo>() {
        @Override
        public VenusInfo createFromParcel(Parcel source) {
            return new VenusInfo(source);
        }

        @Override
        public VenusInfo[] newArray(int size) {
            return new VenusInfo[size];
        }
    };
}
