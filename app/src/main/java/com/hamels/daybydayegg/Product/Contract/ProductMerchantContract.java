package com.hamels.daybydayegg.Product.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Merchant;
import com.hamels.daybydayegg.Repository.Model.Product;

import java.util.List;

public interface ProductMerchantContract {
    interface View extends BaseContract.View {
        void setMerchantlList(List<Merchant> merchantlList);

        void goPageProductMainTypeList(int location_id);

        void goPageProductDetail(int product_id);

        void setProductList(List<Product> productList);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMerchantList();

        void getProductMainTypeListByID(int location_id);

        void getProductDetailByID(int product_ID);

        void getProductList(String location_id,String sort ,String product_type_main_id , String product_type_id , String product_name, String e_ticket);

        void saveFragmentMainType(String sLocationID, String IsETicket);

        void saveFragmentLocation(String sLocationID);
    }
}
