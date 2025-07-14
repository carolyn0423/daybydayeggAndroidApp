package com.hamels.daybydayegg.Order.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Order.View.OrderListFragment;
import com.hamels.daybydayegg.Order.Holder.OrderListHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends BaseAdapter<OrderListHolder> {
    public static final String TAG = OrderListAdapter.class.getSimpleName();

    private int status;
    private List<Order> orders = new ArrayList<>();
    private BaseContract.ValueCallback<Order> buttonCallback;

    public OrderListAdapter(int status, BaseContract.ValueCallback<Order> buttonCallback) {
        this.status = status;
        this.buttonCallback = buttonCallback;
    }

    @NonNull
    @Override
    public OrderListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_list, viewGroup, false);
        return new OrderListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListHolder orderListHolder, int position) {
        orderListHolder.setOrder(orders.get(position), status);

        orderListHolder.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = orderListHolder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                buttonCallback.onValueCallback(OrderListFragment.TASK_RETURN, orders.get(pos));
            }
        });

        orderListHolder.btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = orderListHolder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                buttonCallback.onValueCallback(OrderListFragment.TASK_EXCHANGE, orders.get(pos));
            }
        });
    }


//    @Override
//    public void onBindViewHolder(@NonNull final OrderListHolder orderListHolder, final int position) {
//        orderListHolder.setOrder(orders.get(position), status);
//        orderListHolder.btnReturn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonCallback.onValueCallback(OrderListFragment.TASK_RETURN, orders.get(position));
//            }
//        });
//
//        orderListHolder.btnExchange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonCallback.onValueCallback(OrderListFragment.TASK_EXCHANGE, orders.get(position));
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }
}
