package com.hamels.daybydayegg.MemberCenter.Contract;

import androidx.annotation.StringRes;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.User;

import java.util.List;

public interface MemberInfoChangeContract {
    interface View extends BaseContract.View {
        void setUser(User user);

        void setPropertyCode(List<Address> addressList);

        void setBirthDayModifiedOrNot(String message);

        void showAlert(@StringRes int messageId);

        void showAlert(String message);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMemberInfo();

        void getPropertyData();

        void updateMember(String customerId, String city_code, String area_code, String address, String email, String birth, String carrier_no);
    }
}
