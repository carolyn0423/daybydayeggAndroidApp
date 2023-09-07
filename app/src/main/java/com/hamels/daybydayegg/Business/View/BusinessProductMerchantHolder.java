package com.hamels.daybydayegg.Business.View;

import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Merchant;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.EOrderApplication;

import java.text.DecimalFormat;

public class BusinessProductMerchantHolder extends RecyclerView.ViewHolder {
    public static final String TAG = BusinessProductMerchantHolder.class.getSimpleName();

    public ImageView img_merchant_left , img_merchant_right;
    public ImageView img_product_left , img_product_right;
    public TextView tv_store_name_left ,tv_product_name_left ,tv_price_left , tv_sale_price_left;
    public TextView tv_store_name_right ,tv_product_name_right ,tv_price_right , tv_sale_price_right;
    public ConstraintLayout layout_right , layout_left;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#,###");

    public BusinessProductMerchantHolder(@NonNull View itemView) {
        super(itemView);
            img_merchant_left = itemView.findViewById(R.id.img_merchant_left);
            img_merchant_right = itemView.findViewById(R.id.img_merchant_right);

            img_product_left = itemView.findViewById(R.id.img_product_left);
            img_product_right = itemView.findViewById(R.id.img_product_right);
//            tv_store_name_left = itemView.findViewById(R.id.tv_store_name_left);
//            tv_store_name_right = itemView.findViewById(R.id.tv_store_name_right);
            tv_product_name_left = itemView.findViewById(R.id.tv_product_name_left);
            tv_product_name_right = itemView.findViewById(R.id.tv_product_name_right);
            tv_price_left = itemView.findViewById(R.id.tv_price_left);
            tv_price_right = itemView.findViewById(R.id.tv_price_right);
            tv_sale_price_left = itemView.findViewById(R.id.tv_sale_price_left);
            tv_sale_price_right = itemView.findViewById(R.id.tv_sale_price_right);

            layout_left = itemView.findViewById(R.id.constraintLayout_left);
            layout_right = itemView.findViewById(R.id.constraintLayout_right);
    }


    public void setImg_merchant_two(Merchant merchantleft , Merchant merchantright) {
        img_merchant_left.setVisibility(View.VISIBLE);
        img_merchant_right.setVisibility(View.VISIBLE);
        img_product_right.setVisibility(View.INVISIBLE);
        img_product_left.setVisibility(View.INVISIBLE);
        String sLeftPictureUrl = merchantleft.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : merchantleft.getPicture_url();
        String sRightPictureUrl = merchantright.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : merchantright.getPicture_url();

        Glide.with(BusinessProductMerchantFragment.getInstance()).load(EOrderApplication.sApiUrl + sLeftPictureUrl).into(img_merchant_left);
        Glide.with(BusinessProductMerchantFragment.getInstance()).load(EOrderApplication.sApiUrl + sRightPictureUrl).into(img_merchant_right);
        img_merchant_left.setTag(R.id.img_merchant_left,merchantleft.getId());
        img_merchant_right.setTag(R.id.img_merchant_right,merchantright.getId());
        layout_right.setVisibility(View.VISIBLE);
        tv_price_right.setVisibility(View.GONE);
        tv_sale_price_right.setVisibility(View.GONE);
        tv_product_name_right.setVisibility(View.GONE);
        tv_price_left.setVisibility(View.GONE);
        tv_sale_price_left.setVisibility(View.GONE);
        tv_product_name_left.setVisibility(View.GONE);
    }

