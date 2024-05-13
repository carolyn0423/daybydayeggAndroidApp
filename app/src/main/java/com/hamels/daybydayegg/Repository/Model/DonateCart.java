package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DonateCart implements Parcelable {
    public static final String TAG = DonateCart.class.getSimpleName();

    @SerializedName("uid")
    private int uid = 0;

    private String picture_url = "";
    private String product_name = "";
    private String limit_product_name = "";
    private String eticket_due_date = "";
    private String eticket_shipping = "";
    private String left_number = "";
    private String cart_count = "";
    private String give_date = "";
    private String ticket_status = "";
    private String product_id = "";
    private String spec_id = "";
    private String spec_name = "";
    private String type_name = "";


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPictureUrl() {
        return picture_url;
    }

    public void setPictureUrl(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getEticketDueDate() {
        return eticket_due_date;
    }

    public void setEticketDueDate(String redemption_period) {
        this.eticket_due_date = redemption_period;
    }

    public String getTicketShipping() {
        return eticket_shipping == null ? "" : eticket_shipping;
    }
    public void setTicketShipping(String eticket_shipping) {
        this.eticket_shipping = eticket_shipping;
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public String getLimitProductName() {
        return limit_product_name;
    }

    public String getcart_count() {
        return cart_count;
    }

    public void setcart_count(String type_name) {
        this.cart_count = type_name;
    }

    public String getgive_date() {
        return give_date;
    }

    public void setgive_date(String spec_name) {
        this.give_date = spec_name;
    }

    public String getLeftNumber() {
        return left_number;
    }

    public void setLeftNumber(String left_number) {
        this.left_number = left_number;
    }

    public String getTicketStatus() {
        return ticket_status;
    }

    public void setTicketStatus(String ticket_status) {
        this.ticket_status = ticket_status;
    }

    public String getproduct_id() {
        return product_id;
    }

    public void setproduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getspec_id() {
        return spec_id;
    }

    public void setspec_id(String spec_id) {
        this.spec_id = spec_id;
    }

    public String getspec_name() {
        return spec_name;
    }

    public void setspec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getTypeName() {
        return type_name;
    }

    protected DonateCart(Parcel in) {
        uid = in.readInt();

//        picture_url = in.readString();
//        product_name = in.readString();
//        cart_count = in.readString();
//        give_date = in.readString();
//        left_number = in.readString();
    }

    public static final Creator<DonateCart> CREATOR = new Creator<DonateCart>() {
        @Override
        public DonateCart createFromParcel(Parcel in) {
            return new DonateCart(in);
        }

        @Override
        public DonateCart[] newArray(int size) {
            return new DonateCart[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(uid);
//
//        dest.writeString(picture_url);
//        dest.writeString(product_name);
//        dest.writeString(cart_count);
//        dest.writeString(give_date);
//        dest.writeString(buy_quantity);
//        dest.writeString(left_number);
    }
}

