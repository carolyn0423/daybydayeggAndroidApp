package com.hamels.daybydayegg.Donate.Adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Donate.Contract.DonateContract;
import com.hamels.daybydayegg.Donate.Holder.DonateHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Donate;

import java.util.ArrayList;
import java.util.List;

public class DonateListAdapter extends BaseAdapter<DonateHolder> {
    public static final String TAG = DonateListAdapter.class.getSimpleName();
    private DonateContract.Presenter presenter;

    private List<Donate> productleft = new ArrayList<>();
    private List<Donate> productright = new ArrayList<>();

    public DonateListAdapter(DonateContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public DonateHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        Log.e(TAG, "onCreateViewHolder");
        View viewDonate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_donate, viewGroup, false);
        return new DonateHolder(viewDonate);
    }

    @Override
    public void onBindViewHolder(@NonNull DonateHolder productHolder, @SuppressLint("RecyclerView") final int position) {
//        Log.e(TAG, "onBindViewHolder");
        if (productleft.size() == productright.size()) {
            productHolder.setImg_product_two(productleft.get(position), productright.get(position));
        } else {
            if (productright.size() == position) {
                productHolder.setImg_product_one(productleft.get(position));
            } else {
                productHolder.setImg_product_two(productleft.get(position), productright.get(position));
            }
        }



//        productHolder.img_donate_left.setOnClickListener(left_img_OnClick_Evt);
//        productHolder.img_donate_right.setOnClickListener(right_img_OnClick_Evt);
//        productHolder.layout_left.setOnClickListener(left_img_OnClick_Evt);
//        productHolder.layout_right.setOnClickListener(right_img_OnClick_Evt);

        productHolder.img_donate_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkEnabled(v, productleft.get(position), "Detail");
            }
        });

        productHolder.img_donate_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkEnabled(v, productright.get(position), "Detail");
            }
        });

        productHolder.layout_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkEnabled(v, productleft.get(position), "Detail");
            }
        });

        productHolder.layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkEnabled(v, productright.get(position), "Detail");
            }
        });

        productHolder.tv_cart_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkEnabled(v, productleft.get(position), "Cart");
                //  presenter.goPageDonateCart(productleft.get(position).getproduct_id(), productleft.get(position).getspec_id(), productleft.get(position).getgive_date());
            }
        });
        productHolder.tv_cart_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkEnabled(v, productright.get(position), "Cart");
                //  presenter.goPageDonateCart(productright.get(position).getproduct_id(), productright.get(position).getspec_id(), productright.get(position).getgive_date());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productleft.size();
    }

    public void setDonate(List<Donate> productleft, List<Donate> productright) {
        this.productleft = productleft;
        this.productright = productright;
        notifyDataSetChanged();
    }

    private void chkEnabled(View v, Donate donate, String sMode){

        if(donate.getTicketDue().equals("N")) {
            //  未過期
            if (donate.getTicketEnabled().equals("Y")) {
                //  尚未下架
                if(sMode.equals("Detail")){
                    presenter.getDonateDetailByID(Integer.parseInt(v.getTag(v.getId()).toString()));
                }else{
                    presenter.goPageDonateCart(donate.getproduct_id(), donate.getspec_id(), donate.getgive_date());
                }
            } else {
                //  已下架
                presenter.showContactMessage("此提貨券已下架，請連繫客服人員進行退貨事宜，謝謝您。\n" +
                        "\n" +
                        "聯絡電話 : " + donate.getContactPhone());
            }
        }else{
            presenter.showContactMessage("此提貨券已過期。");
        }
    }

//    View.OnClickListener left_img_OnClick_Evt = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            int id = v.getId();
//            if (id == R.id.img_donate_left || id == R.id.img_donate_right
//            || id == R.id.donate_constraintLayout_left || id == R.id.donate_constraintLayout_right){
//                presenter.getDonateDetailByID(Integer.parseInt(v.getTag(v.getId()).toString()));
//            }
//        }
//    };
//
//    View.OnClickListener right_img_OnClick_Evt = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            int id = v.getId();
//            if (id == R.id.img_donate_left || id == R.id.img_donate_right
//                    || id == R.id.donate_constraintLayout_left || id == R.id.donate_constraintLayout_right){
//                presenter.getDonateDetailByID(Integer.parseInt(v.getTag(v.getId()).toString()));
//            }
//        }
//    };
}