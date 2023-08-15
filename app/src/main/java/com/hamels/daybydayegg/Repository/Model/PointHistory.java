package com.hamels.daybydayegg.Repository.Model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class PointHistory {
    public static final String TAG = PointHistory.class.getSimpleName();
    @SerializedName("member_id")
    public int id;
    public String remark;
    public String bonus_points;
    public String bonus_category_name;
    public String modified_user;
    public String modified_date;


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
