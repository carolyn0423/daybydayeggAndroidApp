package com.hamels.daybydayegg.Donate.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Donate.Adapter.DonateListAdapter;
import com.hamels.daybydayegg.Donate.Adapter.DonateListHistoryAdapter;
import com.hamels.daybydayegg.Donate.Contract.DonateContract;
import com.hamels.daybydayegg.Donate.Presenter.DonatePresenter;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.Order.View.OrderDetailFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Donate;

import java.util.ArrayList;
import java.util.List;

public class DonateFragment extends BaseFragment implements DonateContract.View{
    public static final String TAG = DonateFragment.class.getSimpleName();

    private static DonateFragment fragment;
    private DonateContract.Presenter donatePresenter;
    private RecyclerView recyclerView;
    private DonateListAdapter donateListAdapter;
    private DonateListHistoryAdapter donatelisthistoryAdapter;
    private ImageView img_go_top;
    private TabLayout tabLayout;
    private TextView tv_history_remark, tv_uncoupon;
    private ConstraintLayout uncouponinfo;

    private String type_id = "0";

    public static DonateFragment getInstance() {
        if (fragment == null) {
            fragment = new DonateFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_donate_list, container, false);

        initView(view);

        switch (type_id) {
            case "0":
                initData(view);
                break;
            case "1":
                initDataHistory(view);
                break;
        }

        return view;
    }

    private void initView(View view) {
        Log.e(TAG, "initView");

        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);

        ((MainActivity) getActivity()).setAppTitle(R.string.tab_donate);

        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        tv_history_remark = view.findViewById(R.id.tv_history_remark);
        tabLayout = view.findViewById(R.id.tab_layout);

        if (type_id.equals("1")) {
            tabLayout.getTabAt(1).select();
        }

        recyclerView = view.findViewById(R.id.donate_recycler_view);
        uncouponinfo = view.findViewById(R.id.uncouponinfo);
        tv_uncoupon = view.findViewById(R.id.tv_uncoupon);

        img_go_top = view.findViewById(R.id.img_go_top);
        img_go_top.bringToFront();
        img_go_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });
    }

    private void initData(View view) {
        Log.e(TAG, "initData");

        tv_history_remark.setVisibility(View.GONE);

        donatePresenter = new DonatePresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);

        recyclerView.setPadding(0, 0, 0, 0);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        donateListAdapter = new DonateListAdapter(donatePresenter);
        recyclerView.setAdapter(donateListAdapter);

        donatePresenter.getDonateList();
    }

    private void initDataHistory(View view) {
        Log.e(TAG, "initDataHistory");

        tv_history_remark.setVisibility(View.VISIBLE);

        donatePresenter = new DonatePresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);

        recyclerView.setPadding(0, 30, 0, 0);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        donatelisthistoryAdapter = new DonateListHistoryAdapter(donatePresenter);
        recyclerView.setAdapter(donatelisthistoryAdapter);

        donatePresenter.getTicketUsedHistory();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated");

        super.onViewCreated(view, savedInstanceState);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type_id = Integer.toString(tab.getPosition());

                Log.e(TAG , "==========================" + type_id);

                switch (type_id) {
                    case "0":
                        initData(view);
                        break;
                    case "1":
                        initDataHistory(view);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void setDonateList(List<Donate> donateList) {
//        for(int i = 0 ; i < donateList.size() ; i++){
//            Log.e(TAG,donateList.get(i).getProductName());
//        }

        switch (type_id) {
            case "0":
                if (donateList.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    uncouponinfo.setVisibility(View.GONE);

                    List<Donate> donateleft = new ArrayList<>();
                    List<Donate> donateright = new ArrayList<>();
                    for(int i = 0 ; i < donateList.size() ; i++){
                        if(i % 2 == 0){
                            donateleft.add(donateList.get(i));
                        }
                        else{
                            donateright.add(donateList.get(i));
                        }
                    }
                    donateListAdapter.setDonate(donateleft, donateright);
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    uncouponinfo.setVisibility(View.VISIBLE);
                    tv_uncoupon.setText("目前無未使用提貨券");
                }
                break;
            case "1":
                if (donateList.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    uncouponinfo.setVisibility(View.GONE);

                    donatelisthistoryAdapter.setDonate(donateList);
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    uncouponinfo.setVisibility(View.VISIBLE);
                    tv_uncoupon.setText("目前無使用歷程紀錄");
                }
                break;
        }

        recyclerView.scrollToPosition(0);
    }

    @Override
    public void goPageHistoryDetail(String writeoff_order_id, String eticket_due_date, String modified_date, String meal_no) {

        Log.e(TAG, "----------------------------" + modified_date);

        if (!writeoff_order_id.equals("")) {
            ((MainActivity) getActivity()).addFragment(OrderDetailFragment.getInstance(writeoff_order_id, meal_no));
        }
        else {
            DonateHistoryDetailFragment.getInstance().type_idMode("1");
            ((MainActivity) getActivity()).addFragment(DonateHistoryDetailFragment.getInstance(writeoff_order_id, eticket_due_date, modified_date));
        }
    }

    @Override
    public void setDonateDetail(List<Donate> productDetail) {

    }

    @Override
    public void goPageDonateDetail(int uid) {
        ((MainActivity) getActivity()).addFragment(DonateDetailFragment.getInstance(uid));
    }

    @Override
    public void goPageDonateCart() {
        ((MainActivity) getActivity()).addFragment(DonateCartFragment.getInstance());
    }

    public void intentToLogin(int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.getActivity().startActivityForResult(intent, requestCode);
    }

    public void type_idMode(String type_id){
        this.type_id = type_id;
    }

    public void showAlert(String message) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        ((MainActivity) getActivity()).refreshBadge();
    }
}