package com.hamels.daybydayegg.Donate.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Donate.Contract.DonateContract;
import com.hamels.daybydayegg.Repository.Model.Donate;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

public class DonatePresenter extends BasePresenter<DonateContract.View> implements DonateContract.Presenter {
    public static final String TAG = DonatePresenter.class.getSimpleName();

    public DonatePresenter(DonateContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getDonateDetailByID(int uid) {
        view.goPageDonateDetail(uid);
    }

    @Override
    public void getDonateList() {
        repositoryManager.callGetDonateListApi(new BaseContract.ValueCallback<List<Donate>>() {
            @Override
            public void onValueCallback(int task, List<Donate> type) {
                view.setDonateList(type);
            }
        });
    }

    @Override
    public void getTicketUsedHistory() {
        repositoryManager.callGetTicketUsedHistoryApi(new BaseContract.ValueCallback<List<Donate>>() {
            @Override
            public void onValueCallback(int task, List<Donate> type) {
                view.setDonateList(type);
            }
        });
    }

    @Override
    public void goPageDonateCart(String product_id, String spec_id, String give_date) {
        updateTicket("add", product_id, spec_id, give_date);
    }

    @Override
    public void updateTicket(String action, String product_id, String spec_id, String give_date) {
        repositoryManager.callUpdateTicketCartApi(action, product_id, spec_id, give_date, new BaseContract.ValueCallback<Boolean>() {
            @Override
            public void onValueCallback(int task, Boolean type) {
                view.goPageDonateCart();
            }
        });
    }

    @Override
    public void goPageHistoryDetail(String writeoff_order_id, String eticket_due_date, String modified_date, String meal_no) {
        view.goPageHistoryDetail(writeoff_order_id, eticket_due_date, modified_date, meal_no);
    }

    @Override
    public void getDonateHistoryDetailByID(String writeoff_order_id, String eticket_due_date, String modified_date) {
        repositoryManager.callGetAppDetailApi(writeoff_order_id, eticket_due_date, modified_date, new BaseContract.ValueCallback<List<Donate>>() {
            @Override
            public void onValueCallback(int task, List<Donate> type) {
                view.setDonateDetail(type);
                view.setDonateList(type);
            }
        });
    }

    public void showContactMessage(String sContactPhone){
        view.showAlert(sContactPhone);
    }
}