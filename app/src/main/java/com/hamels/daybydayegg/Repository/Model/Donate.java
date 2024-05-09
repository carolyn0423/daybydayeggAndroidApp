package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Donate implements Parcelable {
    public static final String TAG = Donate.class.getSimpleName();

    @SerializedName("uid")
    private int uid = 0;

    // donatelist
    private String picture_url = "";
    private String eticket_due_date = "";
    private String limit_product_name = "";
    private String product_name = "";
    private String limit_unit_text = "";
    private String type_name = "";
    private String spec_name = "";
    private String buy_quantity = "";
    private String give_quantity = "";
    private String left_number = "";
    private String ticket_code = "";
    private String ticket_status = "";
    private String Last_Uid = "";
    private String Next_Uid = "";
    private String TotalNumber = "";
    private String RowNo = "";
    private String cart_flag = "";
    private String product_id = "";
    private String spec_id = "";
    private String give_date = "";
    private String location_name = "";
    private String eticket_shipping = "";

    // history
    private String give_member_name = "";
    private String give_member_mobile = "";
    private String writeoff_due_date = "";
    private String writeoff_order_id = "";
    private String showText = "";
    private String quantity = "";
    private String due_flag = "";
    private String meal_no = "";
    private String modified_date = "";

    private String booking_pickup_datetime = "";
    private String order_end_datetime = "";
    private String order_status_name = "";
    private String order_id = "";
    private String pickup_way = "";
    private String pickup_way_name = "";

    private String ticket_enabled = "";
    private String ticket_due = "";
    private String contact_phone = "";
    private String transaction_name = "";

    public String getGive_member_name() {
        return give_member_name;
    }

    public void setGive_member_name(String give_member_name) {
        this.give_member_name = give_member_name;
    }

    public String getGive_member_mobile() {
        return give_member_mobile;
    }

    public void setGive_member_mobile(String give_member_mobile) {
        this.give_member_mobile = give_member_mobile;
    }

    public String getWriteoff_order_id() {
        return writeoff_order_id;
    }

    public void setWriteoff_order_id(String writeoff_order_id) {
        this.writeoff_order_id = writeoff_order_id;
    }

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

    public String getLimitProductName() {
        return limit_product_name;
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public String getLimitUnitText() {
        return limit_unit_text;
    }

    public void setLimitUnitText(String limit_unit_text) {
        this.limit_unit_text = limit_unit_text;
    }

    public String getTypeName() {
        return type_name;
    }

    public void setTypeName(String type_name) {
        this.type_name = type_name;
    }

    public String getSpecName() {
        return spec_name;
    }

    public void setSpecName(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getBuy_quantity() {
        return buy_quantity;
    }

    public void setBuy_quantity(String buy_quantity) {
        this.buy_quantity = buy_quantity;
    }

    public String getGive_quantity() {
        return give_quantity;
    }

    public void setGive_quantity(String give_quantity) {
        this.give_quantity = give_quantity;
    }

    public String getLeftNumber() {
        return left_number;
    }

    public void setLeftNumber(String left_number) {
        this.left_number = left_number;
    }

    public String getTicketCode() {
        return ticket_code;
    }

    public void setTicketCode(String ticket_code) {
        this.ticket_code = ticket_code;
    }

    public String getTicketStatus() { return ticket_status; }

    public void setTicketStatus(String ticket_status) {
        this.ticket_status = ticket_status;
    }

    public String getLastUid() {
        return Last_Uid;
    }

    public void setLastUid(String Last_Uid) {
        this.Last_Uid = Last_Uid;
    }

    public String getNextUid() {
        return Next_Uid;
    }

    public void setNextUid(String Next_Uid) {
        this.Next_Uid = Next_Uid;
    }

    public String getTotalNumber() {
        return TotalNumber;
    }

    public void setTotalNumber(String TotalNumber) {
        this.TotalNumber = TotalNumber;
    }

    public String getRowNo() {
        return RowNo;
    }

    public void setRowNo(String RowNo) {
        this.RowNo = RowNo;
    }

    public String getcart_flag() {
        return cart_flag;
    }

    public void setcart_flag(String cart_flag) {
        this.cart_flag = cart_flag;
    }

    public String getwriteoff_due_date() {
        return writeoff_due_date;
    }

    public void setwriteoff_due_date(String writeoff_due_date) {
        this.writeoff_due_date = writeoff_due_date;
    }

    public String getshowText() {
        return showText;
    }

    public void setshowText(String showText) {
        this.showText = showText;
    }

    public String getquantity() {
        return quantity;
    }

    public void setquantity(String quantity) {
        this.quantity = quantity;
    }

    public String getdue_flag() {
        return due_flag;
    }

    public void setdue_flag(String due_flag) {
        this.due_flag = due_flag;
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

    public String getgive_date() {
        return give_date;
    }

    public void setgive_date(String give_date) {
        this.give_date = give_date;
    }

    public String getmeal_no() {
        return meal_no;
    }

    public void setmeal_no(String meal_no) {
        this.meal_no = meal_no;
    }

    public String getmodified_date() {
        return modified_date;
    }

    public void setmodified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getbooking_pickup_datetime() {
        return booking_pickup_datetime;
    }

    public void setbooking_pickup_datetime(String booking_pickup_datetime) {
        this.booking_pickup_datetime = booking_pickup_datetime;
    }

    public String getorder_end_datetime() {
        return order_end_datetime;
    }

    public void setorder_end_datetime(String order_end_datetime) {
        this.order_end_datetime = order_end_datetime;
    }

    public String getorder_status_name() {
        return order_status_name;
    }

    public void setorder_status_name(String order_status_name) {
        this.order_status_name = order_status_name;
    }

    public String getorder_id() {
        return order_id;
    }

    public void setorder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTicketShipping() {
        return eticket_shipping;
    }
    public void setTicketShipping(String eticket_shipping) {
        this.eticket_shipping = eticket_shipping;
    }

    public String getpickup_way_name() {
        return pickup_way_name;
    }

    public void setpickup_way_name(String pickup_way_name) {
        this.pickup_way_name = pickup_way_name;
    }

    public String getpickup_way() {
        return pickup_way;
    }

    public void setpickup_way(String pickup_way) {
        this.pickup_way = pickup_way;
    }

    public String getLocationName() {
        return location_name;
    }

    public String getTicketEnabled() {
        return ticket_enabled;
    }

    public String getTicketDue() {
        return ticket_due;
    }

    public String getContactPhone() {
        return contact_phone;
    }

    public String getTransactionName() {
        return transaction_name;
    }

    protected Donate(Parcel in) {
        uid = in.readInt();

        picture_url = in.readString();
        product_name = in.readString();
        type_name = in.readString();
        spec_name = in.readString();
        buy_quantity = in.readString();
        left_number = in.readString();
        location_name = in.readString();
        ticket_enabled = in.readString();
        ticket_due = in.readString();
        contact_phone = in.readString();
        transaction_name = in.readString();
        eticket_shipping = in.readString();
        limit_unit_text = in.readString();
    }

    public static final Creator<Donate> CREATOR = new Creator<Donate>() {
        @Override
        public Donate createFromParcel(Parcel in) {
            return new Donate(in);
        }

        @Override
        public Donate[] newArray(int size) {
            return new Donate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);

        dest.writeString(picture_url);
        dest.writeString(product_name);
        dest.writeString(type_name);
        dest.writeString(spec_name);
        dest.writeString(buy_quantity);
        dest.writeString(left_number);
        dest.writeString(location_name);
        dest.writeString(ticket_enabled);
        dest.writeString(ticket_due);
        dest.writeString(contact_phone);
        dest.writeString(transaction_name);
        dest.writeString(eticket_shipping);
        dest.writeString(limit_unit_text);
    }
}

