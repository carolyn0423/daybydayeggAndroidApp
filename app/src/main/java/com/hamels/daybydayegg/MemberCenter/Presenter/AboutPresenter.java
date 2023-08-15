package com.hamels.daybydayegg.MemberCenter.Presenter;

import android.Manifest;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.MemberCenter.Contract.AboutContract;
import com.hamels.daybydayegg.Repository.Model.About;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

public class AboutPresenter extends BasePresenter<AboutContract.View> implements AboutContract.Presenter {
    public static final String TAG = AboutPresenter.class.getSimpleName();

    public AboutPresenter(AboutContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getAboutData() {
        repositoryManager.callAboutDataApi(new BaseContract.ValueCallback<List<About>>() {
            @Override
            public void onValueCallback(int task, List<About> type) {
                view.setAboutData(type);
            }
        });
    }

    @Override
    public void callPhone(final String phone) {
        view.checkPermission(new BaseContract.ValueCallback<Boolean>() {
            @Override
            public void onValueCallback(int task, Boolean type) {
                if (type) {
                    view.intentToPhoneCall(phone.replace("-", ""));
                }
            }
        }, Manifest.permission.CALL_PHONE);
    }
}
