package com.hamels.daybydayegg.Business.Presenter;

import android.util.Log;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Business.Contract.BusinessProductMerchantContract;
import com.hamels.daybydayegg.Repository.Model.Merchant;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;


public class BusinessProductMerchantPresenter extends BasePresenter<BusinessProductMerchantContract.View> implements BusinessProductMerchantContract.Presenter {
    public static final String TAG = BusinessProductMerchantPresenter.class.getSimpleName();

    public BusinessProductMerchantPresenter(BusinessProductMerchantContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMerchantList() {
        repositoryManager.callGetMerchantListApi(new BaseContract.ValueCallback<List<Merchant>>() {
            @Override
            public void onValueCallback(int task, List<Merchant> type) {
                for(Merchant merchant : type){
                    Log.e(TAG,merchant.getLocation_name());
                }
                view.setMerchantlList(type);
            }
        });
    }

    @Override
    public void getProductListByID(int product_type_main_id) {
        view.goPageProductList(product_type_main_id);
    }

    @Override
    public void getProductDetailByID(int product_ID) {
        view.goPageProductDetail(product_ID);
    }

    @Override
    public void getProductList(String location_id,String sort,String product_type_main_id, String product_type_id, String product_name , String business_sale_id, String e_ticket) {
        repositoryManager.callGetProductListApi(location_id,business_sale_id , sort ,product_type_main_id , product_type_id ,product_name , e_ticket,  new BaseContract.ValueCallback<List<Product>>() {
            @Override
            public void onValueCallback(int task, List<Product> type) {
                view.setProductList(type);
            }
        });
    }
}
