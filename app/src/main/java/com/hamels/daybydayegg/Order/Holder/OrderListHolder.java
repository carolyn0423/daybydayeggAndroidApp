package com.hamels.daybydayegg.Order.Holder;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.daybydayegg.Order.Adapter.ProductOrderDetailAdapter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Order;

import static com.hamels.daybydayegg.Constant.Constant.ORDER_STATUS_TRANS_FINISH;

public class OrderListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private View itemView;

    public ConstraintLayout layoutItem;
    public ImageView imgArrow, imgItem;
    public Button btnExchange, btnReturn;
    public Group groupButton;
    public TextView tvOrderNo, tvOrderTime, tvOrderPrice, tvOrderCount, tvOrderExtra;
    public RecyclerView recyclerView;
    public ProductOrderDetailAdapter orderDetailAdapter;

    private ConstraintSet applySet, resetSet;
    private Order order;

    public OrderListHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;

        layoutItem = itemView.findViewById(R.id.layout_item);
        imgArrow = itemView.findViewById(R.id.img_arrow);
        imgArrow.setOnClickListener(this);
        imgItem = itemView.findViewById(R.id.img_item);
        btnExchange = itemView.findViewById(R.id.btn_order_exchange);
        btnReturn = itemView.findViewById(R.id.btn_order_return);
        groupButton = itemView.findViewById(R.id.group_order_button);
        tvOrderNo = itemView.findViewById(R.id.tv_order_no);
        tvOrderTime = itemView.findViewById(R.id.tv_order_time);
        tvOrderPrice = itemView.findViewById(R.id.tv_order_price);
        tvOrderCount = itemView.findViewById(R.id.tv_order_count);
        tvOrderExtra = itemView.findViewById(R.id.tv_order_extra);
        recyclerView = itemView.findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);

        orderDetailAdapter = new ProductOrderDetailAdapter();
        recyclerView.setAdapter(orderDetailAdapter);

        applySet = new ConstraintSet();
        applySet.clone(layoutItem);

        resetSet = new ConstraintSet();
        resetSet.clone(layoutItem);
    }

    public void setOrder(Order order, int status) {
        this.order = order;
        setOrderInfo(order, status);
    }

    private void setOrderInfo(Order order, int status) {
        setButtonVisible(status == ORDER_STATUS_TRANS_FINISH && order.canReturn());
        isDetailShow(order.showDetail);

        Glide.with(itemView).load(order.getPreviewImageUrl()).into(imgItem);
        tvOrderNo.setText(String.format(itemView.getContext().getString(R.string.order_no), order.getId()));
        tvOrderTime.setText(String.format(itemView.getContext().getString(R.string.order_time), order.getOrderTime()));
        tvOrderPrice.setText(String.format(itemView.getContext().getString(R.string.order_price), order.getRealPayTotal()));
        tvOrderCount.setText(String.format(itemView.getContext().getString(R.string.order_total), order.getOrderProduct().size()));
        tvOrderExtra.setVisibility(order.isEenterpriseOrder() ? View.VISIBLE : View.GONE);
        orderDetailAdapter.setProducts(order.getOrderProduct());

    }

    private void setButtonVisible(boolean visible) {
        groupButton.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    private void isDetailShow(boolean isShow) {
        imgArrow.setImageResource(isShow ? R.drawable.ic_arrow_up_orange : R.drawable.ic_arrow_down_grey);

        if (isShow) {
            showDetailWithAnimation();
        } else {
            hideDetailWithAnimation();
        }
    }

    private void showDetailWithAnimation() {
        applySet.clone(itemView.getContext(), R.layout.item_order_list_show);
        applySet.applyTo(layoutItem);
    }

    private void hideDetailWithAnimation() {
        resetSet.applyTo(layoutItem);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_arrow){
            order.showDetail = !order.showDetail;
            isDetailShow(order.showDetail);
        }
    }
}
