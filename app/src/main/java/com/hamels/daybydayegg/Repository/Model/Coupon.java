package com.hamels.daybydayegg.Repository.Model;

import com.google.gson.annotations.SerializedName;

public class Coupon {
    public static final String TAG = Coupon.class.getSimpleName();

    @SerializedName("coupon_id")
    private String CouponID;
    @SerializedName("coupon_no")
    private String CouponNo;
    @SerializedName("coupon_type")
    private String CouponType;
    @SerializedName("title")
    private String Title;
    @SerializedName("content")
    private String Content;
    @SerializedName("permanent_flag")
    private String PermanentFlag;
    @SerializedName("validity_startdate")
    private String ValidityStartdate;
    @SerializedName("validity_enddate")
    private String ValidityEnddate;

    @SerializedName("receiver_typeRN")
    private String ReceiverTypeRN;
    @SerializedName("lower_limitRN")
    private String LowerLimitRN;
    @SerializedName("coupon_valueRN")
    private String CouponValueRN;
    @SerializedName("coupon_value_typeRN")
    private String CouponValueTypeRN;
    @SerializedName("receive_valid_dayRN")
    private String ReceiveValidDayRN;
    @SerializedName("coupon_value_type_nameRN")
    private String CouponValueTypeNameRN;

    @SerializedName("receiver_typeRM")
    private String ReceiverTypeRM;
    @SerializedName("lower_limitRM")
    private String LowerLimitRM;
    @SerializedName("coupon_valueRM")
    private String CouponValueRM;
    @SerializedName("coupon_value_typeRM")
    private String CouponValueTypeRM;
    @SerializedName("receive_valid_dayRM")
    private String ReceiveValidDayRM;
    @SerializedName("coupon_value_type_nameRM")
    private String CouponValueTypeNameRM;

    public String getCouponID() {
        return CouponID;
    }

    public String getCouponNo() {
        return CouponNo;
    }

    public String getCouponType() {
        return CouponType;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public String getPermanentFlag() {
        return PermanentFlag;
    }

    public String getValidityStartdate() {
        return ValidityStartdate;
    }

    public String getValidityEnddate() {
        return ValidityEnddate;
    }



    public String getReceiverTypeRN() {
        return ReceiverTypeRN;
    }

    public String getLowerLimitRN() {
        return LowerLimitRN;
    }

    public String getCouponValueRN() {
        return CouponValueRN;
    }

    public String getCouponValueTypeRN() {
        return CouponValueTypeRN;
    }

    public String getReceiveValidDayRN() {
        return ReceiveValidDayRN;
    }

    public String getCouponValueTypeNameRN() {
        return CouponValueTypeNameRN;
    }


    public String getReceiverTypeRM() {
        return ReceiverTypeRM;
    }

    public String getLowerLimitRM() {
        return LowerLimitRM;
    }

    public String getCouponValueRM() {
        return CouponValueRM;
    }

    public String getCouponValueTypeRM() {
        return CouponValueTypeRM;
    }

    public String getReceiveValidDayRM() {
        return ReceiveValidDayRM;
    }

    public String getCouponValueTypeNameRM() {
        return CouponValueTypeNameRM;
    }
}
