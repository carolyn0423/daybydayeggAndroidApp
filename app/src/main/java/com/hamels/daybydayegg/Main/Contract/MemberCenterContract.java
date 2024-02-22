package com.hamels.daybydayegg.Main.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.User;

public interface MemberCenterContract {
    interface View extends BaseContract.View {
        void setUserName(String name);

        void setUserPoint(String point);

        void setUserMobile(String mobile);

//        void setUserQrCode(String point);

        void redirectToMainPage();

        void setPointData(User user);

        void deleteMember();

        void deleteError(String message);
    }

    interface Presenter extends BaseContract.Presenter {
        void getUserInfo();

        void logout();

        void getPointData();

        void getDeleteMember();

        boolean getUserLogin();

        void saveSourceActive(String sSourceActive);

        String getShopkeeper();
    }
}
