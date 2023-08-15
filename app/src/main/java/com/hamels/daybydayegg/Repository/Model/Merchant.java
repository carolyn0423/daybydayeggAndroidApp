package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Merchant implements Parcelable {
    public static final String TAG = Merchant.class.getSimpleName();
    public static final String STATUS_READ = "read";
    public static final String STATUS_DELETE = "delete";

    @SerializedName("location_id")
    private int location_id = 0;
    private String picture_url = "";
    private String location_name = "";
    private String pickup_way = "";

    public int getId() {
        return location_id;
    }

    public void setId(int id) {
        this.location_id = id;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getPickupWay() {
        return pickup_way;
    }

    protected Merchant(Parcel in) {
        location_id = in.readInt();
        picture_url = in.readString();
        location_name = in.readString();
        pickup_way = in.readString();
    }

    public static final Creator<Merchant> CREATOR = new Creator<Merchant>() {
        @Override
        public Merchant createFromParcel(Parcel in) {
            return new Merchant(in);
        }

        @Override
        public Merchant[] newArray(int size) {
            return new Merchant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(location_id);
        dest.writeString(picture_url);
        dest.writeString(location_name);
        dest.writeString(pickup_way);
    }
}

