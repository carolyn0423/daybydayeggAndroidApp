package com.hamels.daybydayegg.Login.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.Contract.CustomerListContract;
import com.hamels.daybydayegg.Login.VIew.CustomerListHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerListAdapter extends BaseAdapter<CustomerListHolder> {
    public static final String TAG = CustomerListAdapter.class.getSimpleName();
    private CustomerListContract.View view;
    private CustomerListContract.Presenter presenter;
    private List<Customer> customers;
    private String sMode;
    private String sLoveCustomer = "";
    private String sSourceActive = "";

    public CustomerListAdapter(CustomerListContract.View view, CustomerListContract.Presenter presenter, String Mode, String LoveCustomer) {
        this.view = view;
        this.presenter = presenter;
        customers = new ArrayList<>();
        sMode = Mode;
        sLoveCustomer = LoveCustomer;
        sSourceActive = presenter.getSourceActive();
    }

    @NonNull
    @Override
    public CustomerListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_customer_list, viewGroup, false);

        return new CustomerListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerListHolder customerListHolder, int position) {
        int customerId = Integer.parseInt(customers.get(position).getCustomerID());
        String customerName = customers.get(position).getCustomerName();
        String apiUrl = customers.get(position).getApiUrl();

        if (sMode.equals("isSelectLoveCustomer")) {
            // 選擇商家
            customerListHolder.tvCustomerSelect.setVisibility(View.VISIBLE);
            customerListHolder.tvCustomerFavorite.setVisibility(View.GONE);

            customerListHolder.tvCustomerSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = customerListHolder.getAdapterPosition();
                    if (pos == RecyclerView.NO_POSITION) return;

                    Customer customer = customers.get(pos);
                    EOrderApplication.isLogin = false;
                    presenter.goCustomer(customer.getCustomerID(), customer.getCustomerName(), customer.getApiUrl());
                }
            });

        } else {
            // 將商家加入最愛
            customerListHolder.tvCustomerSelect.setVisibility(View.GONE);
            customerListHolder.tvCustomerFavorite.setVisibility(View.VISIBLE);

            if (sLoveCustomer.indexOf("|" + customerId + "|") == -1) {
                customerListHolder.tvCustomerFavorite.setImageResource(R.drawable.favoritesgr);
            } else {
                customerListHolder.tvCustomerFavorite.setImageResource(R.drawable.favorites_1);
            }

            customerListHolder.tvCustomerFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = customerListHolder.getAdapterPosition();
                    if (pos == RecyclerView.NO_POSITION) return;

                    int id = Integer.parseInt(customers.get(pos).getCustomerID());
                    Boolean isLove = presenter.saveLoveCustomerID(String.valueOf(id));

                    if (isLove) {
                        customerListHolder.tvCustomerFavorite.setImageResource(R.drawable.favorites_1);
                    } else {
                        customerListHolder.tvCustomerFavorite.setImageResource(R.drawable.favoritesgr);
                    }
                }
            });
        }

        customerListHolder.setCustomer(customers.get(position), sLoveCustomer);
    }

//    public void onBindViewHolder(@NonNull CustomerListHolder customerListHolder,  int position) {
//
//        if(sMode.equals(("isSelectLoveCustomer"))){
//            //  選擇商家
//            customerListHolder.tvCustomerSelect.setVisibility(View.VISIBLE);
//            customerListHolder.tvCustomerFavorite.setVisibility(View.GONE);
//
//            customerListHolder.tvCustomerSelect.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    EOrderApplication.isLogin = false;
//                    //EOrderApplication.sApiUel = customers.get(position).getApiUrl();
//
//                    presenter.goCustomer(customers.get(position).getCustomerID(), customers.get(position).getCustomerName(), customers.get(position).getApiUrl());
//                }
//            });
//        } else {
//            //  將商家加入最愛
//            customerListHolder.tvCustomerSelect.setVisibility(View.GONE);
//            customerListHolder.tvCustomerFavorite.setVisibility(View.VISIBLE);
//
//            if(sLoveCustomer.indexOf("|" + customers.get(position).getCustomerID() + "|") == -1){
//                customerListHolder.tvCustomerFavorite.setImageResource(R.drawable.favoritesgr);
//            }else{
//                customerListHolder.tvCustomerFavorite.setImageResource(R.drawable.favorites_1);
//            }
//
//            customerListHolder.tvCustomerFavorite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Boolean isLove = presenter.saveLoveCustomerID(customers.get(position).getCustomerID());
//
//                    if(isLove){
//                        customerListHolder.tvCustomerFavorite.setImageResource(R.drawable.favorites_1);
//                    }else{
//                        customerListHolder.tvCustomerFavorite.setImageResource(R.drawable.favoritesgr);
//                    }
//                }
//            });
//        }
//
//        customerListHolder.setCustomer(customers.get(position), sLoveCustomer);
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