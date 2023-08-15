package com.hamels.daybydayegg.Repository.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MemberPoint {
    public static final String TAG = MemberPoint.class.getSimpleName();

    @SerializedName("ID")
    private int id = 0;
    @SerializedName("point")
    private int point = 0;
    @SerializedName("mobile")
    private String mobile = "";
    @SerializedName("ord_point")
    private int ordPoint = 0;
    @SerializedName("log")
    private List<PointLog> log = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getOrdPoint() {
        return ordPoint;
    }

    public void setOrdPoint(int ordPoint) {
        this.ordPoint = ordPoint;
    }

    public List<PointLog> getLog() {
        return log;
    }

    public void setLog(List<PointLog> log) {
        this.log = log;
    }

    public List<PointLog> getInTimeLog() {
        List<PointLog> logs = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MONTH, -2);
        long time = calendar.getTimeInMillis();

        for (PointLog pointLog : log) {
            if (pointLog.getDateTimeMillis() >= time) {
                logs.add(pointLog);
            }
        }

        return logs;
    }
}
