package com.hamels.daybydayegg.Order.View;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamels.daybydayegg.Utils.CustomBottomNavigationView;
import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.Order.Adapter.OrderListAdapter;
import com.hamels.daybydayegg.Order.Contract.OrderListContract;
import com.hamels.daybydayegg.Order.OrderObserver;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Order;

import java.util.List;
import java.util.Objects;

import static com.hamels.daybydayegg.Constant.Constant.ORDER_STATUS_WAITING_PAY;
import static com.hamels.daybydayegg.Order.View.ReturnListFragment.STATUS_EXCHANGE;
import static com.hamels.daybydayegg.Order.View.ReturnListFragment.STATUS_RETURN;

public class OrderListFragment extends BaseFragment implements OrderListContract.View, View.OnClickListener {
    public static final String TAG = OrderListFragment.class.getSimpleName();
    public static final int TASK_RETURN = 0;
    public static final int TASK_EXCHANGE = 1;

    private int status = ORDER_STATUS_WAITING_PAY;
    private OrderObserver orderObserver;

    private Group noOrderGroup;
    private TextView tvSend;
    private RecyclerView recyclerView;
    private OrderListAdapter orderListAdapter;

    public static OrderListFragment getInstance(int status) {
        OrderListFragment orderListFragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, status);
        orderListFragment.setArguments(bundle);

        return orderListFragment;
    }

    public OrderListFragment() {
        orderObserver = new OrderObserver(new BaseContract.ValueCallback<List<Order>>() {
            @Override
            public void onValueCallback(int task, List<Order> orders) {
                noOrderGroup.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                orderListAdapter.setOrders(orders);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLifecycle().addObserver(orderObserver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getLifecycle().removeObserver(orderObserver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvSend = view.findViewById(R.id.tv_send);
        tvSend.setOnClickListener(this);
        noOrderGroup = view.findViewById(R.id.no_order_group);
        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);

        if (getArguments() != null) {
            status = getArguments().getInt(TAG, 0);
        }

        orderListAdapter = new OrderListAdapter(status, new BaseContract.ValueCallback<Order>() {
            @Override
            public void onValueCallback(int task, Order order) {
                switch (task) {
                    case TASK_RETURN:
                        ((TransRecordFragment) requireParentFragment()).addReturnFragment(order, STATUS_RETURN);
                        break;
                    case TASK_EXCHANGE:
                        ((TransRecordFragment) requireParentFragment()).addReturnFragment(order, STATUS_EXCHANGE);
                        break;
                }
            }
        });
        recyclerView.setAdapter(orderListAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_send){
            CustomBottomNavigationView bottomNavigationView = ((MainActivity) getActivity()).getBottomNavigationView();
            if (bottomNavigationView != null) {
                MenuItem menuItem = bottomNavigationView.getMenu().getItem(1);
                bottomNavigationView.setIconScaleType(menuItem);
                //bottomNavigationView.setCurrentItem(1);
            }
        }
    }

    @Override
    public void setList(List<Order> orders) {
        orderObserver.setOrders(orders);
    }
}
