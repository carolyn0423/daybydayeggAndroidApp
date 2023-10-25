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
    @SerializedName("discount_modeRN")
    private String DiscountModeRN;
    @SerializedName("discount_contentRN")
    private String DiscountContentRN;
    @SerializedName("lower_limitRN")
    private String LowerLimitRN;
    @SerializedName("max_discountRN")
    private String MaxDiscountRN;
    @SerializedName("receive_valid_dayRN")
    private String ReceiveValidDayRN;

    @SerializedName("receiver_typeRM")
    private String ReceiverTypeRM;
    @SerializedName("discount_modeRM")
    private String DiscountModeRM;
    @SerializedName("discount_contentRM")
    private String DiscountContentRM;
    @SerializedName("release_lower_limitRM")
    private String ReleaseLowerLimitRM;
    @SerializedName("lower_limitRM")
    private String LowerLimitRM;
    @SerializedName("max_discountRM")
    private String MaxDiscountRM;
    @SerializedName("receive_valid_dayRM")
    private String ReceiveValidDayRM;

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
    public String getDiscountModeRN() {
        return DiscountModeRN;
    }
    public String getDiscountContentRN() {
        return DiscountContentRN;
    }
    public String getLowerLimitRN() {
        return LowerLimitRN;
    }
    public String getMaxDiscountRN() {
        return MaxDiscountRN;
    }
    public String getReceiveValidDayRN() {
        return ReceiveValidDayRN;
    }

    public String getReceiverTypeRM() {
        return ReceiverTypeRM;
    }
    public String getDiscountModeRM() {
        return DiscountModeRM;
    }
    public String getDiscountContentRM() {
        return DiscountContentRM;
    }
    public String getReleaseLowerLimitRM() { return ReleaseLowerLimitRM; }
    public String getLowerLimitRM() {
        return LowerLimitRM;
    }
    public String getMaxDiscountRM() {
        return MaxDiscountRM;
    }
    public String getReceiveValidDayRM() {
        return ReceiveValidDayRM;
    }
}
