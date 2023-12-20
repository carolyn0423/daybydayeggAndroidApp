package com.hamels.daybydayegg.Donate.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.DonateCart;

import java.util.HashMap;
import java.util.List;

public interface DonateCartContract {
    interface View extends BaseContract.View {
        void intentToLogin(int requestCode);

        void showErrorAlert(String message);

        void setDonateCart(List<DonateCart> donateList);

        void goBack();
    }

    interface Presenter extends BaseContract.Presenter {
        void updateTicket(String action, String product_id, String spec_id, String give_date);

        void getDonateCart();

        void GiveTicketGiftByCart(String mobile, String nike);

        void updateTicketCartCode(String cart_ticket_code);

        HashMap<String, String> getOftenMobile();

//        void SavePush(String mobile, String title, String content);
    }
}
