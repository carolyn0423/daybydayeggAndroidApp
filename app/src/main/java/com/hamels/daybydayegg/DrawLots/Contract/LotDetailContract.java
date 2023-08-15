package com.hamels.daybydayegg.DrawLots.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.DrawLots;

public interface LotDetailContract {
    interface View extends BaseContract.View {
        void setLotDetail(DrawLots lotDetail);

        void ErrorAlert(int errorMessage);

        void showSuccessMessage(int successMessage);

        void showErrorAlert(String message);

        void showCheckAlert();
    }

    interface Presenter extends BaseContract.Presenter {
        void getLotDetailData(int lot_id);

        void checkIdentity(String lot_type , String is_draw , String cif_code , String prod_id , String spec_name);

        void joinLot(int lot_id ,String  cif_code ,String  prod_id ,String  spec_name );
    }
}
