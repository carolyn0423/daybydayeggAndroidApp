package com.hamels.daybydayegg.Main.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Main.Contract.CustomerContract;
import com.hamels.daybydayegg.Main.Holder.CustomerHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends BaseAdapter<CustomerHolder> {
    public static final String TAG = CustomerAdapter.class.getSimpleName();
    private CustomerContract.View view;
    private CustomerContract.Presenter presenter;
    private List<Customer> customers;
    private String sSourceActive = "";

    public CustomerAdapter(CustomerContract.View view, CustomerContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
        this.customers = new ArrayList<>();
        this.sSourceActive = presenter.getSourceActive();
    }

    @NonNull
    @Override
    public CustomerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_customer_list, viewGroup, false);

        return new CustomerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerHolder customerHolder, int position) {
        customerHolder.setCustomer(customers.get(position));

        switch (sSourceActive) {
            case "PRODUCT_WELCOME":
            case "ETICKET_WELCOME":
            case "LOCATION_WELCOME":
                customerHolder.tvCustomerSelect.setVisibility(View.GONE);
                customerHolder.tvCustomerFavorite.setVisibility(View.GONE);

                customerHolder.clItemCustomer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = customerHolder.getAdapterPosition();
                        if (pos == RecyclerView.NO_POSITION) return;

                        switch (sSourceActive) {
                            case "PRODUCT_WELCOME":
                            case "LOCATION_WELCOME":
                                presenter.saveCustomerID(customers.get(pos).getCustomerID());
                                presenter.saveApiUrl(customers.get(pos).getApiUrl());
                                presenter.goLocationList(customers.get(pos).getCustomerID());

                                EOrderApplication.CUSTOMER_ID = customers.get(pos).getCustomerID();
                                EOrderApplication.isLogin = false;
                                EOrderApplication.sApiUrl = customers.get(pos).getApiUrl();
                                break;

                            case "ETICKET_WELCOME":
                                presenter.saveCustomerID(customers.get(pos).getCustomerID());
                                presenter.saveApiUrl(customers.get(pos).getApiUrl());
                                presenter.goETicketProductMainType(customers.get(pos).getCustomerID());

                                EOrderApplication.CUSTOMER_ID = customers.get(pos).getCustomerID();
                                EOrderApplication.isLogin = false;
                                EOrderApplication.sApiUrl = customers.get(pos).getApiUrl();
                                break;
                        }
                    }
                });
                break;
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull final CustomerHolder customerHolder,  int position) {
//        customerHolder.setCustomer(customers.get(position));
//
//        switch (sSourceActive){
//            case "PRODUCT_WELCOME":
//            case "ETICKET_WELCOME":
//            case "LOCATION_WELCOME":
//                customerHolder.tvCustomerSelect.setVisibility(View.GONE);
//                customerHolder.tvCustomerFavorite.setVisibility(View.GONE);
//
//                customerHolder.clItemCustomer.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        switch (sSourceActive){
//                            case "PRODUCT_WELCOME":
//                            case "LOCATION_WELCOME":
//                                // 未登入 -> 外帶外送 && 門市查詢
//                                presenter.saveCustomerID(customers.get(position).getCustomerID());
//                                presenter.saveApiUrl(customers.get(position).getApiUrl());
//                                presenter.goLocationList(customers.get(position).getCustomerID());
//
//                                EOrderApplication.CUSTOMER_ID = customers.get(position).getCustomerID();
//                                EOrderApplication.isLogin = false;
//                                EOrderApplication.sApiUrl = customers.get(position).getApiUrl();
//                                break;
//                            case "ETICKET_WELCOME":
//                                // 未登入 -> 買提貨卷
//                                presenter.saveCustomerID(customers.get(position).getCustomerID());
//                                presenter.saveApiUrl(customers.get(position).getApiUrl());
//                                presenter.goETicketProductMainType(customers.get(position).getCustomerID());
//
//                                EOrderApplication.CUSTOMER_ID = customers.get(position).getCustomerID();
//                                EOrderApplication.isLogin = false;
//                                EOrderApplication.sApiUrl = customers.get(position).getApiUrl();
//                                break;
//                        }
//                    }
//                });
//                break;
//        }
//    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void setData(List<Customer> customers) {
        this.customers = customers;
        notifyDataSetChanged();
    }
}
