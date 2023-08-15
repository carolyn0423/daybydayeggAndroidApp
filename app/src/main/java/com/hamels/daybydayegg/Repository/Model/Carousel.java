package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Carousel implements Parcelable {
    public static final String TAG = Carousel.class.getSimpleName();
    public static final String STATUS_READ = "read";
    public static final String STATUS_DELETE = "delete";

    @SerializedName("news_id")
    private int news_id = 0;
    private String title = "";
    private String content = "";
    private String picture_url = "";
    private String picture_url2 = "";
    private String start_date = "";
    private String end_date = "";


    public int getId() {
        return news_id;
    }

    public void setId(int news_id) {
        this.news_id = news_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getPicture_url2() {
        return picture_url2;
    }

    public void setPicture_url2(String picture_url2) {
        this.picture_url2 = picture_url2;
    }

    protected Carousel(Parcel in) {
        news_id = in.readInt();
        picture_url = in.readString();
        picture_url2 = in.readString();
        end_date = in.readString();
        start_date = in.readString();
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<Carousel> CREATOR = new Creator<Carousel>() {
        @Override
        public Carousel createFromParcel(Parcel in) {
            return new Carousel(in);
        }

        @Override
        public Carousel[] newArray(int size) {
            return new Carousel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(news_id);
        dest.writeString(picture_url);
        dest.writeString(picture_url2);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(end_date);
        dest.writeString(start_date);
    }
}

