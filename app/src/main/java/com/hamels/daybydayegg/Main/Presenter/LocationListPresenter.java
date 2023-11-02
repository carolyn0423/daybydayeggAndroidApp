package com.hamels.daybydayegg.Main.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Main.Contract.LocationListContract;
import com.hamels.daybydayegg.Repository.Model.Store;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

public class LocationListPresenter extends BasePresenter<LocationListContract.View> implements LocationListContract.Presenter {
    public static final String TAG = LocationListPresenter.class.getSimpleName();

    public static final String FUNCTIONNAME_1 = "AppLocation1";
    public static final String FUNCTIONNAME_2 = "AppLocation2";
    public static final String FUNCTIONNAME_3 = "AppLocation3";
    public static final String FUNCTIONNAME_4 = "AppLocation4";

    private String functionname, location_id;

    public LocationListPresenter(LocationListContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    public void setFunctionname(String functionname, String location_id) {
        this.functionname = functionname;
        this.location_id = location_id;

        view.changeToNewFunctionName(functionname);

        getLocationList();
    }

    public void goProductMainType(String sLocationID){ view.goProductMainType(sLocationID); }

    private void getLocationList() {
        String tmpfunctionname = functionname;
        String sKilometer = "";

        switch (functionname){
            case "AppLocation1":
                tmpfunctionname = "AppOftenLocation";
                sKilometer = "0";
                break;
            case "AppLocation2":
                tmpfunctionname = "AppLocation";
                sKilometer = "5";
                break;
            case "AppLocation3":
                tmpfunctionname = "AppLocation";
                sKilometer = "0";
                break;
        }

        String sCustomerID = repositoryManager.getCustomerID();

        //  先取得該商家的全門市清單
        String finalTmpfunctionname = tmpfunctionname;
        String finalSKilometer = sKilometer;
        repositoryManager.callGetLocationApi("AppLocation", sCustomerID, "", "0", "", new BaseContract.ValueCallback<List<Store>>() {
            @Override
            public void onValueCallback(int task, List<Store> type) {
                boolean isOnlineShopping = type.size() > 0 ? true : false;
                view.setOnlineShoppingFlag(isOnlineShopping, type);

                repositoryManager.callGetLocationApi(finalTmpfunctionname, sCustomerID, "", finalSKilometer, "", new BaseContract.ValueCallback<List<Store>>() {
                    @Override
                    public void onValueCallback(int task, List<Store> type) {
                        view.setLocationList(type);
                    }
                });
            }
        });
    }

    @Override
    public void setStoreOften(String location_id, String uid) {
        if(getUserLogin()) {
            repositoryManager.callSetLocationOftenApi(location_id, uid, new BaseContract.ValueCallback<Boolean>() {
                @Override
                public void onValueCallback(int task, Boolean bSuccess) {
                    getLocationList();
                }
            });
        }
    }

    public boolean getUserLogin(){ return repositoryManager.getUserLogin(); }

    public String getSourceActive() { return repositoryManager.getSourceActive(); }

    public String getFragmentLocation() { return repositoryManager.getFragmentLocation(); }

    public void saveSourceActive(String sSourceActive) { repositoryManager.saveSourceActive(sSourceActive); }

    public void saveFragmentMainType(String sLocationID, String IsETicket) { repositoryManager.saveFragmentMainType(sLocationID, IsETicket); }

}
