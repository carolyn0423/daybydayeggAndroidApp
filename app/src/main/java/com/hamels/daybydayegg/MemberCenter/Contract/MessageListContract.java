package com.hamels.daybydayegg.MemberCenter.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Message;

import java.util.List;

public interface MessageListContract {
    interface View extends BaseContract.View {
        void setMessageList(List<Message> list);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMessageList();

        void updateReadMessageApi();

        void sendMessage(String message);
    }
}
