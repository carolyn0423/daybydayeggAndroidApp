package com.hamels.daybydayegg.Order.Adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Order.Holder.ReturnListHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.OrderProduct;

import java.util.ArrayList;
import java.util.List;

import static com.hamels.daybydayegg.Order.Holder.ReturnListHolder.TASK_ADD_ORDER;
import static com.hamels.daybydayegg.Order.Holder.ReturnListHolder.TASK_REMOVE_ORDER;

public class ReturnListAdapter extends BaseAdapter<ReturnListHolder> {
    public static final String TAG = ReturnListAdapter.class.getSimpleName();

    private List<OrderProduct> products = new ArrayList<>();
    private List<OrderProduct> returnList = new ArrayList<>();

    public ReturnListAdapter() {
        products.clear();
        returnList.clear();
    }

    @NonNull
    @Override
    public ReturnListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_exchange_list, viewGroup, false);
        return new ReturnListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnListHolder returnListHolder, int position) {
        returnListHolder.setProduct(products.get(position));
        returnListHolder.addToReturnListCallback(new BaseContract.ValueCallback<OrderProduct>() {
            @Override
            public void onValueCallback(int task, OrderProduct product) {
                switch (task){
                    case TASK_ADD_ORDER:
                        returnList.add(product);
                        break;
                    case TASK_REMOVE_ORDER:
                        returnList.remove(product);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public List<OrderProduct> getSelectProducts() {
        return returnList;
    }
}
