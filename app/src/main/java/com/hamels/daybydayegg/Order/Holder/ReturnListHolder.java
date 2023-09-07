package com.hamels.daybydayegg.Order.Holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.OrderProduct;

public class ReturnListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static final int TASK_ADD_ORDER = 0;
    public static final int TASK_REMOVE_ORDER = 1;

    public CheckBox cbItem;
    public ImageView imgItem;
    public TextView tvItemNo, tvItemName, tvItemStyle, tvItemPrice, tvItemTotalPrice;

    private OrderProduct orderProduct;
    private BaseContract.ValueCallback<OrderProduct> valueCallback;

    public ReturnListHolder(@NonNull View itemView) {
        super(itemView);
        cbItem = itemView.findViewById(R.id.cb_item);
        cbItem.setOnClickListener(this);
        cbItem.setOnCheckedChangeListener(null);

        imgItem = itemView.findViewById(R.id.img_item);
        tvItemNo = itemView.findViewById(R.id.tv_item_no);
        tvItemName = itemView.findViewById(R.id.tv_item_name);
        tvItemStyle = itemView.findViewById(R.id.tv_item_style);
        tvItemPrice = itemView.findViewById(R.id.tv_item_price);
        tvItemTotalPrice = itemView.findViewById(R.id.tv_item_total_price);
    }

    public void setProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;

        String sPictureUrl = orderProduct.getProduct().getImageSrc().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : orderProduct.getProduct().getImageSrc();
        Glide.with(itemView).load(sPictureUrl).into(imgItem);
        tvItemNo.setText(String.format(itemView.getContext().getString(R.string.item_no), orderProduct.getProductId()));
        tvItemName.setText(orderProduct.getProductColor().getName());
        tvItemStyle.setText("");
        tvItemPrice.setText("$" + orderProduct.getProductOurPrice());
        tvItemTotalPrice.setText(String.format(itemView.getContext().getString(R.string.item_total_price), (orderProduct.getAmount() * orderProduct.getProductOurPrice())));
    }

    public void addToReturnListCallback(BaseContract.ValueCallback<OrderProduct> valueCallback) {
        this.valueCallback = valueCallback;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cb_item){
            valueCallback.onValueCallback(cbItem.isChecked() ? TASK_ADD_ORDER : TASK_REMOVE_ORDER, orderProduct);
        }
    }
}
