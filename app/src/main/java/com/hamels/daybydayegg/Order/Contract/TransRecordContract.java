package com.hamels.daybydayegg.Order.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Order;

import java.util.List;

public interface TransRecordContract {
    interface View extends BaseContract.View {
        void addReturnFragment(Order order, int status);

        void setStatusPage(int page);

        void onOrderListReadyCallback();
    }

    interface Presenter extends BaseContract.Presenter {
        void getOrders();

        List<Order> getStatusOrders(int status);
    }
}
