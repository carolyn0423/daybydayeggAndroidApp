package com.hamels.daybydayegg.Main.Contract;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Machine;

import java.util.List;

public interface MachineMapContract {
    interface View extends BaseContract.View {
        void setMachineList(List<Machine> type, boolean isOne);

        void openBtn(ConstraintLayout btnOften, boolean isOpen);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMachineList(boolean isOne);

        void setStoreOften(ConstraintLayout btnOften, String machine_id, String uid);

        boolean getUserLogin();
    }
}
