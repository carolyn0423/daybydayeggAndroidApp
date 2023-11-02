package com.hamels.daybydayegg.Login.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Customer;

import java.util.List;

public interface LoginContract {
    interface View extends BaseContract.View {
        void showErrorAlert(String message);

        void showVerifyErrorAlert(String message);

        void showGoRegistAlert(String message);

        void showNoInputCellphoneHint();

        void showGoCustomerAlert(String message);

        void intentToRegister();

        void intentToForgetPassword(String customer_id,String cellphone);

        void intentToTermsOfUse();

        void showEmptyPhoneAlert();

        void showEmptyPasswordAlert();

        void setResultOkFinishActivity();

        void doForgetPassword(String customer_id,String cellphone);

        void setCustomer(Customer customers, String sCustomerID, String sCustomerName, String sApiUrl);

        void initSelectCustomer(String sMode);

        void showEmptyCustomerNoAlert();

        void showErrorCustomerNoAlert();

        void showAddLoveAlert();

        void showExistLoveAlert();

        void setCustomerList(List<Customer> type);
    }

    interface Presenter extends BaseContract.Presenter {
        void register();

        void login(String customer_id,String cellphone, String password);

        void forgetPassword(String customer_id,String cellphone);

        void checkAccount(String customer_id,String cellphone);

        void getCustomer();

        void initSelectCustomer(String sMode);

        void checkCustomerNo(String sCustomerNo);

        void saveSourceActive(String login);

        String getLoveCustomer();

        void getCustomerDetailList(String sLoveCustomer);

        boolean getUserLogin();
    }
}
