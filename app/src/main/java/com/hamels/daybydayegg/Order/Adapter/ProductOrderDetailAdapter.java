package com.hamels.daybydayegg.Order.Adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Order.Holder.ProductDetailHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.OrderProduct;

import java.util.ArrayList;
import java.util.List;

public class ProductOrderDetailAdapter extends BaseAdapter<ProductDetailHolder> {
    public static final String TAG = ProductOrderDetailAdapter.class.getSimpleName();

    private List<OrderProduct> products = new ArrayList<>();

    @NonNull
    @Override
    public ProductDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_product_detail, viewGroup, false);
        return new ProductDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailHolder productDetailHolder, int position) {
        productDetailHolder.setProduct(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
        notifyDataSetChanged();
    }
}
