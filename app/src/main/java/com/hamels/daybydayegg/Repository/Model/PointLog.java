package com.hamels.daybydayegg.Repository.Model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PointLog {
    public static final String TAG = PointLog.class.getSimpleName();

    @SerializedName("dmtp")
    private String dmtp = "";
    @SerializedName("trdate")
    private String trDate = "";
    @SerializedName("dmpoint")
    private int dmPoint = 0;

    public String getDmtp() {
        return dmtp;
    }

    public void setDmtp(String dmtp) {
        this.dmtp = dmtp;
    }

    public String getTrDate() {
        return trDate;
    }

    public void setTrDate(String trDate) {
        this.trDate = trDate;
    }

    public int getDmPoint() {
        return dmPoint;
    }

    public void setDmPoint(int dmPoint) {
        this.dmPoint = dmPoint;
    }

    public long getDateTimeMillis() {
        long timeMillis = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Date date = simpleDateFormat.parse(trDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeMillis;
    }
}
