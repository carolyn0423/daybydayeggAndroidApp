package com.hamels.daybydayegg.Main.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.User;

public interface MemberCardContract {
    interface View extends BaseContract.View {
        void setUerInfo(User user);

        void setMemberCardInfo(String cardNum);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMemberInfo();
    }
}
