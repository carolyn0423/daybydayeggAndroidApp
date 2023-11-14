package com.hamels.daybydayegg.Donate.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Donate;

import java.util.List;

public interface DonateDetailContract {
    interface View extends BaseContract.View {
        void setDonateDetail(List<Donate> productDetail);

        void intentToLogin(int requestCode);

        void showErrorAlert(String message);

        void goBack();
    }

    interface Presenter extends BaseContract.Presenter {
        void getDonateDetailByID(String uid);

        void SaveTicketData(String mobile, String uid, String quantity);

//        void SavePush(String mobile, String title, String content);
    }
}
