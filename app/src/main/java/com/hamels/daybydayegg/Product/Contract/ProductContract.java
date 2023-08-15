package com.hamels.daybydayegg.Product.Contract;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Repository.Model.ProductType;

import java.util.List;

public interface ProductContract {
    interface View extends BaseContract.View {
        void setProductTypeList(List<ProductType> productTypeList);

        void goPageProductDetail(int product_id);

        void setProductList(List<Product> productList);


    }

    interface Presenter extends BaseContract.Presenter {
        void getTypeList(int product_type_main_id);

        void getProductDetailByID(int product_ID);

        void getProductList(String location_id,String sort,String product_type_main_id , String product_type_id , String product_name, String e_ticket);
    }
}
