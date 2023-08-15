package com.hamels.daybydayegg.MemberCenter.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.PointHistory;
import com.hamels.daybydayegg.Repository.Model.User;

import java.util.List;

public interface MemberPointContract {
    interface View extends BaseContract.View {
        void setPoint(List<PointHistory> point);

        void setPointData(User user);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMemberPoint(String date);

        void getPointData();
    }
}
