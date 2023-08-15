package com.hamels.daybydayegg.Order.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Order;

import java.util.List;

public interface OrderListContract {
    interface View extends BaseContract.View {
        void setList(List<Order> orders);
    }

    interface Presenter extends BaseContract.Presenter {

    }
}
