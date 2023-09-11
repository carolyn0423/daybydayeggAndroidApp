package com.hamels.daybydayegg.DrawLots.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.DrawLots;
import com.hamels.daybydayegg.EOrderApplication;

public class DrawLotsHolder extends RecyclerView.ViewHolder {
    public static final String TAG = DrawLotsHolder.class.getSimpleName();
    public TextView tv_vip , tv_title , tv_location_name , tv_end_date;
    public ImageView img_lot;
    public ConstraintLayout layout_lot;


    public DrawLotsHolder(@NonNull View itemView) {
        super(itemView);
        tv_vip = itemView.findViewById(R.id.tv_vip);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_location_name = itemView.findViewById(R.id.tv_location_name);
        tv_end_date = itemView.findViewById(R.id.tv_end_date);

        img_lot = itemView.findViewById(R.id.img_lot);

        layout_lot = itemView.findViewById(R.id.layout_lot);

    }
    public void setLot(DrawLots drawLots){
        Log.e(TAG , drawLots.getTitle());
        tv_title.setText(drawLots.getTitle());
        tv_end_date.setText("報名截止："+drawLots.getEnd_date());
        tv_location_name.setText(drawLots.getLocation_name());
        if(drawLots.getLot_type().equals("1")){
            tv_vip.setVisibility(View.VISIBLE);
        }
        Log.e(TAG, EOrderApplication.sApiUrl + drawLots.getLot_picture_url());
        String sPictureUrl = drawLots.getLot_picture_url() == null || drawLots.getLot_picture_url().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : drawLots.getLot_picture_url();
        Glide.with(DrawLotsFragment.getInstance()).load(EOrderApplication.sApiUrl + sPictureUrl).into(img_lot);
        Log.e(TAG,drawLots.getLot_picture_url());

        layout_lot.setTag(R.id.layout_lot,drawLots.getId());
    }
}
