package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductType implements Parcelable {
    public static final String TAG = ProductType.class.getSimpleName();
    public static final String STATUS_READ = "read";
    public static final String STATUS_DELETE = "delete";

    @SerializedName("product_type_id")
    private int id = 0;
    private String type_name = "";
    private String location_name = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public String getLocationName() {
        return location_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    protected ProductType(Parcel in) {
        id = in.readInt();
        type_name = in.readString();
        location_name = in.readString();
    }

    public static final Creator<ProductType> CREATOR = new Creator<ProductType>() {
        @Override
        public ProductType createFromParcel(Parcel in) {
            return new ProductType(in);
        }

        @Override
        public ProductType[] newArray(int size) {
            return new ProductType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type_name);
        dest.writeString(location_name);
    }
}

