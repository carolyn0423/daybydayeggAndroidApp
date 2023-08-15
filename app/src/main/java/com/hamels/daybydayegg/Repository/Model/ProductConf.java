package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductConf implements Parcelable {
    public static final String TAG = ProductConf.class.getSimpleName();

    @SerializedName("conf_id")
    private int id = 0;
    private String product_type_id = "";
    private String main_conf_name = "";
    @SerializedName("conf_name")
    private String conf_name = "";
    private String soldout_flag = "";
    private int price = 0;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_type_id() {
        return product_type_id;
    }

    public void setProduct_type_id(String product_type_id) {
        this.product_type_id = product_type_id;
    }

    public String getMain_conf_name() {
        return main_conf_name;
    }

    public void setMain_conf_name(String main_conf_name) {
        this.main_conf_name = main_conf_name;
    }

    public String getConf_name() {
        return conf_name;
    }

    public void setConf_name(String conf_name) {
        this.conf_name = conf_name;
    }

    public int getprice() {
        return price;
    }

    public void setprice(int price) {
        this.price = price;
    }

    public String getSoldOutQty() {
        return soldout_flag;
    }

    public ProductConf(Parcel in) {
        id = in.readInt();
        conf_name = in.readString();
        main_conf_name = in.readString();
        soldout_flag = in.readString();
    }

    public static final Creator<ProductConf> CREATOR = new Creator<ProductConf>() {
        @Override
        public ProductConf createFromParcel(Parcel in) {
            return new ProductConf(in);
        }

        @Override
        public ProductConf[] newArray(int size) {
            return new ProductConf[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(conf_name);
        dest.writeString(main_conf_name);
    }
}
