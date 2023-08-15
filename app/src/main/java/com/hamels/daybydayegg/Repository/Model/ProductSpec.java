package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductSpec implements Parcelable {
    public static final String TAG = ProductSpec.class.getSimpleName();

    @SerializedName("spec_id")
    private int id = 0;
    @SerializedName("spec_name")
    private String specname = "";
    private String stock = "";
    private int spec_qty = 0;
    private int price = 0;
    private int sale_price = 0;
    private int ticket_sales_price = 0;
    private int ticket_stock = 0;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecname() {
        return specname;
    }

    public void setSpecname(String specname) {
        this.specname = specname;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String specname) {
        this.stock = stock;
    }

    public int getSpec_qty() {
        return spec_qty;
    }

    public void setSpec_qty(int spec_qty) {
        this.spec_qty = spec_qty;
    }

    public int getprice() {
        return price;
    }

    public void setprice(int price) {
        this.price = price;
    }

    public int getsale_price() {
        return sale_price;
    }

    public void setsale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public int getticket_sales_price() {
        return ticket_sales_price;
    }

    public void setticket_sales_price(int ticket_sales_price) {
        this.ticket_sales_price = ticket_sales_price;
    }

    public int getticket_stock() {
        return ticket_stock;
    }

    public void setticket_stock(int ticket_stock) {
        this.ticket_stock = ticket_stock;
    }




    protected ProductSpec(Parcel in) {
        id = in.readInt();
        specname = in.readString();
        stock = in.readString();
    }

    public static final Creator<ProductSpec> CREATOR = new Creator<ProductSpec>() {
        @Override
        public ProductSpec createFromParcel(Parcel in) {
            return new ProductSpec(in);
        }

        @Override
        public ProductSpec[] newArray(int size) {
            return new ProductSpec[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(specname);
        dest.writeString(stock);
    }
}
