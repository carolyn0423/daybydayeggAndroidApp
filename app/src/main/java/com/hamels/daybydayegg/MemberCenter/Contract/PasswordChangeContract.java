package com.hamels.daybydayegg.MemberCenter.Contract;

import androidx.annotation.StringRes;

import com.hamels.daybydayegg.Base.BaseContract;

public interface PasswordChangeContract {
    interface View extends BaseContract.View {
        void showInputErrorAlert(@StringRes int stringRes);

        void showFinishAlert();

        void showErrorMessage(String stringRes);
    }

    interface Presenter extends BaseContract.Presenter {
        void checkInputAndSendApi(String existPsd, String newPsd, String newPsdAgain);
    }
}
