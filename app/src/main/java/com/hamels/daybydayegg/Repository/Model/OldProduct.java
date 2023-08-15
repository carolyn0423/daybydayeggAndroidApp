package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class OldProduct implements Parcelable {
    public static final String TAG = OldProduct.class.getSimpleName();

    private String productNo;
    private String imageSrc;

    protected OldProduct(Parcel in) {
        productNo = in.readString();
        imageSrc = in.readString();
    }

    public static final Creator<OldProduct> CREATOR = new Creator<OldProduct>() {
        @Override
        public OldProduct createFromParcel(Parcel in) {
            return new OldProduct(in);
        }

        @Override
        public OldProduct[] newArray(int size) {
            return new OldProduct[size];
        }
    };

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productNo);
        dest.writeString(imageSrc);
    }
}
