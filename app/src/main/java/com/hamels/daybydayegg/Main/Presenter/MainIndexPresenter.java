package com.hamels.daybydayegg.Main.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Main.Contract.MainIndexContract;
import com.hamels.daybydayegg.Repository.Model.Carousel;
import com.hamels.daybydayegg.Repository.Model.Customer;
import com.hamels.daybydayegg.Repository.Model.User;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

public class MainIndexPresenter extends BasePresenter<MainIndexContract.View> implements MainIndexContract.Presenter {
    public static final String TAG = MainIndexPresenter.class.getSimpleName();

    public MainIndexPresenter(MainIndexContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }


    @Override
    public void getCarouselList(final String sCustomer_id) {

        if(EOrderApplication.sApiUrl.equals("")){
            checkCustomerNo(EOrderApplication.sPecialCustomerNo);
        }else {
            if (sCustomer_id.equals("")) {
                repositoryManager.callGetAdminCarouselListApi(new BaseContract.ValueCallback<List<Carousel>>() {
                    @Override
                    public void onValueCallback(int task, List<Carousel> type) {
                        view.setCarouselList(type);

                    }
                });
            } else {
                repositoryManager.callGetCarouselListApi(sCustomer_id, new BaseContract.ValueCallback<List<Carousel>>() {
                    @Override
                    public void onValueCallback(int task, List<Carousel> type) {
                        view.setCarouselList(type);

                    }
                });
            }
        }
    }

    @Override
    public String getName() {
        String Name = "";
        if (repositoryManager.getUserLogin()) {
            Name = repositoryManager.getUser().getName();
        }
        else{

        }
        return Name;
    }

    @Override
    public String getGroup() {
        String Group = "";
        if (repositoryManager.getUserLogin()) {
            Group = repositoryManager.getUser().getGroup();
        }
        else{

        }
        return Group;
    }

    @Override
    public void checkMemberData() {
        if(!repositoryManager.getUserID().equals("")){
            repositoryManager.callGetMemberInfoApi(repositoryManager.getUserID(),new BaseContract.ValueCallback<User>() {
                @Override
                public void onValueCallback(int task, User user) {
                    if(user != null) {
                        if(user.getOnlineEnabled() != null && user.getOnlineEnabled().equals("Y")) {
                            repositoryManager.saveUser(user);
                            view.setMemberCardImg(user.getGroup());
                        }
                    }else{
                        view.CustomerOnlineISFalse();
                    }
                }
            });
        }
    }

    public boolean getUserLogin(){ return repositoryManager.getUserLogin(); }

    public void checkCustomerNo(String customer_no) {
        if (customer_no.isEmpty()) {
            view.showToast("EmptyCustomerNo");
        } else {
            repositoryManager.callGetCustomerByNoApi(customer_no, new BaseContract.ValueCallback<Customer>() {
                @Override
                public void onValueCallback(int task, Customer customers) {
                    if(customers.getCustomerID() == null || customers.getCustomerID().equals("")){
                        view.showToast("EmptyCustomerNo");
                    }else{
                        String sLoveCustomer = repositoryManager.getLoveCustomer();
                        if(sLoveCustomer.indexOf("|" + customers.getCustomerID() + "|") == -1) {
                            repositoryManager.saveLoveCustomer(customers.getCustomerID());

                            //view.showToast("AddLove");
                        }

                        EOrderApplication.CUSTOMER_ID = customers.getCustomerID();
                        EOrderApplication.CUSTOMER_NAME = customers.getCustomerName();
                        EOrderApplication.sApiUrl = customers.getApiUrl();

                        repositoryManager.saveCustomerID(customers.getCustomerID());
                        repositoryManager.saveCustomerName(customers.getCustomerName());
                        repositoryManager.saveApiUrl(customers.getApiUrl());

                        repositoryManager.callGetCarouselListApi(customers.getCustomerID(), new BaseContract.ValueCallback<List<Carousel>>() {
                            @Override
                            public void onValueCallback(int task, List<Carousel> type) {
                                view.setCarouselList(type);

                            }
                        });
                    }
                }
            });
        }
    }
}
