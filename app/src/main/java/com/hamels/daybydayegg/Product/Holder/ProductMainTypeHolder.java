package com.hamels.daybydayegg.Product.Holder;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Product.View.ProductMainTypeFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.ProductMainType;

public class ProductMainTypeHolder extends RecyclerView.ViewHolder {
    public static final String TAG = ProductMainTypeHolder.class.getSimpleName();

    public ImageView img_productMainType_left , img_productMainType_right;
    public ImageView img_merchant_left , img_merchant_right;
    public TextView tv_productMainType_name_left, tv_productMainType_name_right;

    public ConstraintLayout constraintLayout_right, constraintLayout_left;

    public ProductMainTypeHolder(@NonNull View itemView) {
        super(itemView);

        img_productMainType_right = itemView.findViewById(R.id.img_productMainType_right);
        img_productMainType_left = itemView.findViewById(R.id.img_productMainType_left);

        tv_productMainType_name_left = itemView.findViewById(R.id.tv_productMainType_name_left);
        tv_productMainType_name_right = itemView.findViewById(R.id.tv_productMainType_name_right);

        img_merchant_right = itemView.findViewById(R.id.img_merchant_right);
        img_merchant_left = itemView.findViewById(R.id.img_merchant_left);

        constraintLayout_right = itemView.findViewById(R.id.constraintLayout_right);
        constraintLayout_left = itemView.findViewById(R.id.constraintLayout_left);


    }
    public void setImg_ProductMainType_one(ProductMainType mainTypeleft, String sImageUrl, String sETicket) {
        Log.e(TAG,mainTypeleft.getPicture_url());
        img_productMainType_left.setVisibility(View.VISIBLE);
        img_productMainType_right.setVisibility(View.VISIBLE);

        // 应用圆角转换的 RequestOptions
        RequestOptions requestOptions_left = new RequestOptions()
                .transform(new RoundedCorners(20));

        String sPictureUrl = mainTypeleft.getPicture_url() == null || mainTypeleft.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : mainTypeleft.getPicture_url();
        Glide.with(ProductMainTypeFragment.getInstance()).load(sImageUrl + sPictureUrl).apply(requestOptions_left).into(img_productMainType_left);

        img_productMainType_left.setTag(R.id.img_productMainType_left,mainTypeleft.getId());
        constraintLayout_left.setTag(R.id.constraintLayout_left,mainTypeleft.getId());

        tv_productMainType_name_left.setText(mainTypeleft.getProduct_Type_Main_Name());
        tv_productMainType_name_right.setText("");
    }

    public void setImg_ProductMainType_two(ProductMainType mainTypeleft , ProductMainType mainTyperight, String sImageUrl, String sETicket) {
        img_productMainType_left.setVisibility(View.VISIBLE);
        img_productMainType_right.setVisibility(View.VISIBLE);

        // 应用圆角转换的 RequestOptions
        RequestOptions requestOptions_left = new RequestOptions()
                .transform(new RoundedCorners(20));
        RequestOptions requestOptions_right = new RequestOptions()
                .transform(new RoundedCorners(20));

        String sLeftPictureUrl = mainTypeleft.getPicture_url() == null || mainTypeleft.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : mainTypeleft.getPicture_url();
        String sRightPictureUrl = mainTyperight.getPicture_url() == null || mainTyperight.getPicture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : mainTyperight.getPicture_url();

        Glide.with(ProductMainTypeFragment.getInstance()).load(sImageUrl + sLeftPictureUrl).apply(requestOptions_left).into(img_productMainType_left);
        Glide.with(ProductMainTypeFragment.getInstance()).load(sImageUrl + sRightPictureUrl).apply(requestOptions_right).into(img_productMainType_right);

        img_productMainType_left.setTag(R.id.img_productMainType_left, mainTypeleft.getId());
        img_productMainType_right.setTag(R.id.img_productMainType_right, mainTyperight.getId());

        constraintLayout_left.setTag(R.id.constraintLayout_left,mainTypeleft.getId());
        constraintLayout_right.setTag(R.id.constraintLayout_right,mainTyperight.getId());

        tv_productMainType_name_left.setText(mainTypeleft.getProduct_Type_Main_Name());
        tv_productMainType_name_right.setText(mainTyperight.getProduct_Type_Main_Name());
    }
}