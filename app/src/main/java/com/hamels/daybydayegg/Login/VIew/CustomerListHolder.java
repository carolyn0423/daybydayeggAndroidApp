package com.hamels.daybydayegg.Login.VIew;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Customer;

public class CustomerListHolder extends RecyclerView.ViewHolder {
    public TextView tvCustomerName, tvCustomerDistance, tvAddress, tvCustomerSelect;
    public ImageView tvCustomerFavorite;
    public ConstraintLayout clItemCustomer;
    public String sMode;

    public CustomerListHolder(@NonNull View itemView) {
        super(itemView);
        clItemCustomer = itemView.findViewById(R.id.item_customer);

        tvCustomerName = itemView.findViewById(R.id.tv_customer_name);

        tvCustomerDistance = itemView.findViewById(R.id.tv_customer_distance);
        tvAddress = itemView.findViewById(R.id.tv_address);

        tvCustomerSelect = itemView.findViewById(R.id.tv_customer_select);
        tvCustomerFavorite = itemView.findViewById(R.id.tv_customer_favorite);
    }

    public void setCustomer(Customer customer, String sLoveCustomer) {

        tvCustomerName.setText(customer.getCustomerName());
        tvCustomerDistance.setText(Double.toString(customer.getDistance()));
        tvAddress.setText(customer.getCity() + customer.getAddress());

        String tmpdistance = "";
//        if (customer.getDistance() > 0) {
//            DecimalFormat decimalFormat = new DecimalFormat(".0"); //取小數點後面兩位
//            Double DoubleTmp = customer.getDistance() / 1000; //Double取小數點後面兩位
//            String StringTmp = "";//最終取到的結果
//            StringTmp = decimalFormat.format(DoubleTmp);
//            tmpdistance = StringTmp + "km";
//        }
        tvCustomerDistance.setText(tmpdistance);

        /*
        if (store.getIsOften().equals("1")) {
            tv_storefavorite.setImageResource(R.drawable.favorites_1);
        }
        else {
            tv_storefavorite.setImageResource(R.drawable.favoritesgr);
        }

         */
    }
}
