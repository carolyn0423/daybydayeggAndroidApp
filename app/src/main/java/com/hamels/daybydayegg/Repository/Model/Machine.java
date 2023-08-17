package com.hamels.daybydayegg.Repository.Model;

import com.google.gson.annotations.SerializedName;

public class Machine {
    public static final String TAG = Machine.class.getSimpleName();

    @SerializedName("machine_id")
    private String machine_id;
    @SerializedName("vendor_code")
    private String vendor_code;
    @SerializedName("code")
    private String code;
    @SerializedName("machine_status")
    private String machine_status;
    @SerializedName("title")
    private String title;
    @SerializedName("address")
    private String address;
    @SerializedName("lon")
    private double lon;
    @SerializedName("lat")
    private double lat;
    @SerializedName("online")
    private String online;
    @SerializedName("distance")
    private double distance;
    @SerializedName("often_id")
    private String often_id;

    public String getMachineID() {
        return machine_id;
    }

    public String getVendorCode() {
        return vendor_code;
    }

    public String getCode() {
        return code;
    }

    public String getMachineStatus() {
        return machine_status;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public String getOnline() {
        return online;
    }

    public double getDistance() {
        return distance;
    }

    public String geOftenID() {
        return often_id;
    }
}
