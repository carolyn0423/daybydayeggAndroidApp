package com.hamels.daybydayegg.MemberCenter.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.MemberCenter.Contract.PasswordChangeContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.RepositoryManager;
import com.hamels.daybydayegg.Utils.FormatUtils;

public class PasswordChangePresenter extends BasePresenter<PasswordChangeContract.View> implements PasswordChangeContract.Presenter {
    public static final String TAG = PasswordChangePresenter.class.getSimpleName();

    public PasswordChangePresenter(PasswordChangeContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void checkInputAndSendApi(String existPsd, String newPsd, String newPsdAgain) {
        if (existPsd.isEmpty() || !FormatUtils.isPasswordFormat(existPsd)) {
            view.showInputErrorAlert(R.string.exist_password_format_error);
        } else if (newPsd.isEmpty() || !FormatUtils.isPasswordFormat(newPsd)) {
            view.showInputErrorAlert(R.string.new_password_format_error);
        } else if (newPsdAgain.isEmpty() || !FormatUtils.isPasswordFormat(newPsdAgain)) {
            view.showInputErrorAlert(R.string.new_password_check_format_error);
        } else if (!newPsd.equals(newPsdAgain)) {
            view.showInputErrorAlert(R.string.new_password_not_match);
        } else {
            repositoryManager.callUpdatePasswordApi(existPsd, newPsd, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.showFinishAlert();
                }
            }, new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String message) {
                    view.showErrorMessage(message);
                }
            });
        }
    }
}
