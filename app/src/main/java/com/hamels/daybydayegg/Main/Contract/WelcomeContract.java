package com.hamels.daybydayegg.Main.Contract;

import com.hamels.daybydayegg.Base.BaseContract;

public interface WelcomeContract {
    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {
        Boolean getUserInfo();

        String getSourceActive();
    }
}
