package com.hamels.daybydayegg.Login.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.Contract.CustomerListContract;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.Customer;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

public class CustomerListPresenter extends BasePresenter<CustomerListContract.View> implements CustomerListContract.Presenter {
    public static final String TAG = CustomerListPresenter.class.getSimpleName();

    public CustomerListPresenter(CustomerListContract.View view, RepositoryManager repositoryManager, String sMode) {
        super(view, repositoryManager);

        getSelectCustomerList(sMode);

        getPropertyData();
    }

    private void getSelectCustomerList(final String sMode) {
        repositoryManager.callGetCustomerApi("", "", new BaseContract.ValueCallback<List<Customer>>() {
            @Override
            public void onValueCallback(int task, List<Customer> type) {
                view.setCustomerList(type, sMode);
            }
        });
    }

    public void goCustomer(String sCustomerID, String sCustomerName, String sApiUrl) {
        //EOrderApplication.CUSTOMER_ID = sCustomerID;
        //EOrderApplication.DOMAIN = sApiUrl;
        //EOrderApplication.sApiUel = sApiUrl;
        EOrderApplication.isLogin = false;

        repositoryManager.saveCustomerID(EOrderApplication.CUSTOMER_ID);
        repositoryManager.saveCustomerName(EOrderApplication.CUSTOMER_NAME);
        repositoryManager.saveApiUrl(EOrderApplication.sApiUrl);
        view.goCustomer();
    }

    public Boolean saveLoveCustomerID(String sCustomerID) { return repositoryManager.saveLoveCustomer(sCustomerID); }

    public void saveCustomerID(String sCustomerID) { repositoryManager.saveCustomerID(sCustomerID); }

    public void saveApiUrl(String sApiUrl) { repositoryManager.saveApiUrl(sApiUrl); }

    public void goLocationList(String sCustomerID){ view.goLocation(sCustomerID); }

    public void goETicketProductMainType(String sCustomerID){ view.goETicketProductMainType(sCustomerID); }

    public String getSourceActive() { return repositoryManager.getSourceActive(); }

    public void getCustomerList(final String sMode, String sCity, String sCustomerName) {
        repositoryManager.callGetCustomerApi(sCity, sCustomerName, new BaseContract.ValueCallback<List<Customer>>() {
            @Override
            public void onValueCallback(int task, List<Customer> type) {
                view.setCustomerList(type, sMode);
            }
        });
    }

    public void getPropertyData(){

        String sCustomerID = EOrderApplication.CUSTOMER_ID;

        if(sCustomerID == null || sCustomerID.equals("")){
            repositoryManager.callAdminGetPropertyDataApi( new BaseContract.ValueCallback<List<Address>>() {
                @Override
                public void onValueCallback(int task, List<Address> type) {
                    view.setPropertyCode(type);
                }
            });
        }else{
            repositoryManager.callGetPropertyDataApi( new BaseContract.ValueCallback<List<Address>>() {
                @Override
                public void onValueCallback(int task, List<Address> type) {
                    view.setPropertyCode(type);
                }
            });
        }
    }
}