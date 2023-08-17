package com.hamels.daybydayegg.Main.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Main.Contract.MachineContract;
import com.hamels.daybydayegg.Repository.Model.Machine;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

public class MachinePresenter extends BasePresenter<MachineContract.View> implements MachineContract.Presenter {
    public static final String TAG = MachinePresenter.class.getSimpleName();

    public static final String FUNCTIONNAME_1 = "AppLocation1";
    public static final String FUNCTIONNAME_2 = "AppLocation2";
    public static final String FUNCTIONNAME_3 = "AppLocation3";
    public static final String FUNCTIONNAME_4 = "AppLocation4";

    private String functionname;

    public MachinePresenter(MachineContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    public void setFunctionname(String functionname) {
        this.functionname = functionname;

        view.changeToNewFunctionName(functionname);

        getMachineList(functionname);
    }

    private void getMachineList(String functionname) {
        String tmpfunctionname = functionname;
        String sKilometer = "";

        String sCustomerID = repositoryManager.getCustomerID();

        //  先取得該商家的全門市清單
        String finalTmpfunctionname = tmpfunctionname;
        String finalSKilometer = sKilometer;
        repositoryManager.callGetMachineApi(finalTmpfunctionname, functionname, sCustomerID, "0", "", new BaseContract.ValueCallback<List<Machine>>() {
            @Override
            public void onValueCallback(int task, List<Machine> type) {
                repositoryManager.callGetMachineApi(finalTmpfunctionname, functionname, sCustomerID, finalSKilometer, "", new BaseContract.ValueCallback<List<Machine>>() {
                    @Override
                    public void onValueCallback(int task, List<Machine> type) {
                        view.setMachineList(type);
                    }
                });
            }
        });
    }

    @Override
    public void setStoreOften(String machine_id, String uid) {
        if(!repositoryManager.getUserID().equals("")) {
            repositoryManager.callSetMachineOftenApi(machine_id, uid, new BaseContract.ValueCallback<Boolean>() {
                @Override
                public void onValueCallback(int task, Boolean bSuccess) {
                    getMachineList(functionname);
                }
            });
        }
    }

    public boolean getUserLogin(){ return repositoryManager.getUserLogin(); }

}
