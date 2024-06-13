package com.hamels.daybydayegg.Product.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Product.Contract.ProductMainTypeContract;
import com.hamels.daybydayegg.Repository.Model.ProductMainType;
import com.hamels.daybydayegg.Repository.Model.Store;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;


public class ProductMainTypePresenter extends BasePresenter<ProductMainTypeContract.View> implements ProductMainTypeContract.Presenter {
    public static final String TAG = ProductMainTypePresenter.class.getSimpleName();

    public ProductMainTypePresenter(ProductMainTypeContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getProductMainTypeList(String location_id) {
        String sCustomerID = repositoryManager.getCustomerID();

        String isETicket = getFragmentMainType("ISETICKET");
        if(isETicket.equals("Y")) {
            //  先取得該商家的門市總店
            repositoryManager.callGetLocationApi("AppLocation", sCustomerID, "", "0", "Y", new BaseContract.ValueCallback<List<Store>>() {
                @Override
                public void onValueCallback(int task, List<Store> type) {
                    if(type.get(0).getHeadLocationFlag().equals("Y")){
                        //  總店判斷是否有 線上購物
                        if(type.get(0).getOrderSource().indexOf("1") != -1){
                            callGetProductMainTypeListApi(location_id, sCustomerID, isETicket);
                        }else{
                            //  無線上購物功能
                            view.setOnlineShoppingFlag(false);
                        }
                    }
                }
            });
        }else{
            callGetProductMainTypeListApi(location_id, sCustomerID, isETicket);
        }
    }

    public void callGetProductMainTypeListApi(String location_id, String sCustomerID, String sETicket){
        view.setOnlineShoppingFlag(true);

        repositoryManager.callGetProductMainTypeListApi(location_id, sCustomerID, sETicket, new BaseContract.ValueCallback<List<ProductMainType>>() {
            @Override
            public void onValueCallback(int task, List<ProductMainType> type) {
                view.setProductMainTypeList(type);
            }
        });
    }

    @Override
    public void getProductList(int product_type_main_id) { view.getProductList(product_type_main_id); }

    public boolean getUserLogin(){ return repositoryManager.getUserLogin(); }

    public String getApiUrl() { return repositoryManager.getApiUrl(); }

    public String getFragmentMainType(String sParam) { return repositoryManager.getFragmentMainType(sParam); }
}
