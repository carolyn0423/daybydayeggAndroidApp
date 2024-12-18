package com.hamels.daybydayegg.Product.Holder;

import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Product.View.ProductFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Utils.WaterMaskUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductETicketHolder extends RecyclerView.ViewHolder {
    public static final String TAG = ProductETicketHolder.class.getSimpleName();
    public ImageView img_product;
    public TextView tv_product_name, tv_sale_price, tv_price, tv_coupon_name, tv_water_mask;
    public ConstraintLayout layout;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#,###");

    public ProductETicketHolder(@NonNull View itemView) {
        super(itemView);
        img_product = itemView.findViewById(R.id.img_product);
        tv_water_mask = itemView.findViewById(R.id.tv_water_mask);
        tv_product_name = itemView.findViewById(R.id.tv_product_name);
        tv_sale_price = itemView.findViewById(R.id.tv_sale_price);
        tv_price = itemView.findViewById(R.id.tv_price);
        tv_coupon_name = itemView.findViewById(R.id.tv_coupon_name);
        layout = itemView.findViewById(R.id.layout_constraint);
    }

    public void setImg_product(Product product) {
        // 应用圆角转换的 RequestOptions
        RequestOptions requestOptions = new RequestOptions()
                .transform(new RoundedCorners(20));

        String sPictureUrl = product.getPicture_url() == null || product.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : product.getPicture_url();
        Glide.with(ProductFragment.getInstance()).load(EOrderApplication.sApiUrl + sPictureUrl).apply(requestOptions).into(img_product);
        if (product.getSoldoutFlag().equals("E") || product.getSoldoutFlag().equals("Y")){
            List<String> labels = new ArrayList<>();
            labels.add("今日完售");
            tv_water_mask.setBackground(new WaterMaskUtils(ProductFragment.getInstance().getContext(),labels,-30,13));
        }
        img_product.setTag(R.id.img_product, product.getId());
        layout.setTag(R.id.layout_constraint, product.getId());
        tv_product_name.setText(product.getProduct_name());
        tv_coupon_name.setText(product.getCoupon_title());
        //String sTicketSalePrice = mDecimalFormat.format((double) (product.getticket_sales_price() * product.getLimitQuantity()));
        String sTicketSalePrice = mDecimalFormat.format((double) (product.getticket_sales_price() ));
        //String sPrice = mDecimalFormat.format((double) (product.getPrice() * product.getLimitQuantity()));
        String sPrice = mDecimalFormat.format((double) (product.getPrice() ));
        if (1 < product.getspec_cnt()) {
            tv_sale_price.setText("$" + sTicketSalePrice + " 起");

            if (product.getticket_sales_price() != product.getPrice()) {
                tv_price.setVisibility(View.VISIBLE);
                tv_price.setText("$" + sPrice);
                tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tv_price.setVisibility(View.GONE);
            }
        } else {
            tv_sale_price.setText("$" + sTicketSalePrice);

            if (product.getticket_sales_price() != product.getPrice()) {
                tv_price.setVisibility(View.VISIBLE);
                tv_price.setText("$" + sPrice);
                tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tv_price.setVisibility(View.GONE);
            }
        }
    }
}