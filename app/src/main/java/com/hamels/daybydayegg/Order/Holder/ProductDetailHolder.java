package com.hamels.daybydayegg.Order.Holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.OrderProduct;

public class ProductDetailHolder extends RecyclerView.ViewHolder {
    private View itemView;

    public TextView tvItemNo, tvItemName, tvItemStyle, tvItemPrice, tvItemCount, tvItemTotalPrice;
    public ImageView imgItem;

    public ProductDetailHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;

        imgItem = itemView.findViewById(R.id.img_item);
        tvItemNo = itemView.findViewById(R.id.tv_item_no);
        tvItemCount = itemView.findViewById(R.id.tv_item_count);
        tvItemName = itemView.findViewById(R.id.tv_item_name);
        tvItemStyle = itemView.findViewById(R.id.tv_item_style);
        tvItemPrice = itemView.findViewById(R.id.tv_item_price);
        tvItemTotalPrice = itemView.findViewById(R.id.tv_item_total_price);
    }

    public void setProduct(OrderProduct orderProduct) {
        Glide.with(itemView).load(orderProduct.getProduct().getImageSrc()).into(imgItem);
        tvItemNo.setText(String.format(itemView.getContext().getString(R.string.item_no), orderProduct.getProductId()));
        tvItemCount.setText("x " + orderProduct.getAmount());
        tvItemName.setText(orderProduct.getProductColor().getName());
        tvItemStyle.setText("");
        tvItemPrice.setText("$" + orderProduct.getProductOurPrice());
        tvItemTotalPrice.setText(String.format(itemView.getContext().getString(R.string.item_total_price), (orderProduct.getAmount() * orderProduct.getProductOurPrice())));
    }
}
