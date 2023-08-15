package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductColor implements Parcelable {
    public static final String TAG = ProductColor.class.getSimpleName();

    @SerializedName("ID")
    private int id = 0;
    private String name = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected ProductColor(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<ProductColor> CREATOR = new Creator<ProductColor>() {
        @Override
        public ProductColor createFromParcel(Parcel in) {
            return new ProductColor(in);
        }

        @Override
        public ProductColor[] newArray(int size) {
            return new ProductColor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
