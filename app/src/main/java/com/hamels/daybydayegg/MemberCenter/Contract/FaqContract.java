package com.hamels.daybydayegg.MemberCenter.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Faq;

public interface FaqContract {
    interface View extends BaseContract.View {
        void setFaqData(Faq faq);
    }

    interface Presenter extends BaseContract.Presenter {
        void getFaqData(String faq_id);
    }
}
