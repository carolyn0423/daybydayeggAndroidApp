package com.hamels.daybydayegg.MemberCenter.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.MemberMessage;

import java.util.List;

public interface MailFileContract {
    interface View extends BaseContract.View {
        void showMailDetail(MemberMessage memberMessage);

        void setMessageList(List<MemberMessage> memberMessages);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMessageList();

        void deleteMessage(MemberMessage memberMessage, BaseContract.ValueCallback<String> valueCallback);

        void readMessage(MemberMessage memberMessage);

        boolean getUserLogin();
    }
}
