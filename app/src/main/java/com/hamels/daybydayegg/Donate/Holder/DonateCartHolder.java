package com.hamels.daybydayegg.Donate.Holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.daybydayegg.Donate.View.DonateCart2Fragment;
import com.hamels.daybydayegg.Donate.View.DonateCartFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.DonateCart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DonateCartHolder extends RecyclerView.ViewHolder {
    public static final String TAG = DonateCartHolder.class.getSimpleName();
    public ImageView img_picture_url;
    public TextView tv_product_name, tv_type_name_spec_name, tv_eticket_due_date, tv_left_number, tv_giveflag, tv_cart_count;
    public EditText edit_cart_count;
    public Button btn_minus, btn_plus;

    public DonateCartHolder(@NonNull View itemView) {
        super(itemView);

        tv_giveflag = itemView.findViewById(R.id.tv_giveflag);
        img_picture_url = itemView.findViewById(R.id.img_picture_url);
        tv_product_name = itemView.findViewById(R.id.tv_product_name);
        //tv_type_name_spec_name = itemView.findViewById(R.id.tv_type_name_spec_name);
        tv_eticket_due_date = itemView.findViewById(R.id.tv_eticket_due_date);
        tv_left_number = itemView.findViewById(R.id.tv_left_number);
        edit_cart_count = itemView.findViewById(R.id.edit_cart_count);
        btn_minus = itemView.findViewById(R.id.btn_minus);
        btn_plus = itemView.findViewById(R.id.btn_plus);
        tv_cart_count = itemView.findViewById(R.id.tv_cart_count);
    }

    public void setImg_donatecart1(DonateCart history) {
        Glide.with(DonateCartFragment.getInstance()).load(EOrderApplication.sApiUrl + history.getPictureUrl()).into(img_picture_url);
        tv_product_name.setText(history.getProductName());
        //tv_type_name_spec_name.setText(" ( " + history.getTypeName() + " - " + history.getspec_name() + " ) ");
        //tv_spec_name.setText("");

        String sdate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(history.getEticketDueDate());
            sdate = transFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (sdate.equals("")) {
            tv_eticket_due_date.setText(history.getEticketDueDate());
        }
        else {
            tv_eticket_due_date.setText(sdate);
        }

        tv_left_number.setText(history.getLeftNumber());
        edit_cart_count.setText(history.getcart_count());

        if (history.getTicketStatus().equals("R")) {
            tv_giveflag.setVisibility(View.VISIBLE);
        } else {
            tv_giveflag.setVisibility(View.INVISIBLE);
        }
    }

    public void setImg_donatecart2(DonateCart history) {
        Glide.with(DonateCart2Fragment.getInstance()).load(EOrderApplication.sApiUrl + history.getPictureUrl()).into(img_picture_url);
        tv_product_name.setText(history.getProductName());
        //tv_type_name_spec_name.setText(" ( " + history.getTypeName() + " - " + history.getspec_name() + " ) ");
        tv_eticket_due_date.setText(history.getEticketDueDate());
        tv_cart_count.setText(history.getcart_count());
    }

    public Boolean setcart_count(DonateCart history, String calc_type) {
        Boolean status = false;

        Integer left_number = Integer.parseInt(history.getLeftNumber());
        Integer quantity = Integer.parseInt(edit_cart_count.getText().toString());

        switch (calc_type) {
            case "add":
                if (quantity < left_number) {
                    quantity++;
                    status = true;
                }
                break;
            case "minus":
                if(quantity > 0){
                    quantity--;
                    status = true;
                }
                break;
        }

        edit_cart_count.setText(Integer.toString(quantity));

        return status;
    }
}
