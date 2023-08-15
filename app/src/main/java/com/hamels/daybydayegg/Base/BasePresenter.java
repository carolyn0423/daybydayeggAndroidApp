package com.hamels.daybydayegg.Base;

import android.util.Log;

import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.RepositoryManager;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Utils.SharedUtils;

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter {
    protected V view;
    protected RepositoryManager repositoryManager;

    public BasePresenter(V view, RepositoryManager repositoryManager) {
        this.view = view;
        this.repositoryManager = repositoryManager;
        repositoryManager.setPresenter(this);
    }

    @Override
    public boolean isLogin() {
        return repositoryManager.getUserLogin();
    }

    @Override
    public void forceLogout() {
        SharedUtils.getInstance().removeAllLocalData(EOrderApplication.getInstance());
        view.showToast(EOrderApplication.getInstance().getString(R.string.force_logout_message));
    }

    public void startCallApi() {
        //Log.e("startCallApi", "fail startCallApi: ");
//        view.showLoadingDialog();
    }

    public void finishApi() {
        Log.e("finishApi", "fail finishApi: ");
//        view.hideLoadingDialog();
    }
}