    public void setImg_merchant_one(Merchant merchantleft) {
        Log.e(TAG,merchantleft.getPicture_url());
        img_merchant_left.setVisibility(View.VISIBLE);
        img_merchant_right.setVisibility(View.INVISIBLE);

        layout_right.setVisibility(View.INVISIBLE);
        String sLeftPictureUrl = merchantleft.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : merchantleft.getPicture_url();

        Glide.with(BusinessProductMerchantFragment.getInstance()).load(EOrderApplication.sApiUrl + sLeftPictureUrl).into(img_merchant_left);
        img_merchant_left.setTag(R.id.img_merchant_left,merchantleft.getId());

        img_product_left.setVisibility(View.INVISIBLE);
        img_product_right.setVisibility(View.INVISIBLE);
        tv_price_right.setVisibility(View.GONE);
        tv_sale_price_right.setVisibility(View.GONE);
        tv_product_name_right.setVisibility(View.GONE);
        tv_price_left.setVisibility(View.GONE);
        tv_sale_price_left.setVisibility(View.GONE);
        tv_product_name_left.setVisibility(View.GONE);
    }

    public void setImg_product_two(Product productleft , Product productright) {
        img_product_right.setVisibility(View.VISIBLE);
        tv_price_right.setVisibility(View.VISIBLE);
        tv_sale_price_right.setVisibility(View.VISIBLE);
        tv_product_name_right.setVisibility(View.VISIBLE);
        layout_right.setVisibility(View.VISIBLE);
        img_product_left.setVisibility(View.VISIBLE);
        tv_price_left.setVisibility(View.VISIBLE);
        tv_sale_price_left.setVisibility(View.VISIBLE);
        tv_product_name_left.setVisibility(View.VISIBLE);
        img_merchant_left.setVisibility(View.INVISIBLE);
        img_merchant_right.setVisibility(View.INVISIBLE);
        String sLeftPictureUrl = productleft.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : productleft.getPicture_url();
        String sRightPictureUrl = productright.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : productright.getPicture_url();

        Glide.with(BusinessProductMerchantFragment.getInstance()).load(EOrderApplication.sApiUrl + sLeftPictureUrl).into(img_product_left);
        Glide.with(BusinessProductMerchantFragment.getInstance()).load(EOrderApplication.sApiUrl + sRightPictureUrl).into(img_product_right);
        tv_price_left.setText("NT$"+mDecimalFormat.format((double)productleft.getPrice()));
        tv_product_name_left.setText(productleft.getProduct_name());
        tv_sale_price_left.setText("NT$"+mDecimalFormat.format((double)productleft.getSale_price()));
        tv_price_left.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        img_product_left.setTag(R.id.img_product_left,productleft.getId());
        img_product_right.setTag(R.id.img_product_right,productright.getId());
        tv_price_right.setText("NT$"+mDecimalFormat.format((double)productright.getPrice()));
        tv_product_name_right.setText(productright.getProduct_name());
        tv_sale_price_right.setText("NT$"+mDecimalFormat.format((double)productright.getSale_price()));
        tv_price_right.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void setImg_product_one(Product productleft) {
        img_merchant_left.setVisibility(View.INVISIBLE);
        img_merchant_right.setVisibility(View.INVISIBLE);
        img_product_left.setVisibility(View.VISIBLE);
        tv_price_left.setVisibility(View.VISIBLE);
        tv_sale_price_left.setVisibility(View.VISIBLE);
        tv_product_name_left.setVisibility(View.VISIBLE);
        img_product_right.setVisibility(View.INVISIBLE);
        tv_price_right.setVisibility(View.INVISIBLE);
        tv_sale_price_right.setVisibility(View.INVISIBLE);
        tv_product_name_right.setVisibility(View.INVISIBLE);
        String sLeftPictureUrl = productleft.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : productleft.getPicture_url();

        Glide.with(BusinessProductMerchantFragment.getInstance()).load(EOrderApplication.sApiUrl + sLeftPictureUrl).into(img_product_left);
        img_product_left.setTag(R.id.img_product_left,productleft.getId());
        tv_price_left.setText("NT$"+mDecimalFormat.format((double)productleft.getPrice()));
        tv_product_name_left.setText(productleft.getProduct_name());
        tv_sale_price_left.setText("NT$"+mDecimalFormat.format((double)productleft.getSale_price()));
        tv_price_left.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        layout_right.setVisibility(View.INVISIBLE);
    }

    public void clean() {

    }
}
