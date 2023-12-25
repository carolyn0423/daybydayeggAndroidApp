package com.hamels.daybydayegg.Repository.Model;

public class Often {
    public static final String TAG = Often.class.getSimpleName();

    private String uid = "";

    private String mobile = "";
    private String nick_name = "";

    private String addr_name = "";
    private String city_code = "";
    private String area_code = "";
    private String address = "";
    private String tmp_city_code = "";
    private String tmp_area_code = "";

    private String city_list = "";

    private String vat_number = "";
    private String vat_title = "";

    public Often(String key, String value) {
        this.mobile = key;
        this.nick_name = value;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nick_name;
    }

    public void setNickName(String nick_name) {
        this.nick_name = nick_name;
    }


    public String getAddrName() {
        return addr_name;
    }

    public void setAddrName(String addr_name) {
        this.addr_name = addr_name;
    }

    public String getCityCode() {
        return city_code;
    }

    public void setCityCode(String city_code) {
        this.city_code = city_code;
    }

    public String getAreaCode() {
        return area_code;
    }

    public void setAreaCode(String area_code) {
        this.area_code = area_code;
    }

    public String getTmpCityCode() {
        return tmp_city_code;
    }

    public void setTmpCityCode(String tmp_city_code) {
        this.tmp_city_code = tmp_city_code;
    }

    public String getTmpAreaCode() {
        return tmp_area_code;
    }

    public void setTmpAreaCode(String tmp_area_code) {
        this.tmp_area_code = tmp_area_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getVatNumber() {
        return vat_number;
    }

    public void setVatNumber(String vat_number) {
        this.vat_number = vat_number;
    }

    public String getVatTitle() {
        return vat_title;
    }

    public void setVatTitle(String vat_title) {
        this.vat_title = vat_title;
    }

    public String getCityList() {
        return city_list;
    }
}

