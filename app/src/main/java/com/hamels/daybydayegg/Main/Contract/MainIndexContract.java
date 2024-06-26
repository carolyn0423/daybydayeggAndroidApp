package com.hamels.daybydayegg.Main.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Carousel;

import java.util.List;

public interface MainIndexContract {
    interface View extends BaseContract.View {
        void setCarouselList(List<Carousel> carouselList);

        void setMemberCouponPointData(String sTxt);

        void CustomerOnlineISFalse();

        void intentToLogin(int page);

        void getNoVerift();

        void CallActive();
    }

    interface Presenter extends BaseContract.Presenter {
        void getCarouselList(String sCustomer_id);

        void checkMemberData();

        boolean getUserLogin();

        void getMemberBadge();

        void logout();
    }
}
