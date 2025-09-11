package com.hamels.daybydayegg.MemberCenter.View;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Adapter.OftenAdapter;
import com.hamels.daybydayegg.MemberCenter.Contract.OftenContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.OftenPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.ApiRepository.ApiRepository;
import com.hamels.daybydayegg.Repository.ApiRepository.MemberRepository;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.Often;

import java.util.List;

public class OftenFragment extends BaseFragment implements OftenContract.View {
    public static final String TAG = OftenFragment.class.getSimpleName();

    private static OftenFragment fragment;
    private TabLayout tabLayout;
    private TabItem tabItem1, tabItem2, tabItem3;
    private RecyclerView recyclerView;

    private OftenAdapter Adapter;
    private OftenContract.Presenter Presenter;
    public List<Address> addresses = null;

    public static OftenFragment getInstance() {
        if (fragment == null) {
            fragment = new OftenFragment();
        }
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_often, container, false);

        tabLayout = view.findViewById(R.id.tab_layout);
        tabItem1 = view.findViewById(R.id.tabItem1);
        tabItem2 = view.findViewById(R.id.tabItem2);
        tabItem3 = view.findViewById(R.id.tabItem3);

        initView(view);

        return view;
    }


    private void initView(View view) {
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_often);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);

        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        //((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        //  清除API 暫存, 重新取得URL
        ApiRepository.repository = null;
        MemberRepository.memberRepository = null;
        ApiRepository.getInstance();
        MemberRepository.getInstance();

        Presenter = new OftenPresenter(this, getRepositoryManager(getContext()));
        Adapter = new OftenAdapter(this, Presenter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String stTabText = tab.getText().toString();
                switch (stTabText){
                    case "統一編號":
                        //  常用統一編號
                        Presenter.getOftenList("INVOICE");
                        break;
                    case "轉贈手機號":
                        //  常用轉贈手機號
                        Presenter.getOftenList("MOBILE");
                        break;
                    case "宅配地址":
                        //  常用地址
                        Presenter.getOftenList("ADDRESS");
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

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(Adapter);

        //  取得地址清單
        if(addresses == null){
            Presenter.getPropertyData();
        }else {
            //  預設進入 常用統一編號
            Presenter.getOftenList("INVOICE");
        }
    }

    @Override
    public void setOftenList(String sFunctionName, List<Often> oftens) {
        Adapter.setData(sFunctionName, oftens);
    }

    public void setPropertyCode(List<Address> addresses){
        this.addresses = addresses;
        Presenter.getOftenList("INVOICE");
    }

    public void showAlert(String message) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        ((MainActivity) getActivity()).refreshBadge();
    }

    public void CloseWindow(){
        // 关闭键盘
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        // 隐藏键盘，传入当前焦点的 View
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
