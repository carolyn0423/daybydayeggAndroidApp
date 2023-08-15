package com.hamels.daybydayegg.MemberCenter.Contract;

import androidx.annotation.StringRes;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.Coupon;
import com.hamels.daybydayegg.Repository.Model.DrawLots;
import com.hamels.daybydayegg.Repository.Model.User;

import java.util.List;

public interface MemberGiftContract {
    interface View extends BaseContract.View {

        void setCouponData(Coupon type);
    }

    interface Presenter extends BaseContract.Presenter {
        void getCouponData();

        String getInvitationCode();
    }
}
