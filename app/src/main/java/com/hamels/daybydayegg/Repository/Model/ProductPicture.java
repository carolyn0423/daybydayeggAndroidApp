package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductPicture implements Parcelable {
    public static final String TAG = ProductPicture.class.getSimpleName();

    @SerializedName("uid")
    private int id = 0;
    @SerializedName("picture_url")
    private String pictureurl = "";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    protected ProductPicture(Parcel in) {
        id = in.readInt();
        pictureurl = in.readString();
    }

    public static final Creator<ProductPicture> CREATOR = new Creator<ProductPicture>() {
        @Override
        public ProductPicture createFromParcel(Parcel in) {
            return new ProductPicture(in);
        }

        @Override
        public ProductPicture[] newArray(int size) {
            return new ProductPicture[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(pictureurl);
    }
}
