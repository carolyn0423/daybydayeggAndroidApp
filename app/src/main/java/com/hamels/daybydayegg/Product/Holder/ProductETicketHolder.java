package com.hamels.daybydayegg.Product.Holder;

import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Product.View.ProductFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Product;

import java.text.DecimalFormat;

public class ProductETicketHolder extends RecyclerView.ViewHolder {
    public static final String TAG = ProductETicketHolder.class.getSimpleName();
    public ImageView img_product;
    public TextView tv_product_name, tv_sale_price, tv_price;
    public ConstraintLayout layout;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#,###");

    public ProductETicketHolder(@NonNull View itemView) {
        super(itemView);
        img_product = itemView.findViewById(R.id.img_product);
        tv_product_name = itemView.findViewById(R.id.tv_product_name);
        tv_sale_price = itemView.findViewById(R.id.tv_sale_price);
        tv_price = itemView.findViewById(R.id.tv_price);
        layout = itemView.findViewById(R.id.layout_constraint);
    }

    public void setImg_product(Product product) {
        Glide.with(ProductFragment.getInstance()).load(EOrderApplication.sApiUrl + product.getPicture_url()).into(img_product);
        img_product.setTag(R.id.img_product, product.getId());
        layout.setTag(R.id.layout_constraint, product.getId());
        tv_product_name.setText(product.getProduct_name());

        if (1 < product.getspec_cnt()) {
            tv_sale_price.setText("NT$" + mDecimalFormat.format((double) product.getticket_sales_price()) + " 起");

            if (product.getticket_sales_price() != product.getPrice()) {
                tv_price.setVisibility(View.VISIBLE);
                tv_price.setText("NT$" + mDecimalFormat.format((double) product.getPrice()));
                tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tv_price.setVisibility(View.GONE);
            }
        } else {
            tv_sale_price.setText("NT$" + mDecimalFormat.format((double) product.getticket_sales_price()));

            if (product.getticket_sales_price() != product.getPrice()) {
                tv_price.setVisibility(View.VISIBLE);
                tv_price.setText("NT$" + mDecimalFormat.format((double) product.getPrice()));
                tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tv_price.setVisibility(View.GONE);
            }
        }
    }
}