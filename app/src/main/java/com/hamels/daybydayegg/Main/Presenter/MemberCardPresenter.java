package com.hamels.daybydayegg.Main.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Main.Contract.MemberCardContract;
import com.hamels.daybydayegg.Repository.Model.User;
import com.hamels.daybydayegg.Repository.RepositoryManager;

public class MemberCardPresenter extends BasePresenter<MemberCardContract.View> implements MemberCardContract.Presenter {
    public static final String TAG = MemberCardPresenter.class.getSimpleName();

    public MemberCardPresenter(MemberCardContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMemberInfo() {
        repositoryManager.callGetMemberInfoApi(repositoryManager.getUserID(),new BaseContract.ValueCallback<User>() {
            @Override
            public void onValueCallback(int task, User user) {
                view.setUerInfo(user);
//                view.setMemberCardInfo(user.getMembershipCode(), user.getMembershipSrc());
                view.setMemberCardInfo(user.getMembershipCode());
            }
        });
    }
}
