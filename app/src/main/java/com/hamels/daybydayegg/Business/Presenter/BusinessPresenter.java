package com.hamels.daybydayegg.Business.Presenter;
import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Business.Contract.BusinessContract;
import com.hamels.daybydayegg.Repository.RepositoryManager;
import static com.hamels.daybydayegg.Constant.ApiConstant.TASK_POST_GET_BUSINESS_CODE;

public class BusinessPresenter extends BasePresenter<BusinessContract.View> implements BusinessContract.Presenter {
    public static final String TAG = BusinessPresenter.class.getSimpleName();

    public BusinessPresenter(BusinessContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void checkBusinessCode(String sale_password) {
        repositoryManager.callGetBusinessCodeApi(sale_password ,  new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                if(task == TASK_POST_GET_BUSINESS_CODE){
                    repositoryManager.saveBusinessSaleID(type);
                    view.goBusinessProduct(type);
                }
                else{
                    view.ErrorAlert(type);
                }
            }
        });
    }
}
