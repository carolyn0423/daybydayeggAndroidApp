package com.hamels.daybydayegg.DrawLots.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.DrawLots;

import java.util.List;

public interface DrawLotsContract {
    interface View extends BaseContract.View {
        void setLotList(List<DrawLots> drawLotsList);

        void goLotDetail(int lot_id);

    }

    interface Presenter extends BaseContract.Presenter {
        void getLotList();

        void getLotDetailByID(int lot_id);
    }
}
