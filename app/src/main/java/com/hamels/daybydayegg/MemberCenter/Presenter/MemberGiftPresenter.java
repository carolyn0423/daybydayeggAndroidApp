package com.hamels.daybydayegg.MemberCenter.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.MemberCenter.Contract.MemberGiftContract;
import com.hamels.daybydayegg.MemberCenter.Contract.MemberInfoChangeContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.Coupon;
import com.hamels.daybydayegg.Repository.Model.DrawLots;
import com.hamels.daybydayegg.Repository.Model.User;
import com.hamels.daybydayegg.Repository.RepositoryManager;
import com.hamels.daybydayegg.Utils.FormatUtils;

import java.util.List;

public class MemberGiftPresenter extends BasePresenter<MemberGiftContract.View> implements MemberGiftContract.Presenter {
    public static final String TAG = MemberGiftPresenter.class.getSimpleName();

    public MemberGiftPresenter(MemberGiftContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    public void getCouponData(){
        repositoryManager.callGetRegisterCouponApi(repositoryManager.getUserID() ,  new BaseContract.ValueCallback<Coupon>() {
            @Override
            public void onValueCallback(int task, Coupon type) {
                view.setCouponData(type);
            }
        });
    }

    public String getInvitationCode() {
        return repositoryManager.getInvitationCode();
    }
}
