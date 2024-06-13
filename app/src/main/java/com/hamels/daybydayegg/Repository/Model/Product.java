package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product implements Parcelable {
    public static final String TAG = Product.class.getSimpleName();
    public static final String STATUS_READ = "read";
    public static final String STATUS_DELETE = "delete";

    @SerializedName("product_id")
    private int id = 0;
    private String product_type_main_name = "";
    private String product_type_main_id = "";
    private String type_name = "";
    private String product_name = "";
    private String location_id = "";
    private String sales_type = "";
    private int price = 0;
    private int sale_price = 0;
    private int ticket_sales_price = 0;
    private String modified_date = "";
    private String freight_title = "";
    private String picture_url = "";
    private String coupon_id = "";
    private String coupon_title = "";
    private List<ProductPicture> picture_url_list;
    private String dealer_product_id = "";
    private String desc = "";
    private String store_name = "";
    private List<ProductSpec> spec_list;
    private int spec_cnt;
    private List<ProductConf> conf_list;
    private String soldout_flag = "";
    private int limit_quantity = 1;

    public String getLocation_id() {
        return location_id;
    }

    public String getSalesType() {
        return sales_type;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public int getticket_sales_price() {
        return ticket_sales_price;
    }

    public void setticket_sales_price(int ticket_sales_price) {
        this.ticket_sales_price = ticket_sales_price;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getDealer_product_id() {
        return dealer_product_id;
    }

    public void setDealer_product_id(String dealer_product_id) {
        this.dealer_product_id = dealer_product_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProductTypeMainName() {
        return product_type_main_name;
    }

    public String getProductTypeMainID() {
        return product_type_main_id;
    }

    public String getTypeName() {
        return type_name;
    }

    public String getCoupon_ID() {
        return coupon_id;
    }
    public void setCoupon_ID(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_title() {
        return coupon_title;
    }
    public void setCoupon_title(String coupon_title) {
        this.coupon_title = coupon_title;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getFreight_title() {
        return freight_title;
    }

    public void setFreight_title(String freight_title) {
        this.freight_title = freight_title;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public List<ProductPicture> getPicture_url_list() {
        return picture_url_list;
    }

    public void setPicture_url_list(List<ProductPicture> picture_url_list) {
        this.picture_url_list = picture_url_list;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public List<ProductSpec> getSpec_list() {
        return spec_list;
    }

    public void setSpec_list(List<ProductSpec> spec_list) {
        this.spec_list = spec_list;
    }

    public List<ProductConf> getConf_list() {
        return conf_list;
    }

    public void setConf_list(List<ProductConf> conf_list) {
        this.conf_list = conf_list;
    }

    public void setspec_cnt(int spec_cnt) {
        this.spec_cnt = spec_cnt;
    }

    public int getspec_cnt() {
        return spec_cnt;
    }

    public String getSoldoutFlag() { return soldout_flag; }

    public int getLimitQuantity() { return limit_quantity; }

    protected Product(Parcel in) {
        id = in.readInt();
        price = in.readInt();
        sale_price = in.readInt();
        limit_quantity = in.readInt();
        picture_url = in.readString();
        sales_type = in.readString();
        product_name = in.readString();
        freight_title = in.readString();
        modified_date = in.readString();
        picture_url_list = in.createTypedArrayList(ProductPicture.CREATOR);
        dealer_product_id = in.readString();
        desc = in.readString();
        store_name = in.readString();
        spec_list = in.createTypedArrayList(ProductSpec.CREATOR);
        soldout_flag = in.readString();
        product_type_main_id = in.readString();
        coupon_id = in.readString();
        coupon_title = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(price);
        dest.writeInt(sale_price);
        dest.writeInt(limit_quantity);
        dest.writeString(picture_url);
        dest.writeString(product_name);
        dest.writeString(sales_type);
        dest.writeString(freight_title);
        dest.writeString(modified_date);
        dest.writeTypedList(picture_url_list);
        dest.writeString(desc);
        dest.writeString(dealer_product_id);
        dest.writeString(store_name);
        dest.writeTypedList(spec_list);
        dest.writeString(soldout_flag);
        dest.writeString(product_type_main_id);
        dest.writeString(coupon_id);
        dest.writeString(coupon_title);
    }
}

