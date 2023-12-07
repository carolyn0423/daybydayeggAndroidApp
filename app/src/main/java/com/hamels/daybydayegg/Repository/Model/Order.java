package com.hamels.daybydayegg.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order implements Parcelable {
    public static final String TAG = Order.class.getSimpleName();

    //for recyclerView show full product use
    public boolean showDetail = false;

    @SerializedName("ID")
    private String id;
    private String createDateTime;
    private int realPayTotal;

    //2:一般購物, 3:企業專區
    @SerializedName("systemChannel_ID")
    private int systemChannelId;

    //待付款、待出貨、取消訂單、完成出貨、已換貨、已退貨
    private String status;

    private ReturnApplyData returnApplyData;
    private int returnMoney;
    @SerializedName("order_product")
    private List<OrderProduct> orderProduct;

    public Order() {
        showDetail = false;
        id = "";
        createDateTime = "";
        realPayTotal = 0;
        systemChannelId = 0;
        status = "";
        returnApplyData = null;
        returnMoney = 0;
        orderProduct = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public String getOrderTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = format.parse(createDateTime);
            return transFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public int getRealPayTotal() {
        return realPayTotal;
    }

    public void setRealPayTotal(int realPayTotal) {
        this.realPayTotal = realPayTotal;
    }

    public int getSystemChannelId() {
        return systemChannelId;
    }

    public void setSystemChannelId(int systemChannelId) {
        this.systemChannelId = systemChannelId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ReturnApplyData getReturnApplyData() {
        return returnApplyData;
    }

    public void setReturnApplyData(ReturnApplyData returnApplyData) {
        this.returnApplyData = returnApplyData;
    }

    public int getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(int returnMoney) {
        this.returnMoney = returnMoney;
    }

    public List<OrderProduct> getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(List<OrderProduct> orderProduct) {
        this.orderProduct = orderProduct;
    }

    public boolean isEenterpriseOrder() {
        return systemChannelId == 3;
    }

    public boolean canReturn() {
        if (!shippingTimeOver12Day() && returnApplyData == null) {
            return true;
        }

        return false;
    }

    private boolean shippingTimeOver12Day() {
        if (orderProduct.size() > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -12);
            Date before12Day = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date createDay = format.parse(orderProduct.get(0).getShippingDateTime());
                return createDay.before(before12Day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static String getOrderStatusByType(int status) {
        switch (status) {
            case 1:
                return EOrderApplication.getInstance().getString(R.string.order_waiting_ship);
            case 2:
            case 4:
            case 5:
                return EOrderApplication.getInstance().getString(R.string.order_finish_api);
            case 3:
                return EOrderApplication.getInstance().getString(R.string.order_cancel);
            case 6:
                return EOrderApplication.getInstance().getString(R.string.order_return_finish);
            case 7:
                return EOrderApplication.getInstance().getString(R.string.order_exchange_finish);
            case 0:
            default:
                return EOrderApplication.getInstance().getString(R.string.order_waiting_pay);
        }
    }

    public boolean isAllProductReturn() {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProduct orderProduct : orderProduct) {
            if (orderProduct.isReturnProduct()) {
                orderProducts.add(orderProduct);
            }
        }

        return orderProducts.size() == orderProduct.size();
    }

    //must generate filter finish/return order because order data from server doesn't separate finish/return status order
    public Order copy(boolean isReturnOrder) {
        Order order = new Order();
        order.showDetail = showDetail;
        order.id = id;
        order.createDateTime = createDateTime;
        order.realPayTotal = realPayTotal;
        order.systemChannelId = systemChannelId;
        order.status = status;
        order.returnApplyData = returnApplyData;
        order.orderProduct = getFilterOrderProduct(isReturnOrder);

        return order;
    }

    private List<OrderProduct> getFilterOrderProduct(boolean isReturnOrder) {
        List<OrderProduct> list = new ArrayList<>();
        for (OrderProduct product : orderProduct) {
            //return product
            if (isReturnOrder && product.isReturnProduct()) {
                list.add(product);
            }
            //finish product
            if (!isReturnOrder && !product.isReturnProduct()) {
                list.add(product);
            }
        }

        return list;
    }

    public String getPreviewImageUrl() {
        String url = "";
        if (orderProduct.size() > 0) {
            url = orderProduct.get(0).getProduct().getImageSrc();
        }
        Log.e(TAG, "url: " + url);
        return url;
    }

    protected Order(Parcel in) {
        id = in.readString();
        createDateTime = in.readString();
        realPayTotal = in.readInt();
        systemChannelId = in.readInt();
        status = in.readString();
        returnApplyData = in.readParcelable(ReturnApplyData.class.getClassLoader());
        orderProduct = in.createTypedArrayList(OrderProduct.CREATOR);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createDateTime);
        dest.writeInt(realPayTotal);
        dest.writeInt(systemChannelId);
        dest.writeString(status);
        dest.writeParcelable(returnApplyData, flags);
        dest.writeTypedList(orderProduct);
    }
}
