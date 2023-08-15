package com.hamels.daybydayegg.Donate.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Donate;

import java.util.List;

public interface DonateContract {
    interface View extends BaseContract.View {
        void goPageDonateDetail(int donate_id);

        void goPageDonateCart();

        void intentToLogin(int requestCode);

        void setDonateList(List<Donate> donateList);

        void goPageHistoryDetail(String writeoff_order_id, String eticket_due_date, String modified_date, String meal_no);

        void setDonateDetail(List<Donate> productDetail);

        void showAlert(String sContactPhone);
    }

    interface Presenter extends BaseContract.Presenter {
        void getDonateDetailByID(int uid);

        void getDonateList();

        void getTicketUsedHistory();

        void goPageDonateCart(String product_id, String spec_id, String give_date);

        void updateTicket(String action, String product_id, String spec_id, String give_date);

        void goPageHistoryDetail(String writeoff_order_id, String eticket_due_date, String modified_date, String meal_no);

        void getDonateHistoryDetailByID(String writeoff_order_id, String eticket_due_date, String modified_date);

        void showContactMessage(String contactPhone);
    }
}
