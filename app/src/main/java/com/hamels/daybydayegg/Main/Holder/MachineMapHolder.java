package com.hamels.daybydayegg.Main.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Machine;

public class MachineMapHolder extends RecyclerView.ViewHolder {
    public TextView tvProductName, tvQuantity;

    public MachineMapHolder(@NonNull View itemView) {
        super(itemView);
        tvProductName = itemView.findViewById(R.id.product_name);
        tvQuantity = itemView.findViewById(R.id.quantity);
    }

    public void setProduct(Machine.Product product) {
        tvProductName.setText(product.getName());
        tvQuantity.setText(product.getQuantity());
    }
}
