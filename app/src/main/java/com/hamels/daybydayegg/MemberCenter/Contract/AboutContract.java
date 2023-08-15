package com.hamels.daybydayegg.MemberCenter.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.About;

import java.util.List;

public interface AboutContract {
    interface View extends BaseContract.View {
        void setAboutData(List<About> aboutList);

        void intentToPhoneCall(String phone);
    }

    interface Presenter extends BaseContract.Presenter {
        void getAboutData();

        void callPhone(String phone);
    }
}
