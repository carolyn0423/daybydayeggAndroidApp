package com.hamels.daybydayegg.Main.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Main.Contract.MachineContract;
import com.hamels.daybydayegg.Repository.Model.Machine;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

public class MachinePresenter extends BasePresenter<MachineContract.View> implements MachineContract.Presenter {
    public static final String TAG = MachinePresenter.class.getSimpleName();
    private String sFunctionname = "All";

    public MachinePresenter(MachineContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    public void setFunctionname(String functionname) {
        sFunctionname = functionname;
        getMachineList();
    }

    private void getMachineList() {
        String sCustomerID = repositoryManager.getCustomerID();
        //  先取得該商家的全門市清單
        repositoryManager.callGetMachineApi("All", sCustomerID, "", new BaseContract.ValueCallback<List<Machine>>() {
            @Override
            public void onValueCallback(int task, List<Machine> type) {
                repositoryManager.callGetMachineApi(sFunctionname, sCustomerID, "", new BaseContract.ValueCallback<List<Machine>>() {
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
                    getMachineList();
                }
            });
        }
    }

    public boolean getUserLogin(){ return repositoryManager.getUserLogin(); }

}
