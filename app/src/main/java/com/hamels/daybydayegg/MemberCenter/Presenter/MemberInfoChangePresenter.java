package com.hamels.daybydayegg.MemberCenter.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.MemberCenter.Contract.MemberInfoChangeContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.User;
import com.hamels.daybydayegg.Repository.RepositoryManager;
import com.hamels.daybydayegg.Utils.FormatUtils;

import java.util.List;

public class MemberInfoChangePresenter extends BasePresenter<MemberInfoChangeContract.View> implements MemberInfoChangeContract.Presenter {
    public static final String TAG = MemberInfoChangePresenter.class.getSimpleName();

    public MemberInfoChangePresenter(MemberInfoChangeContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMemberInfo() {
        repositoryManager.callGetMemberInfoApi(repositoryManager.getUserID(), new BaseContract.ValueCallback<User>() {
            @Override
            public void onValueCallback(int task, User user) {
                view.setUser(user);
            }
        });
    }

    public void getPropertyData(){
        repositoryManager.callGetPropertyDataApi( new BaseContract.ValueCallback<List<Address>>() {
            @Override
            public void onValueCallback(int task, List<Address> type) {
                view.setPropertyCode(type);
//                repositoryManager.callGetMemberInfoApi(repositoryManager.getUserID(), new BaseContract.ValueCallback<User>() {
//                    @Override
//                    public void onValueCallback(int task, User user) {
//                        view.setUser(user);
//                    }
//                });
            }
        });
    }

    @Override
    public void updateMember(String customer_id, String city_code , String area_code, String address,String email, String birth, String carrier_no) {

        if(city_code.equals("請選擇城市")){
            view.showAlert(R.string.register_city);
            return;
        }

        if(area_code.equals("請選擇地區")){
            view.showAlert(R.string.register_area);
            return;
        }

        if (email.isEmpty()) {
            view.showAlert(R.string.email_empty);
            return;
        }

        if (address.isEmpty()) {
            view.showAlert(R.string.register_address);
            return;
        }

        if (!FormatUtils.isEmailFormat(email)) {
            view.showAlert(R.string.email_format_error);
            return;
        }

//        if (gender == -1) {
//            view.showAlert(R.string.gender_empty);
//            return;
//        }

        repositoryManager.callUpdateMemberInfoApi(customer_id, city_code,area_code,address,email,birth, carrier_no, new BaseContract.ValueCallback<String>() {
//            repositoryManager.callUpdateMemberInfoApi(city_code,area_code,address,email, gender == R.id.radio_male ? "1" : "0", new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String message) {
                view.showAlert(message);
                view.setBirthDayModifiedOrNot(message);
            }
        });
    }
}
