package com.hamels.daybydayegg.MemberCenter.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.MemberCenter.Contract.AdminMessageContract;
import com.hamels.daybydayegg.Repository.Model.MessageGroup;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

public class AdminMessagePresenter extends BasePresenter<AdminMessageContract.View> implements AdminMessageContract.Presenter {
    public static final String TAG = AdminMessagePresenter.class.getSimpleName();

    public AdminMessagePresenter(AdminMessageContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMessageList() {
        repositoryManager.callMessageListGroup(new BaseContract.ValueCallback<List<MessageGroup>>() {
            @Override
            public void onValueCallback(int task, List<MessageGroup> type) {
                view.setMessageList(type);
            }
        });
    }

    public void goMessageList(MessageGroup messageGroup){
        view.goMessageList(messageGroup.getMemberID(), messageGroup.getMobile());
    }

    public boolean getUserLogin(){ return repositoryManager.getUserLogin(); }
}
