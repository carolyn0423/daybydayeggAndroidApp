package com.hamels.daybydayegg.Login.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Constant.ApiConstant;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.Contract.LoginContract;
import com.hamels.daybydayegg.Repository.Model.Customer;
import com.hamels.daybydayegg.Repository.Model.User;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    public static final String TAG = LoginPresenter.class.getSimpleName();
    public LoginPresenter(LoginContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void register() {
        view.intentToRegister();
    }
    @Override
    public void initSelectCustomer(String sMode) { view.initSelectCustomer(sMode); }
    @Override
    public void login(final String customer_id,final String cellphone, final String password) {
        if (cellphone.isEmpty()) {
            view.showEmptyPhoneAlert();
        } else if (password.isEmpty()) {
            view.showEmptyPasswordAlert();
        } else {
            repositoryManager.callLoginApi(customer_id,cellphone, password, new BaseContract.ValueCallback<User>() {
                @Override
                public void onValueCallback(int task, User user) {
                    repositoryManager.saveCustomerID(Integer.toString(user.getCustomer()));
                    repositoryManager.saveAccountInfo(cellphone, password);
                    repositoryManager.saveUserID(Integer.toString(user.getMember()));
                    repositoryManager.saveVerifyCode(user.getVerifyCode());
                    repositoryManager.saveInvitationCode(user.getInvitationCode());
                    repositoryManager.saveUserName(user.getName());
                    repositoryManager.saveApiUrl(EOrderApplication.sApiUrl);
                    if(task == ApiConstant.RESPONSE_CODE_LOGIN_VERIFIED_ERROR){
                        view.showVerifyErrorAlert("尚未完成簡訊驗證");
                    }
                    else{
                        repositoryManager.callGetBadgeNumberApi(new BaseContract.ValueCallback<String>() {
                            @Override
                            public void onValueCallback(int task, String type) {
                                String[] array= type.split("_");
                                if(array.length==4){
                                    repositoryManager.saveShoppingCartCount(array[3]);
                                }else{
                                    repositoryManager.saveShoppingCartCount("0");
                                }
                            }
                        });

                        EOrderApplication.isLogin = true;
                        saveSourceActive("Login");
                        view.setResultOkFinishActivity();
                    }
                }
            }, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String message) {
                    view.showErrorAlert(message);
                }
            });
        }
    }

    @Override
    public void forgetPassword(final String customer_id,final String cellphone) {
        if (cellphone.isEmpty()) {
            view.showNoInputCellphoneHint();
        } else {
            repositoryManager.callForgetPsdSmsApi(customer_id,cellphone, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.intentToForgetPassword(customer_id,cellphone);
                }
            }, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String message) {
                    if(message.contains("無此會員資料，請先註冊帳號。")){
                        view.showGoRegistAlert(message);
                    }else{
                        view.showErrorAlert(message);
                    }
                }
            });
        }
    }

    @Override
    public void checkAccount(final String customer_id,final String cellphone) {
        if (cellphone.isEmpty()) {

            view.showNoInputCellphoneHint();
        } else {
            repositoryManager.callCheckAccountApi(customer_id, cellphone, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.doForgetPassword(customer_id,cellphone);
                }
            }, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String message) {
                    if(message.contains("無此會員資料，請先註冊帳號。")){
                        view.showGoRegistAlert(message);
                    }else{
                        view.showErrorAlert(message);
                    }
                }
            });
        }
    }

    public void getCustomerDetailList(String sCustomerIDList) {
        repositoryManager.callGetCustomerDetailListApi(sCustomerIDList, new BaseContract.ValueCallback<List<Customer>>() {
            @Override
            public void onValueCallback(int task, List<Customer> type) {
                view.setCustomerList(type);
            }
        });
    }

    public void checkCustomerNo(String customer_no) {
        if (customer_no.isEmpty()) {
            view.showToast("EmptyCustomerNo");
        } else {
            repositoryManager.callGetCustomerByNoApi(customer_no, new BaseContract.ValueCallback<Customer>() {
                @Override
                public void onValueCallback(int task, Customer customers) {
                    String OnlineEnabled = customers.getOnlineEnabled();
                    if(customers.getCustomerID() == null || customers.getCustomerID().equals("")){
                        view.showToast("EmptyCustomerNo");
                    }else if(customers.getOnlineEnabled().equals("N")) {
                        view.showToast("CustomerNoOnline");
                    }else{
                        String sLoveCustomer = repositoryManager.getLoveCustomer();
                        if(sLoveCustomer.indexOf("|" + customers.getCustomerID() + "|") == -1) {
                            repositoryManager.saveLoveCustomer(customers.getCustomerID());

                            view.showToast("AddLove");
                        }

                        EOrderApplication.CUSTOMER_ID = customers.getCustomerID();
                        EOrderApplication.CUSTOMER_NAME = customers.getCustomerName();
                        EOrderApplication.sApiUrl = customers.getApiUrl();

                        repositoryManager.saveCustomerID(customers.getCustomerID());
                        repositoryManager.saveCustomerName(customers.getCustomerName());
                        repositoryManager.saveApiUrl(customers.getApiUrl());

                        getCustomer();
                    }
                }
            });
        }
    }

    public void getCustomer(){
        String sCustomerID = repositoryManager.getCustomerID();
        String sLoveCustomer = repositoryManager.getLoveCustomer();
        String[] LoveCustomer = sLoveCustomer.split("\\|");

        if(sCustomerID.equals((""))){
            EOrderApplication.CUSTOMER_ID = "";
            EOrderApplication.CUSTOMER_NAME = "";
            EOrderApplication.sApiUrl = "";
            if(LoveCustomer.length > 0 && !sLoveCustomer.equals((""))){
                repositoryManager.callGetCustomerDetailApi(LoveCustomer[1], new BaseContract.ValueCallback<Customer>() {
                    @Override
                    public void onValueCallback(int task, Customer customers) {
                        view.setCustomer(customers, "", "", "");
                    }
                });
            }else{
                view.setCustomer(null, "", "", "");
            }
        }else{
            view.setCustomer(null, sCustomerID, repositoryManager.getCustomerName(), repositoryManager.getApiUrl());
        }
    }

    public void saveSourceActive(String sSourceActive) { repositoryManager.saveSourceActive(sSourceActive); }

    public String getLoveCustomer() { return repositoryManager.getLoveCustomer(); }
}