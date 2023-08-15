package com.hamels.daybydayegg.Order.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Order.Contract.ReturnInputContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.OrderProduct;
import com.hamels.daybydayegg.Repository.RepositoryManager;
import com.hamels.daybydayegg.Utils.FormatUtils;

import java.util.ArrayList;

import static com.hamels.daybydayegg.Constant.ApiConstant.TASK_POST_RETURN_ORDER_SUCCESS;

public class ReturnInputPresenter extends BasePresenter<ReturnInputContract.View> implements ReturnInputContract.Presenter {
    public static final String TAG = ReturnInputPresenter.class.getSimpleName();

    public ReturnInputPresenter(ReturnInputContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void checkInputAndSend(ArrayList<OrderProduct> products, int returnType, String name, String address, String phone, String email) {
        if (name.isEmpty()) {
            view.showAlert(R.string.name_empty);
            return;
        }
        if (address.isEmpty()) {
            view.showAlert(R.string.address_empty);
            return;
        }
        if (phone.isEmpty() || !FormatUtils.isCellphoneFormat(phone)) {
            view.showAlert(R.string.phone_format_error);
            return;
        }
        if (email.isEmpty() || !FormatUtils.isEmailFormat(email)) {
            view.showAlert(R.string.email_format_error);
            return;
        }

        callReturnOrdersApi(products, returnType, name, address, phone, email);
    }

    private void callReturnOrdersApi(ArrayList<OrderProduct> products, int returnType, String name, String address, String phone, String email) {
        repositoryManager.callReturnProductsApi(products, name, address, phone, email, returnType, new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                view.showReturnResult(task == TASK_POST_RETURN_ORDER_SUCCESS, type);
            }
        });
    }
}
