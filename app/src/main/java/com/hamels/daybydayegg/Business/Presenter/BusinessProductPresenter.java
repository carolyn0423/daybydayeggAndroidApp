package com.hamels.daybydayegg.Business.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Business.Contract.BusinessProductContract;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Repository.Model.ProductType;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;


public class BusinessProductPresenter extends BasePresenter<BusinessProductContract.View> implements BusinessProductContract.Presenter {
    public static final String TAG = BusinessProductPresenter.class.getSimpleName();

    public BusinessProductPresenter(BusinessProductContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getTypeList(int store_id) {
        repositoryManager.callGetProductTypeListApi(store_id ,  new BaseContract.ValueCallback<List<ProductType>>() {
            @Override
            public void onValueCallback(int task, List<ProductType> type) {
                view.setProductTypeList(type);

            }
        });
    }

    @Override
    public void getProductDetailByID(int product_ID) {
        view.goPageProductDetail(product_ID);
    }

    @Override
    public void getProductList(String location_id,String sort,String product_type_main_id, String product_type_id , String product_name , String business_sale_id, String e_ticket) {
        repositoryManager.callGetProductListApi(location_id,business_sale_id,sort , product_type_main_id , product_type_id ,product_name , e_ticket, new BaseContract.ValueCallback<List<Product>>() {
            @Override
            public void onValueCallback(int task, List<Product> type) {
                view.setProductList(type);

            }
        });
    }
}
