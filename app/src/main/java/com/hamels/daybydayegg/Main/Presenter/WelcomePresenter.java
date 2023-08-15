package com.hamels.daybydayegg.Main.Presenter;

import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Main.Contract.WelcomeContract;
import com.hamels.daybydayegg.Repository.Model.User;
import com.hamels.daybydayegg.Repository.RepositoryManager;


public class WelcomePresenter extends BasePresenter<WelcomeContract.View> implements WelcomeContract.Presenter {
    public static final String TAG = WelcomePresenter.class.getSimpleName();

    public WelcomePresenter(WelcomeContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    public Boolean getUserInfo() {
        User user = repositoryManager.getUser();
        if (user != null) {
            return true;
        }else{
            return false;
        }
    }

    public String getSourceActive() { return repositoryManager.getSourceActive(); }
}
