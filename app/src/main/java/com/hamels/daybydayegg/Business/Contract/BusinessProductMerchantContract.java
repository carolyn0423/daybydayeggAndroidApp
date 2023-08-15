package com.hamels.daybydayegg.Business.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Merchant;
import com.hamels.daybydayegg.Repository.Model.Product;

import java.util.List;

public interface BusinessProductMerchantContract {
    interface View extends BaseContract.View {
        void setMerchantlList(List<Merchant> merchantlList);

        void goPageProductList(int product_type_main_id);

        void goPageProductDetail(int product_id);

        void setProductList(List<Product> productList);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMerchantList();

        void getProductListByID(int product_type_main_id);

        void getProductDetailByID(int product_ID);

        void getProductList(String location_id,String sort, String product_type_main_id, String product_type_id, String product_name,String business_sale_id, String e_ticket);
    }
}
