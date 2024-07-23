package com.hamels.daybydayegg.Donate.View;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Donate.Adapter.DonateListHistoryAdapter;
import com.hamels.daybydayegg.Donate.Contract.DonateContract;
import com.hamels.daybydayegg.Donate.Presenter.DonatePresenter;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Donate;

import java.util.List;

public class DonateHistoryDetailFragment extends BaseFragment implements DonateContract.View {
    public static final String TAG = DonateHistoryDetailFragment.class.getSimpleName();

    private static final String WRITEOFF_ORDER_ID = "writeoff_order_id";
    private static final String ETICKETDUEDATE = "eticket_due_date";
    private static final String MODIFIEDDATE = "modified_date";

    private static DonateHistoryDetailFragment fragment;

    private TextView tv_meal_no_title, tv_meal_no, tv_ref_content, tv_writeoff_due_date;

    private DonateContract.Presenter donatePresenter;
    private RecyclerView recyclerView;
    private DonateListHistoryAdapter donatelisthistoryAdapter;

    private TabLayout tabLayout;
    private String type_id = "0";

    private String writeoff_order_id = "";
    private String eticket_due_date = "";
    private String modified_date = "";

    public static DonateHistoryDetailFragment getInstance() {
        if (fragment == null) {
            fragment = new DonateHistoryDetailFragment();
        }

        return fragment;
    }

    public static DonateHistoryDetailFragment getInstance(String writeoff_order_id, String eticket_due_date, String modified_date) {
        if (fragment == null) {
            fragment = new DonateHistoryDetailFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(WRITEOFF_ORDER_ID, writeoff_order_id);
        bundle.putString(ETICKETDUEDATE, eticket_due_date);
        bundle.putString(MODIFIEDDATE, modified_date);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donatehistory_detail, container, false);
        if (getArguments() != null) {
            writeoff_order_id = getArguments().getString(WRITEOFF_ORDER_ID, "");
            eticket_due_date = getArguments().getString(ETICKETDUEDATE, "");
            modified_date = getArguments().getString(MODIFIEDDATE, "");
            Log.e(TAG, writeoff_order_id + eticket_due_date + modified_date + "");
            initView(view);
        }
        initView(view);
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
        ((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        tabLayout = view.findViewById(R.id.tab_layout);

        if (type_id.equals("1")) {
            tabLayout.getTabAt(1).select();
        }

        // not meal_no
        tv_meal_no_title = view.findViewById(R.id.tv_meal_no_title);
        tv_meal_no = view.findViewById(R.id.tv_meal_no);
        tv_ref_content = view.findViewById(R.id.tv_ref_content);
        tv_writeoff_due_date = view.findViewById(R.id.tv_writeoff_due_date);

        initDataHistory(view);
    }

    private void initDataHistory(View view) {
        Log.e(TAG, "initDataHistory");

        recyclerView = view.findViewById(R.id.donatehistorydetail_recycler_view);

        donatePresenter = new DonatePresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        donatelisthistoryAdapter = new DonateListHistoryAdapter(donatePresenter);
        recyclerView.setAdapter(donatelisthistoryAdapter);

        donatePresenter.getDonateHistoryDetailByID(writeoff_order_id, eticket_due_date, modified_date);
    }

    @Override
    public void setDonateDetail(List<Donate> productDetail) {

        tv_meal_no.setText("");
        tv_writeoff_due_date.setText(productDetail.get(0).getwriteoff_due_date());
        tv_meal_no_title.setText(productDetail.get(0).getTransactionName());

        switch (productDetail.get(0).getTicketStatus()){
            case "B": //購買
            case "R": //自購自用
                break;
            case "G": //贈出
                tv_ref_content.setText("受贈者：" + productDetail.get(0).getGive_member_name() + " " + productDetail.get(0).getGive_member_mobile());
                break;
            case "BU": //
            case "RU": //接收贈送兌換

                if(productDetail.get(0).getWriteoff_order_id().indexOf("|||") >=0) {
                    tv_ref_content.setText("備註/編號：" + productDetail.get(0).getWriteoff_order_id().split("\\|\\|\\|")[0]);
                } else {
                    tv_ref_content.setText("備註/編號 : " + productDetail.get(0).getWriteoff_order_id());
                }

                break;
        }
    }

    @Override
    public void showAlert(String sContactPhone) {

    }

    @Override
    public void goPageDonateDetail(int donate_id) {

    }

    @Override
    public void goPageDonateCart() {

    }

    @Override
    public void intentToLogin(int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public void setDonateList(List<Donate> donateList) {
        for(int i = 0 ; i < donateList.size() ; i++){
            Log.e(TAG,donateList.get(i).getProductName());
        }

        donatelisthistoryAdapter.setDonateHistoryDetail(donateList);
    }

    @Override
    public void goPageHistoryDetail(String writeoff_order_id, String eticket_due_date, String modified_date, String meal_no) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated");

        super.onViewCreated(view, savedInstanceState);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type_id = Integer.toString(tab.getPosition());

                Log.e(TAG , "==========================" + type_id);

                switch (type_id) {
                    case "0":
                        DonateFragment.getInstance().type_idMode("0");
                        ((MainActivity) getActivity()).changeTabFragment(DonateFragment.getInstance());
                        break;
                    case "1":
                        getActivity().getSupportFragmentManager().popBackStack();
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

    public void type_idMode(String type_id){
        this.type_id = type_id;
    }
}

