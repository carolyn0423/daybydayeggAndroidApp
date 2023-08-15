package com.hamels.daybydayegg.Main.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Carousel;

import java.util.List;

public interface MainIndexContract {
    interface View extends BaseContract.View {
        void setCarouselList(List<Carousel> carouselList);

        void setMemberCardImg(String group);

        void CustomerOnlineISFalse();
    }

    interface Presenter extends BaseContract.Presenter {
        void getCarouselList(String sCustomer_id);

        String getName();

        String getGroup();

        void checkMemberData();

        boolean getUserLogin();
    }
}
