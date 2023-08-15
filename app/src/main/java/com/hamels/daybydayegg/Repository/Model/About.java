package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class About implements Parcelable {
    public static final String TAG = About.class.getSimpleName();
    public static final String STATUS_READ = "read";
    public static final String STATUS_DELETE = "delete";

    @SerializedName("about_id")
    private int id = 0;
    private String about_name = "";
    private String address = "";
    private String service_time = "";
    private String phone = "";
    private String line_at = "";
    private String email = "";
    private String fb_url = "";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbout_name() {
        return about_name;
    }

    public void setAbout_name(String about_name) {
        this.about_name = about_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLine_at() {
        return line_at;
    }

    public void setLine_at(String line_at) {
        this.line_at = line_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFb_url() {
        return fb_url;
    }

    public void setFb_url(String fb_url) {
        this.fb_url = fb_url;
    }

    protected About(Parcel in) {
        id = in.readInt();
        about_name = in.readString();
        address = in.readString();
        service_time = in.readString();
        phone = in.readString();
        line_at = in.readString();
        email = in.readString();
        fb_url = in.readString();
    }

    public static final Creator<About> CREATOR = new Creator<About>() {
        @Override
        public About createFromParcel(Parcel in) {
            return new About(in);
        }

        @Override
        public About[] newArray(int size) {
            return new About[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(about_name);
        dest.writeString(address);
        dest.writeString(service_time);
        dest.writeString(phone);
        dest.writeString(line_at);
        dest.writeString(email);
        dest.writeString(fb_url);
    }
}

