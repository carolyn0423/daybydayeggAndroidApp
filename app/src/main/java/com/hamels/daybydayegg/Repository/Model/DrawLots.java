package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrawLots implements Parcelable {
    public static final String TAG = DrawLots.class.getSimpleName();
    public static final String STATUS_READ = "read";
    public static final String STATUS_DELETE = "delete";

    @SerializedName("lot_id")
    private int id = 0;
    private String lot_type = "";
    private String title = "";
    private String location_name = "";
    private String lot_picture_url = "";
    @SerializedName("end_date")
    private String end_date = "";
    @SerializedName("start_date")
    private String start_date = "";
    private String announce_date = "";
    private String pickup_date = "";
    private String content = "";
    private String is_draw = "";
    private String can_draw_flag = "";

    private List<ProductPicture> lot_picture_url_list;

    private List<LotProduct> lot_product_list;

    public String getCan_draw_flag() {
        return can_draw_flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLot_type() {
        return lot_type;
    }

    public void setLot_type(String lot_type) {
        this.lot_type = lot_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLot_picture_url() {
        return lot_picture_url;
    }

    public void setLot_picture_url(String lot_picture_url) {
        this.lot_picture_url = lot_picture_url;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getAnnounce_date() {
        return announce_date;
    }

    public void setAnnounce_date(String announce_date) {
        this.announce_date = announce_date;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public List<ProductPicture> getLot_picture_url_list() {
        return lot_picture_url_list;
    }

    public void setLot_picture_url_list(List<ProductPicture> lot_picture_url_list) {
        this.lot_picture_url_list = lot_picture_url_list;
    }

    public List<LotProduct> getLot_product_list() {
        return lot_product_list;
    }

    public void setLot_product_list(List<LotProduct> lot_product_list) {
        this.lot_product_list = lot_product_list;
    }

    public String getIs_draw(){
        return is_draw;
    }

    public void setIs_draw(String is_draw){
        this.is_draw = is_draw;
    }

    protected DrawLots(Parcel in) {
        id = in.readInt();
        lot_type = in.readString();
        title = in.readString();
        location_name = in.readString();
        lot_picture_url = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        announce_date = in.readString();
        pickup_date = in.readString();
        content = in.readString();
        is_draw = in.readString();
        lot_picture_url_list = in.createTypedArrayList(ProductPicture.CREATOR);
        lot_product_list = in.createTypedArrayList(LotProduct.CREATOR);
    }

    public static final Creator<DrawLots> CREATOR = new Creator<DrawLots>() {
        @Override
        public DrawLots createFromParcel(Parcel in) {
            return new DrawLots(in);
        }

        @Override
        public DrawLots[] newArray(int size) {
            return new DrawLots[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(lot_type);
        dest.writeString(title);
        dest.writeString(location_name);
        dest.writeString(lot_picture_url);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(announce_date);
        dest.writeString(pickup_date);
        dest.writeString(content);
        dest.writeString(is_draw);
        dest.writeTypedList(lot_picture_url_list);
        dest.writeTypedList(lot_product_list);
    }
}

