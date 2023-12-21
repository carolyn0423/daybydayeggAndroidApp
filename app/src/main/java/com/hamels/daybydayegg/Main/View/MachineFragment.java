package com.hamels.daybydayegg.Main.View;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Main.Adapter.MachineAdapter;
import com.hamels.daybydayegg.Main.Contract.MachineContract;
import com.hamels.daybydayegg.Main.Presenter.MachinePresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.ApiRepository.ApiRepository;
import com.hamels.daybydayegg.Repository.ApiRepository.MemberRepository;
import com.hamels.daybydayegg.Repository.Model.Machine;
import com.hamels.daybydayegg.Utils.IntentUtils;

import java.util.List;

public class MachineFragment extends BaseFragment implements MachineContract.View {
    public static final String TAG = MachineFragment.class.getSimpleName();

    private static MachineFragment fragment;
    public static int lastSelectedTabPosition = -1;
    private TabLayout tabLayout;
    private TabItem tabItem1, tabItem2, tabItem3, tabItem4;
    private RecyclerView recyclerView;

    private MachineAdapter machineAdapter;
    private MachineContract.Presenter machinePresenter;
    private boolean isOne = true;

    public static MachineFragment getInstance() {
        if (fragment == null) {
            fragment = new MachineFragment();
        }
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machine, container, false);

        tabLayout = view.findViewById(R.id.tab_machine_layout);
        tabItem1 = view.findViewById(R.id.tabItem1);
        //  tabItem2 = view.findViewById(R.id.tabItem2);
        tabItem3 = view.findViewById(R.id.tabItem3);
        tabItem4 = view.findViewById(R.id.tabItem4);

        initView(view);

        return view;
    }


    private void initView(View view) {
        isOne = true;
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_store);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
//        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);

        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        //  清除API 暫存, 重新取得URL
        ApiRepository.repository = null;
        MemberRepository.memberRepository = null;
        ApiRepository.getInstance();
        MemberRepository.getInstance();

        machinePresenter = new MachinePresenter(this, getRepositoryManager(getContext()));
        machineAdapter = new MachineAdapter(this, machinePresenter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String stTabText = tab.getText().toString();
                switch (stTabText){
                    case "常用據點":
                        //  常用機台
                        lastSelectedTabPosition = -1;
                        machinePresenter.setFunctionname("AppOften");
                        break;
                    case "最近五公里":
                        //  最近五公里
                        //machinePresenter.setFunctionname("AppDistance");
                        break;
                    case "據點清單":
                        //  據點清單
                        lastSelectedTabPosition = -1;
                        machinePresenter.setFunctionname("All");
                        break;
                    case "城市探索":
                        //  城市探索
                        ((MainActivity) getActivity()).checkLocationPermission();
                        ((MainActivity) getActivity()).addFragment(MachineMapFragment.getInstance());
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

        ((MainActivity) getActivity()).checkLocationPermission();

        if(!machinePresenter.getUserLogin()) {
            //  若未登入，移除常用鈕
            tabLayout.removeTabAt(1);
        }

        switch (lastSelectedTabPosition){
            case -1:    //  代表第一次近來
            case 0:
                tabLayout.getTabAt(0).select();
                machinePresenter.setFunctionname("All");
                break;
            case 1:
                tabLayout.getTabAt(1).select();
                machinePresenter.setFunctionname("AppOften");
                break;
        }

        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(machineAdapter);
    }

    @Override
    public void setMachineList(List<Machine> machines) {
        boolean isOften = false;
        for (Machine machine : machines) {
            if(!isOften) isOften = machine.geOftenID() != null && !machine.geOftenID().equals("") ? true : false;
            Log.e(TAG, machine.getTitle());
        }

        if(isOften && lastSelectedTabPosition == -1 && isOne){
            tabLayout.getTabAt(1).select();
            machinePresenter.setFunctionname("AppOften");
        } else {
            machineAdapter.setData(machines);
        }

        isOne = false;
    }

    @Override
    public void intentToGoogleMap(String address) {
        IntentUtils.intentToGoogleMap((BaseActivity) getActivity(), address);
    }
}
