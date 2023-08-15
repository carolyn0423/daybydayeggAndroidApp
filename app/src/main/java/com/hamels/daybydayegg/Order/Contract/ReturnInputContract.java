package com.hamels.daybydayegg.Order.Contract;

import androidx.annotation.StringRes;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.OrderProduct;

import java.util.ArrayList;

public interface ReturnInputContract {
    interface View extends BaseContract.View {
        void showAlert(@StringRes int message);

        void showAlert(String message);

        void showReturnResult(boolean isSuccess, String message);
    }

    interface Presenter extends BaseContract.Presenter {
        void checkInputAndSend(ArrayList<OrderProduct> products, int returnType, String name, String address, String phone, String email);
    }
}
