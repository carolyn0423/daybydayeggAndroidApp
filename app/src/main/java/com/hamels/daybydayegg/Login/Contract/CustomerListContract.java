package com.hamels.daybydayegg.Login.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.Customer;

import java.util.List;

public interface CustomerListContract {
    interface View extends BaseContract.View {

        void setCustomerList(List<Customer> customers, String sMode);

        void goCustomer();

        void setPropertyCode(List<Address> type);

        void goLocation(String customerID);

        void goETicketProductMainType(String sCustomerID);
    }

    interface Presenter extends BaseContract.Presenter {

        void goCustomer(String customer_id, String customer_name, String api_url);

        void getPropertyData();

        Boolean saveLoveCustomerID(String customer_id);

        void getCustomerList(String sMode, String sCity, String sCustomerName);

        void saveCustomerID(String customerID);

        void goLocationList(String customerID);

        String getSourceActive();

        void goETicketProductMainType(String customerID);

        void saveApiUrl(String apiUrl);
    }
}