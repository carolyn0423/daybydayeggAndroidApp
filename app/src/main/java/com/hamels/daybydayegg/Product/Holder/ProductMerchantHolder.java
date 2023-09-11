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
import com.hamels.daybydayegg.Product.View.ProductMerchantFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Merchant;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.EOrderApplication;

import java.text.DecimalFormat;

public class ProductMerchantHolder extends RecyclerView.ViewHolder {
    public static final String TAG = ProductMerchantHolder.class.getSimpleName();
    public ImageView img_merchant_left , img_merchant_right;
    public ImageView img_product_left , img_product_right;
    public TextView tv_store_name_left ,tv_product_name_left ,tv_price_left , tv_sale_price_left;
    public TextView tv_store_name_right ,tv_product_name_right ,tv_price_right , tv_sale_price_right;
    public ConstraintLayout layout_right , layout_left;
    public TextView tv_location_name;
    public ConstraintLayout item_location;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#,###");

    public ProductMerchantHolder(@NonNull View itemView) {
        super(itemView);
        tv_location_name = itemView.findViewById(R.id.tv_location_name);
        item_location = itemView.findViewById(R.id.item_location);

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

    public void setLocationMerchant(Merchant merchants){
        tv_location_name.setText(merchants.getLocation_name() + " ( " + merchants.getPickupWay() + " )");
        item_location.setTag(R.id.item_location, merchants.getId());
    }

//    public void setImg_merchant_two(Merchant merchantleft , Merchant merchantright) {
//        img_merchant_left.setVisibility(View.VISIBLE);
//        img_merchant_right.setVisibility(View.VISIBLE);
//        img_product_right.setVisibility(View.INVISIBLE);
//        img_product_left.setVisibility(View.INVISIBLE);
//        Glide.with(ProductMerchantFragment.getInstance()).load(EOrderApplication.DOMAIN + merchantleft.getPicture_url()).into(img_merchant_left);
//        Glide.with(ProductMerchantFragment.getInstance()).load(EOrderApplication.DOMAIN + merchantright.getPicture_url()).into(img_merchant_right);
//        img_merchant_left.setTag(R.id.img_merchant_left,merchantleft.getId());
//        img_merchant_right.setTag(R.id.img_merchant_right,merchantright.getId());
//        layout_right.setVisibility(View.VISIBLE);
//        tv_price_right.setVisibility(View.GONE);
//        tv_sale_price_right.setVisibility(View.GONE);
//        tv_product_name_right.setVisibility(View.GONE);
//        tv_price_left.setVisibility(View.GONE);
//        tv_sale_price_left.setVisibility(View.GONE);
//        tv_product_name_left.setVisibility(View.GONE);
//    }

//    public void setImg_merchant_one(Merchant merchantleft) {
//        Log.e(TAG,merchantleft.getPicture_url());
//        img_merchant_left.setVisibility(View.VISIBLE);
//        img_merchant_right.setVisibility(View.INVISIBLE);
//
//        layout_right.setVisibility(View.INVISIBLE);
//        Glide.with(ProductMerchantFragment.getInstance()).load(EOrderApplication.DOMAIN + merchantleft.getPicture_url()).into(img_merchant_left);
//        img_merchant_left.setTag(R.id.img_merchant_left,merchantleft.getId());
//
//        img_product_left.setVisibility(View.INVISIBLE);
//        img_product_right.setVisibility(View.INVISIBLE);
//        tv_price_right.setVisibility(View.GONE);
//        tv_sale_price_right.setVisibility(View.GONE);
//        tv_product_name_right.setVisibility(View.GONE);
//        tv_price_left.setVisibility(View.GONE);
//        tv_sale_price_left.setVisibility(View.GONE);
//        tv_product_name_left.setVisibility(View.GONE);
//    }

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

        // 应用圆角转换的 RequestOptions
        RequestOptions requestOptions_left = new RequestOptions()
                .transform(new RoundedCorners(20));
        RequestOptions requestOptions_right = new RequestOptions()
                .transform(new RoundedCorners(20));

        String sLeftPictureUrl = productleft.getPicture_url() == null || productleft.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : productleft.getPicture_url();
        String sRightPictureUrl = productright.getPicture_url() == null || productright.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : productright.getPicture_url();

        Glide.with(ProductMerchantFragment.getInstance()).load(EOrderApplication.sApiUrl + sLeftPictureUrl).apply(requestOptions_left).into(img_product_left);
        Glide.with(ProductMerchantFragment.getInstance()).load(EOrderApplication.sApiUrl + sRightPictureUrl).apply(requestOptions_right).into(img_product_right);

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

        // 应用圆角转换的 RequestOptions
        RequestOptions requestOptions_left = new RequestOptions()
                .transform(new RoundedCorners(20));
        String sLeftPictureUrl = productleft.getPicture_url() == null || productleft.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : productleft.getPicture_url();
        Glide.with(ProductMerchantFragment.getInstance()).load(EOrderApplication.sApiUrl + sLeftPictureUrl).apply(requestOptions_left).into(img_product_left);

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
