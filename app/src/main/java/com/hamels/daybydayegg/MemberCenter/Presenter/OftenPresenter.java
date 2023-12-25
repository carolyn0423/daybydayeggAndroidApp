package com.hamels.daybydayegg.MemberCenter.Presenter;

import static com.hamels.daybydayegg.Constant.ApiConstant.TASK_PUT_UPDATE_MEMBER;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.MemberCenter.Contract.OftenContract;
import com.hamels.daybydayegg.Repository.ApiCallback;
import com.hamels.daybydayegg.Repository.ApiRepository.MemberRepository;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.BaseModel;
import com.hamels.daybydayegg.Repository.Model.Machine;
import com.hamels.daybydayegg.Repository.Model.Often;
import com.hamels.daybydayegg.Repository.Model.OftenMobile;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OftenPresenter extends BasePresenter<OftenContract.View> implements OftenContract.Presenter {
    public static final String TAG = OftenPresenter.class.getSimpleName();

    public OftenPresenter(OftenContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    public void getOftenList(String sFunctionName) {
        switch (sFunctionName){
            case "INVOICE":
                repositoryManager.getOftenVatNumber(new BaseContract.ValueCallback<List<Often>>() {
                    @Override
                    public void onValueCallback(int task, List<Often> type) {
                        view.setOftenList(sFunctionName, type);
                    }
                });
                break;
            case "MOBILE":
                HashMap<String, String> oftenMobileMap = new HashMap<>();
                oftenMobileMap = repositoryManager.getOftenMobile();

                List<Often> oftenList = new ArrayList<>();
                // 从您的数据源中获取 OftenMobile 对象并添加到 oftenMobileList 中
                if(oftenMobileMap != null) {
                    for (Map.Entry<String, String> entry : oftenMobileMap.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        Often often = new Often(key, value);
                        oftenList.add(often);
                    }
                }

                view.setOftenList(sFunctionName, oftenList);
                break;
            case "ADDRESS":
                repositoryManager.getOftenPickup(new BaseContract.ValueCallback<List<Often>>() {
                    @Override
                    public void onValueCallback(int task, List<Often> type) {
                        view.setOftenList(sFunctionName, type);
                    }
                });
                break;
        }
    }

    public void getPropertyData(){
        repositoryManager.callGetPropertyDataApi( new BaseContract.ValueCallback<List<Address>>() {
            @Override
            public void onValueCallback(int task, List<Address> type) {
                view.setPropertyCode(type);

            }
        });
    }

    public void GetFunctionSaveDataApi(String sFunctionName, String sField1, String sField2, String sField3, String sField4, String sField5){
        repositoryManager.callGetFunctionSaveDataApi(sFunctionName, sField1, sField2, sField3, sField4, sField5, new BaseContract.ValueCallback<Boolean>() {
            @Override
            public void onValueCallback(int task, Boolean bSuccess) {
                if (bSuccess) {
                    view.showAlert("儲存成功");
                } else {
                    view.showAlert("系統錯誤");
                }
            }
        });
    }

    public void saveOftenMobile(String sMobile, String sNike){
        repositoryManager.saveOftenMobile(sMobile, sNike);
    }
}
