package com.hamels.daybydayegg.Login.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.Contract.ForgetPasswordContract;
import com.hamels.daybydayegg.Repository.RepositoryManager;
import com.hamels.daybydayegg.Utils.FormatUtils;

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordContract.View> implements ForgetPasswordContract.Presenter {
    public static final String TAG = ForgetPasswordPresenter.class.getSimpleName();

    public ForgetPasswordPresenter(ForgetPasswordContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void checkInputValue(String cellphone, String verifyCode, String password, String passwordCheck) {
        String passwordFormat = "[a-zA-Z0-9]{6,12}";
        if (verifyCode.isEmpty()) {
            view.showEmptyVerifyCodeAlert();
        } else if (password.isEmpty() || !FormatUtils.isPasswordFormat(password)) {
            view.showPasswordFormatAlert();
        } else if (passwordCheck.isEmpty() || !passwordCheck.matches(passwordFormat)) {
            view.showPasswordCheckFormatAlert();
        } else if (!password.equals(passwordCheck)) {
            view.showPasswordDifferentAlert();
        } else {
            repositoryManager.callVerifyForgetPsdApi(EOrderApplication.CUSTOMER_ID, cellphone, password, verifyCode, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.showSUCCESSToLogin("重設密碼成功，請重新登入。");
                }
            }, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String message) {
                    if(message.contains("無此會員資料，請先註冊帳號。")){
                        view.showGoRegistAlert(message);
                    }else{
                        view.showAlert(message);
                    }
                }
            });
        }
    }

    @Override
    public void register() {
        view.intentToRegister();
    }
}
