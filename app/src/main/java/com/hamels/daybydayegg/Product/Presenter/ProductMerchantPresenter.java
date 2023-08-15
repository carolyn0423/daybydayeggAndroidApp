package com.hamels.daybydayegg.Product.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Product.Contract.ProductMerchantContract;
import com.hamels.daybydayegg.Repository.Model.Merchant;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;


public class ProductMerchantPresenter extends BasePresenter<ProductMerchantContract.View> implements ProductMerchantContract.Presenter {
    public static final String TAG = ProductMerchantPresenter.class.getSimpleName();

    public ProductMerchantPresenter(ProductMerchantContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMerchantList() {
        repositoryManager.callGetMerchantListApi(new BaseContract.ValueCallback<List<Merchant>>() {
            @Override
            public void onValueCallback(int task, List<Merchant> type) {
                view.setMerchantlList(type);
            }
        });
    }
    @Override
    public void getProductMainTypeListByID(int location_id) {
        view.goPageProductMainTypeList(location_id);
    }

    @Override
    public void getProductDetailByID(int product_ID) {
        view.goPageProductDetail(product_ID);
    }

    @Override
    public void getProductList(String location_id,String sort,String product_type_main_id, String product_type_id, String product_name, String e_ticket) {
        repositoryManager.callGetProductListApi(location_id,"",sort ,product_type_main_id , product_type_id ,product_name , e_ticket,  new BaseContract.ValueCallback<List<Product>>() {
            @Override
            public void onValueCallback(int task, List<Product> type) {
                view.setProductList(type);
            }
        });
    }

    public void saveFragmentMainType(String sLocationID, String IsETicket) { repositoryManager.saveFragmentMainType(sLocationID, IsETicket); }

    public void saveFragmentLocation(String sLocationID) { repositoryManager.saveFragmentLocation(sLocationID); }
}
