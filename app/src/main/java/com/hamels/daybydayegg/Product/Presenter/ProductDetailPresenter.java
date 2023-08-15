package com.hamels.daybydayegg.Product.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Product.Contract.ProductDetailContract;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.List;

import static com.hamels.daybydayegg.Constant.Constant.REQUEST_PRODUCT_DETAIL;


public class ProductDetailPresenter extends BasePresenter<ProductDetailContract.View> implements ProductDetailContract.Presenter {
    public static final String TAG = ProductDetailPresenter.class.getSimpleName();

    public ProductDetailPresenter(ProductDetailContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getProductDetailByID(String product_ID, String e_ticket) {
        repositoryManager.callGetProductDetailApi("", product_ID, e_ticket, new BaseContract.ValueCallback<List<Product>>() {
            @Override
            public void onValueCallback(int task, List<Product> type) {
                view.setProductDetail(type);
            }
        });
    }

    @Override
    public void addShoppingCart(String product_id, String spec_id, String location_id, String spec_qty, String stock, String quantity, String order_type, String conf_list, int conf_quantity) {
        if (repositoryManager.getUserLogin()) {
            if (spec_id.equals("0") || spec_id.isEmpty()) {
                view.showErrorAlert("請填寫商品規格");
            } else if (quantity.equals("0") || quantity.isEmpty()) {
                view.showErrorAlert("請填寫商品數量");
            } else if ((stock.equals("0") || Integer.parseInt(quantity) > Integer.parseInt(stock))) {
                view.showErrorAlert("此尺寸庫存量不足 " + "目前庫存量" + Integer.parseInt(stock));
            } else if(Integer.parseInt(quantity) < conf_quantity){
                view.showErrorAlert("購買數量不可小於客製份數");
            } else {
                repositoryManager.callAddShoppingCattApi("0", "G", product_id, spec_id, location_id, quantity, order_type, conf_list,
                        new BaseContract.ValueCallback<String>() {
                            @Override
                            public void onValueCallback(int task, String type) {
                                view.showAddCartSuccess("加入購物車成功");
                            }
                        });
            }
        } else {
            view.intentToLogin(REQUEST_PRODUCT_DETAIL);
        }
    }
}