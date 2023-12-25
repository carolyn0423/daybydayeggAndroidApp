package com.hamels.daybydayegg.MemberCenter.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.Often;

import java.util.List;

public interface OftenContract {
    interface View extends BaseContract.View {
        void setOftenList(String sFunctionName, List<Often> type);

        void showAlert(String sMessage);

        void setPropertyCode(List<Address> addresses);
    }

    interface Presenter extends BaseContract.Presenter {
        void getOftenList(String sFunctionName);

        void GetFunctionSaveDataApi(String functionName, String uid, String sAddrName, String sCityCode, String sAreaCode, String sAddress);

        void saveOftenMobile(String sMobile, String sNick);

        void getPropertyData();
    }
}
