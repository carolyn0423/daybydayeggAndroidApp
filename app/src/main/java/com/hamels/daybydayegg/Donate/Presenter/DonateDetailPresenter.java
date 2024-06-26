package com.hamels.daybydayegg.Donate.Presenter;

import android.util.Log;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Donate.Contract.DonateDetailContract;
import com.hamels.daybydayegg.Repository.Model.Donate;
import com.hamels.daybydayegg.Repository.Model.DonateCart;
import com.hamels.daybydayegg.Repository.Model.User;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.HashMap;
import java.util.List;

public class DonateDetailPresenter extends BasePresenter<DonateDetailContract.View> implements DonateDetailContract.Presenter {
    public static final String TAG = DonateDetailPresenter.class.getSimpleName();

    public DonateDetailPresenter(DonateDetailContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getDonateDetailByID(String uid) {
        repositoryManager.callGetDonateDetailApi(uid, new BaseContract.ValueCallback<List<Donate>>() {
            @Override
            public void onValueCallback(int task, List<Donate> type) {
                view.setDonateDetail(type);
            }
        });
    }

    @Override
    public void SaveTicketData(final String mobile, final String uid, final String quantity, String nike) {
        if (mobile.isEmpty()) {
            view.showErrorAlert("請填寫手機號");
        } else {
            final User user = repositoryManager.getUser();

            if (mobile.equals(user.getMobile())) {
                view.showErrorAlert("不可贈送禮物給自己");
            } else {
                repositoryManager.callChkPhoneExistApi(mobile, new BaseContract.ValueCallback<Boolean>() {
                    @Override
                    public void onValueCallback(int task, Boolean bSuccess) {
                        if (bSuccess) {
                            repositoryManager.callSaveTicketDataApi(uid, mobile, quantity, new BaseContract.ValueCallback<Boolean>() {
                                @Override
                                public void onValueCallback(int task, Boolean bSuccess) {
                                    if (bSuccess) {
//                                        SavePush(mobile, "提貨券訊息", user.getName() + "送了提貨券給您");
                                        repositoryManager.saveOftenMobile(mobile, nike, "");
                                        view.showErrorAlert("已贈送禮物");
                                        view.goBack();
                                    }
                                    else {
                                        view.showErrorAlert("贈送禮物失敗");
                                    }
                                }
                            });
                        } else {
                            view.showErrorAlert("該手機號尚未註冊");
                        }
                    }
                });
            }
        }
    }

    @Override
    public void updateTicket(String action, String product_id, String spec_id, String give_date) {
        repositoryManager.callUpdateTicketCartApi(action, product_id, spec_id, give_date, new BaseContract.ValueCallback<List<DonateCart>>() {
            @Override
            public void onValueCallback(int task, List<DonateCart> type) {
                view.goWebViewCart();
            }
        });
    }

    public HashMap getOftenMobile(){
        return repositoryManager.getOftenMobile();
    }

//    @Override
//    public void SavePush(final String mobile, final String title, final String content) {
//        Log.e(TAG, "mobilemobilemobilemobilemobilemobile:" + mobile);
//
//        repositoryManager.callgetMemberInfoAssignmemberApi(mobile, new BaseContract.ValueCallback<User>() {
//            @Override
//            public void onValueCallback(int task, User user) {
//                Log.e(TAG, "sendmember_id:" + user.getMember());
//                if (0 != user.getMember()) {
//                    repositoryManager.callSavePushApi(Integer.toString(user.getMember()), title, content, new BaseContract.ValueCallback<Boolean>() {
//                        @Override
//                        public void onValueCallback(int task, Boolean bSuccess) {
//                            if (bSuccess) {
//                                Log.e(TAG, "SUCCESS");
//                            } else {
//                                Log.e(TAG, "ERROR");
//                            }
//                        }
//                    });
//                }
//            }
//        });
//    }
}