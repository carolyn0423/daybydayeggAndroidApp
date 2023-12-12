package com.hamels.daybydayegg.Repository.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Machine {
    public static final String TAG = Machine.class.getSimpleName();

    public class Product {
        private String product_name;
        private String quantity;

        public Product(String product_name, String quantity) {
            this.product_name = product_name;
            this.quantity = quantity;
        }

        public String getName() {
            return product_name;
        }

        public String getQuantity() {
            return quantity;
        }
    }

    @SerializedName("machine_id")
    private String machine_id;
    @SerializedName("vendor_code")
    private String vendor_code;
    @SerializedName("code")
    private String code;
    @SerializedName("machine_status")
    private String machine_status;
    @SerializedName("title")
    private String title;
    @SerializedName("address")
    private String address;
    @SerializedName("lon")
    private double lon;
    @SerializedName("lat")
    private double lat;
    @SerializedName("online")
    private String online;
    @SerializedName("distance")
    private double distance;
    @SerializedName("often_id")
    private String often_id;

    private List<Product> product;

    public String getMachineID() {
        return machine_id;
    }

    public String getVendorCode() {
        return vendor_code;
    }

    public String getCode() {
        return code;
    }

    public String getMachineStatus() {
        return machine_status;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public String getOnline() {
        return online;
    }

    public double getDistance() {
        return distance;
    }

    public String geOftenID() {
        return often_id;
    }

    public List<Product> getProductList() {
        return product;
    }
}
