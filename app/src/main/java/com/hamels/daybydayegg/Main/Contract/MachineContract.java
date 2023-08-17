package com.hamels.daybydayegg.Main.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Machine;
import com.hamels.daybydayegg.Repository.Model.Store;

import java.util.List;

public interface MachineContract {
    interface View extends BaseContract.View {
        void setMachineList(List<Machine> type);
    }

    interface Presenter extends BaseContract.Presenter {
        void setFunctionname(String functionname);

        void setStoreOften(String machine_id, String uid);

        boolean getUserLogin();
    }
}
