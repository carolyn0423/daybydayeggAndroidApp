package com.hamels.daybydayegg.Repository.Model;

import com.google.gson.annotations.SerializedName;

public class WebSetup {
    public static final String TAG = WebSetup.class.getSimpleName();

    @SerializedName("uid")
    private String Uid;
    @SerializedName("sys_name")
    private String SysName;
    @SerializedName("sys_content")
    private String SysContent;
    @SerializedName("sys_description")
    private String SysDescription;
    @SerializedName("sys_param")
    private String SysParam;


    public String getUid() {
        return Uid;
    }

    public String getSysName() {
        return SysName;
    }

    public String getSysContent() {
        return SysContent;
    }

    public String getSysDescription() {
        return SysDescription;
    }

    public String getSysParam() {
        return SysParam;
    }
}

