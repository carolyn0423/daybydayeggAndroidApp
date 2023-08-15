package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductMainType implements Parcelable {
    public static final String TAG = ProductMainType.class.getSimpleName();
    public static final String STATUS_READ = "read";
    public static final String STATUS_DELETE = "delete";

    @SerializedName("product_type_main_id")
    private int product_type_main_id = 0;
    private String product_type_main_name = "";
    private String picture_url = "";
    private String location_id = "";
    private String location_name = "";

    public int getId() {
        return product_type_main_id;
    }

    public void setId(int id) {
        this.product_type_main_id = id;
    }

    public String getType_name() {
        return product_type_main_name;
    }

    public void setType_name(String type_name) {
        this.product_type_main_name = type_name;
    }

    protected ProductMainType(Parcel in) {
        product_type_main_id = in.readInt();
        product_type_main_name = in.readString();
        picture_url = in.readString();
        location_id = in.readString();
        location_name = in.readString();
    }

    public static final Creator<ProductMainType> CREATOR = new Creator<ProductMainType>() {
        @Override
        public ProductMainType createFromParcel(Parcel in) {
            return new ProductMainType(in);
        }

        @Override
        public ProductMainType[] newArray(int size) {
            return new ProductMainType[size];
        }
    };

    public String getPicture_url() {
        return picture_url;
    }

    public String getProduct_Type_Main_Name() {
        return product_type_main_name;
    }

    public String getLocationID() {
        return location_id;
    }

    public String getLocationName() {
        return location_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(product_type_main_id);
        dest.writeString(product_type_main_name);
        dest.writeString(picture_url);
        dest.writeString(location_id);
        dest.writeString(location_name);
    }
}

