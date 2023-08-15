package com.hamels.daybydayegg.Product.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Product.Contract.ProductContract;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Repository.Model.ProductType;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;


public class ProductPresenter extends BasePresenter<ProductContract.View> implements ProductContract.Presenter {
    public static final String TAG = ProductPresenter.class.getSimpleName();

    public ProductPresenter(ProductContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getTypeList(int product_type_main_id) {
        repositoryManager.callGetProductTypeListApi(product_type_main_id ,  new BaseContract.ValueCallback<List<ProductType>>() {
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
    public void getProductList(String location_id,String sort,String product_type_main_id, String product_type_id , String product_name, String e_ticket) {
       // (final String location_id,final String business_sale_id, final String sort, final String product_type_main_id, final String product_type_id, final String product_name, final String e_ticket, final BaseContract.ValueCallback<List<Product>> valueCallback) {
        repositoryManager.callGetProductListApi(location_id,"",sort , product_type_main_id , product_type_id ,product_name , e_ticket, new BaseContract.ValueCallback<List<Product>>() {
            @Override
            public void onValueCallback(int task, List<Product> type) {
                view.setProductList(type);

            }
        });
    }
}
