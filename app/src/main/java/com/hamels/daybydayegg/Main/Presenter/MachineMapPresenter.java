package com.hamels.daybydayegg.Main.Presenter;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Main.Contract.MachineContract;
import com.hamels.daybydayegg.Main.Contract.MachineMapContract;
import com.hamels.daybydayegg.Repository.Model.Machine;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

public class MachineMapPresenter extends BasePresenter<MachineMapContract.View> implements MachineMapContract.Presenter {
    public static final String TAG = MachineMapPresenter.class.getSimpleName();

    public MachineMapPresenter(MachineMapContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    public void getMachineList(boolean isOne) {
        String sCustomerID = repositoryManager.getCustomerID();
        //  先取得該商家的全門市清單
        repositoryManager.callGetMachineApi("All", sCustomerID, new BaseContract.ValueCallback<List<Machine>>() {
            @Override
            public void onValueCallback(int task, List<Machine> type) {
                view.setMachineList(type, isOne);
            }
        });
    }

    public void setStoreOften(ConstraintLayout btnOften, String machine_id, String uid) {
        if(getUserLogin()) {
            repositoryManager.callSetMachineOftenApi(machine_id, uid, new BaseContract.ValueCallback<Boolean>() {
                @Override
                public void onValueCallback(int task, Boolean bSuccess) {
                    view.openBtn(btnOften, true);
                    getMachineList(false);
                }
            });
        }
    }

    public boolean getUserLogin(){ return repositoryManager.getUserLogin(); }
}
