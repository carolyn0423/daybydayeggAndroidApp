package com.hamels.daybydayegg.Login.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.Contract.VerifyCodeContract;
import com.hamels.daybydayegg.Repository.Model.User;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import static com.hamels.daybydayegg.Constant.ApiConstant.TASK_POST_VERIFY_SMS;

public class VerifyCodePresenter extends BasePresenter<VerifyCodeContract.View> implements VerifyCodeContract.Presenter {
    public static final String TAG = VerifyCodePresenter.class.getSimpleName();

    public VerifyCodePresenter(VerifyCodeContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void resendSms() {
        repositoryManager.callResendSmsApi(EOrderApplication.CUSTOMER_ID, repositoryManager.getUserAccount(), new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                view.showResendHint(type);
            }
        });
    }

    @Override
    public void goLogin() {
        login();
    }

    @Override
    public void checkVerifyCodeAndFinish(String code) {
        if (code.length() != 4) {
            view.showWrongVerifyHint();
        } else {
            repositoryManager.callVerifySmsApi(code , repositoryManager.getUserID() , new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    if(task==TASK_POST_VERIFY_SMS){
                        view.showWrongVerifyHint(true,type);
                    }
                    else{
                        view.showWrongVerifyHint(false,type);
                    }
                }
            }, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.showWrongVerifyHint(false,type);
                }
            });
        }
    }

    private void login() {
        repositoryManager.callLoginApi(repositoryManager.getCustomerID(),repositoryManager.getUserAccount(), repositoryManager.getUserPassword(), new BaseContract.ValueCallback<User>() {
            @Override
            public void onValueCallback(int task, User user) {
                view.finishActivity();
            }
        }, new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String message) {
                view.showWrongVerifyHint(message);
            }
        });
    }

    @Override
    public void logout() {
        repositoryManager.callLogOutApi(repositoryManager.getUserID(), new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                view.finishActivity();
            }
        });
    }
}
