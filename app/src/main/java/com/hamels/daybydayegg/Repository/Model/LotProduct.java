package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LotProduct implements Parcelable {
    public static final String TAG = LotProduct.class.getSimpleName();

    @SerializedName("lot_product_id")
    private int id = 0;
    private String lot_dealer_product_id = "";
    private String lot_product_name = "";
    private String lot_spec_name = "";
    private String lot_product_price = "";
    private List<ProductSpec> lot_spec_list;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLot_dealer_product_id() {
        return lot_dealer_product_id;
    }

    public void setLot_dealer_product_id(String lot_dealer_product_id) {
        this.lot_dealer_product_id = lot_dealer_product_id;
    }

    public String getLot_product_name() {
        return lot_product_name;
    }

    public void setLot_product_name(String lot_product_name) {
        this.lot_product_name = lot_product_name;
    }

    public String getLot_spec_name() {
        return lot_spec_name;
    }

    public void setLot_spec_name(String lot_spec_name) {
        this.lot_spec_name = lot_spec_name;
    }

    public String getLot_product_price() {
        return lot_product_price;
    }

    public void setLot_product_price(String lot_product_price) {
        this.lot_product_price = lot_product_price;
    }

    public List<ProductSpec> getLot_spec_list() {
        return lot_spec_list;
    }

    public void setLot_spec_list(List<ProductSpec> lot_spec_list) {
        this.lot_spec_list = lot_spec_list;
    }

    protected LotProduct(Parcel in) {
        id = in.readInt();
        lot_dealer_product_id = in.readString();
        lot_product_name = in.readString();
        lot_spec_name = in.readString();
        lot_product_price = in.readString();
        lot_spec_list = in.createTypedArrayList(ProductSpec.CREATOR);
    }

    public static final Creator<LotProduct> CREATOR = new Creator<LotProduct>() {
        @Override
        public LotProduct createFromParcel(Parcel in) {
            return new LotProduct(in);
        }

        @Override
        public LotProduct[] newArray(int size) {
            return new LotProduct[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(lot_dealer_product_id);
        dest.writeString(lot_product_name);
        dest.writeString(lot_spec_name);
        dest.writeString(lot_product_price);
        dest.writeTypedList(lot_spec_list);
    }
}
