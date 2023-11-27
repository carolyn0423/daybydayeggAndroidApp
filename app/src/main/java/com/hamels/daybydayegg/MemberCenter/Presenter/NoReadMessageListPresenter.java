package com.hamels.daybydayegg.MemberCenter.Presenter;

import android.util.Log;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.MemberCenter.Contract.NoReadMessageListContract;
import com.hamels.daybydayegg.Repository.Model.Message;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

public class NoReadMessageListPresenter extends BasePresenter<NoReadMessageListContract.View> implements NoReadMessageListContract.Presenter {
    public static final String TAG = NoReadMessageListPresenter.class.getSimpleName();

    public NoReadMessageListPresenter(NoReadMessageListContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMessageList() {
        repositoryManager.callGetMessageListApi(new BaseContract.ValueCallback<List<Message>>() {
            @Override
            public void onValueCallback(int task, List<Message> type) {
                Log.e(TAG,type.toString());
                view.setMessageList(type);
            }
        });
    }

    @Override
    public void updateReadMessageApi() {
        repositoryManager.callReadMessageApi(new BaseContract.ValueCallback<Boolean>() {
            @Override
            public void onValueCallback(int task, Boolean type) {
            }
        });
    }

    @Override
    public void sendMessage(String message) {
        repositoryManager.callSendMessageApi(message, new BaseContract.ValueCallback<Boolean>() {
            @Override
            public void onValueCallback(int task, Boolean type) {
                getMessageList();
            }
        });
    }

    public boolean getUserLogin() { return repositoryManager.getUserLogin(); }
}
