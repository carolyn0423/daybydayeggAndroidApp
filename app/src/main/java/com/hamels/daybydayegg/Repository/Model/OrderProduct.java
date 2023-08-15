package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderProduct implements Parcelable {
    public static final String TAG = OrderProduct.class.getSimpleName();

    @SerializedName("product_ID")
    private int productId = 0;
    @SerializedName("order_ID")
    private String orderId = "";
    @SerializedName("productColor_ID")
    private int productColorId = 0;
    @SerializedName("product_ourPrice")
    private int productOurPrice = 0;
    private int amount = 0;

    //y:已出貨, n:未出貨, c:取消, a:申請退換貨, x:已退貨
    private String shippingStatus = "";
    private String shippingDateTime = "";
    private OldProduct product;
    @SerializedName("product_color")
    private ProductColor productColor;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(int productColorId) {
        this.productColorId = productColorId;
    }

    public int getProductOurPrice() {
        return productOurPrice;
    }

    public void setProductOurPrice(int productOurPrice) {
        this.productOurPrice = productOurPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getShippingDateTime() {
        return shippingDateTime;
    }

    public void setshippingDateTime(String shippingDateTime) {
        this.shippingDateTime = shippingDateTime;
    }

    public OldProduct getProduct() {
        return product;
    }

    public void setProduct(OldProduct product) {
        this.product = product;
    }

    public ProductColor getProductColor() {
        return productColor;
    }

    public void setProductColor(ProductColor productColor) {
        this.productColor = productColor;
    }

    public boolean isReturnProduct() {
        return shippingStatus.equals("a");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrderProduct) {
            OrderProduct product = (OrderProduct) obj;
            return product.orderId.equals(orderId) && product.productId == productId;
        }
        return false;
    }

    protected OrderProduct(Parcel in) {
        productId = in.readInt();
        orderId = in.readString();
        productColorId = in.readInt();
        productOurPrice = in.readInt();
        amount = in.readInt();
        shippingStatus = in.readString();
        shippingDateTime = in.readString();
        product = in.readParcelable(OldProduct.class.getClassLoader());
        productColor = in.readParcelable(ProductColor.class.getClassLoader());
    }

    public static final Creator<OrderProduct> CREATOR = new Creator<OrderProduct>() {
        @Override
        public OrderProduct createFromParcel(Parcel in) {
            return new OrderProduct(in);
        }

        @Override
        public OrderProduct[] newArray(int size) {
            return new OrderProduct[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(orderId);
        dest.writeInt(productColorId);
        dest.writeInt(productOurPrice);
        dest.writeInt(amount);
        dest.writeString(shippingStatus);
        dest.writeString(shippingDateTime);
        dest.writeParcelable(product, flags);
        dest.writeParcelable(productColor, flags);
    }
}
