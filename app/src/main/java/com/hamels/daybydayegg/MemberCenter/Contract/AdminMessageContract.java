package com.hamels.daybydayegg.MemberCenter.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.MessageGroup;

import java.util.List;

public interface AdminMessageContract {
    interface View extends BaseContract.View {
        void setMessageList(List<MessageGroup> messageGroups);

        void goMessageList(String sReMemberID, String sMobile);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMessageList();

        boolean getUserLogin();

        void goMessageList(MessageGroup messageGroup);
    }
}
