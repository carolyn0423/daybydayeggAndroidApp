package com.hamels.daybydayegg.Main.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Store;

import java.util.List;

public interface LocationListContract {
    interface View extends BaseContract.View {
        void changeToNewFunctionName(String area);

        void setLocationList(List<Store> stores);

        void intentToGoogleMap(String address);

        void intentToPhoneCall(String phone);

        void goProductMainType(String sLocationID);

        void setOnlineShoppingFlag(boolean isOnlineShopping, List<Store> Count);
    }

    interface Presenter extends BaseContract.Presenter {
        void setFunctionname(String functionname, String location_id);

        void setStoreOften(String location_id, String uid);

        boolean getUserLogin();

        void saveSourceActive(String sSourceActive);

        String getSourceActive();

        void goProductMainType(String locationID);

        void saveFragmentMainType(String sLocationID, String IsETicket);

        String getFragmentLocation();
    }
}
