package com.hamels.daybydayegg.Repository.Model;

import com.google.gson.annotations.SerializedName;

public class Customer {
    public static final String TAG = Customer.class.getSimpleName();

    @SerializedName("customer_id")
    private String customer_id;
    @SerializedName("customer_name")
    private String customer_name;
    @SerializedName("customer_no")
    private String customer_no;
    @SerializedName("api_url")
    private String api_url;
    @SerializedName("admin_url")
    private String admin_url;
    @SerializedName("city")
    private String city;
    @SerializedName("address")
    private String address;
    @SerializedName("lon")
    private String lon;
    @SerializedName("lat")
    private String lat;
    @SerializedName("distance")
    private double distance;
    @SerializedName("Online_Enabled")
    private String OnlineEnabled;
    @SerializedName("appstore_version")
    private String AppstoreVersion;

    public String getCustomerID() {
        return customer_id;
    }

    public void setCustomerID(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomerName() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomerNo() {
        return customer_no;
    }

    public void setCustomerNo(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getApiUrl() {
        return api_url;
    }

    public void setApiUrl(String api_url) {
        this.api_url = api_url;
    }

    public String getAdminUrl() {
        return admin_url;
    }

    public void setAdminUrl(String admin_url) {
        this.admin_url = admin_url;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getOnlineEnabled() {
        return OnlineEnabled;
    }

    public String getAppstoreVersion() {
        return AppstoreVersion;
    }
}
