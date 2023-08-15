package com.hamels.daybydayegg.Order.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Order.Contract.TransRecordContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Order;
import com.hamels.daybydayegg.Repository.RepositoryManager;
import com.hamels.daybydayegg.EOrderApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransRecordPresenter extends BasePresenter<TransRecordContract.View> implements TransRecordContract.Presenter {
    public static final String TAG = TransRecordPresenter.class.getSimpleName();
    private Map<String, List<Order>> ordersMap = new HashMap<>();

    public TransRecordPresenter(TransRecordContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getOrders() {
        repositoryManager.callGetOrdersApi(new BaseContract.ValueCallback<List<Order>>() {
            @Override
            public void onValueCallback(int task, List<Order> type) {
                generateMap(type);
                view.onOrderListReadyCallback();
            }
        });
    }

    @Override
    public List<Order> getStatusOrders(int status) {
        if (ordersMap != null) {
            switch (status) {
                case 2:
                case 4:
                case 5:
                    return getFilterFinishOrders(ordersMap.get(Order.getOrderStatusByType(status)), status);
                default:
                    return ordersMap.get(Order.getOrderStatusByType(status));

            }
        } else {
            return null;
        }
    }

    private void generateMap(List<Order> list) {
        ordersMap.clear();
        for (Order order : list) {
            try {
                ordersMap.get(order.getStatus()).add(order);
            } catch (NullPointerException e) {
                List<Order> orders = new ArrayList<>();
                orders.add(order);
                ordersMap.put(order.getStatus(), orders);
            }
        }
    }

    private List<Order> getFilterFinishOrders(List<Order> finishOrder, int status) {
        List<Order> orders = new ArrayList<>();
        if (finishOrder == null) {
            return null;
        }

        switch (status) {
            case 2:
                for (Order order : finishOrder) {
                    if (!order.isAllProductReturn()) {
                        Order copyOrder = order.copy(false);
                        if (copyOrder.getOrderProduct().size() > 0) {
                            orders.add(copyOrder);
                        }
                    }
                }
                break;
            case 4:
                for (Order order : finishOrder) {
                    if (order.getReturnApplyData() != null && order.getReturnApplyData().getType().equals(EOrderApplication.getInstance().getString(R.string.order_return))) {
                        Order copyOrder = order.copy(true);
                        if (copyOrder.getOrderProduct().size() > 0) {
                            orders.add(copyOrder);
                        }
                    }
                }
                break;
            case 5:
                for (Order order : finishOrder) {
                    if (order.getReturnApplyData() != null && order.getReturnApplyData().getType().equals(EOrderApplication.getInstance().getString(R.string.order_exchange))) {
                        Order copyOrder = order.copy(true);
                        if (copyOrder.getOrderProduct().size() > 0) {
                            orders.add(copyOrder);
                        }
                    }
                }
                break;
        }
        return orders;
    }
}
