package com.hamels.daybydayegg.Main.View;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
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
    private TabLayout tabLayout;
    private TabItem tabItem1, tabItem2, tabItem3, tabItem4;
    private RecyclerView recyclerView;

    private MachineAdapter machineAdapter;
    private MachineContract.Presenter machinePresenter;
    private static final int REQUEST_LOCATION_PERMISSION = 100;

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
        tabItem2 = view.findViewById(R.id.tabItem2);
        tabItem3 = view.findViewById(R.id.tabItem3);
        tabItem4 = view.findViewById(R.id.tabItem4);

        initView(view);

        return view;
    }


    private void initView(View view) {
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_store);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
//        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);

        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(false);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);

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
                        machinePresenter.setFunctionname("AppOften");
                        break;
                    case "最近五公里":
                        //  最近五公里
                        machinePresenter.setFunctionname("AppDistance");
                        break;
                    case "據點清單":
                        //  據點清單
                        machinePresenter.setFunctionname("All");
                        break;
                    case "城市探索":
                        //  城市探索
                        Toast.makeText(getContext(), "敬請期待", Toast.LENGTH_SHORT).show();
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

        requestUserLocation();

        if(machinePresenter.getUserLogin()){
            tabLayout.getTabAt(0).select();
            machinePresenter.setFunctionname("AppOften");
        }else{
            tabLayout.getTabAt(2).select();
            machinePresenter.setFunctionname("All");
            tabLayout.removeTabAt(0);
        }

        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(machineAdapter);
    }

    @Override
    public void setMachineList(List<Machine> machines) {
        for (Machine machine : machines) {
            Log.e(TAG, machine.getTitle());
        }

        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        machineAdapter.setData(machines);
    }

    @Override
    public void intentToGoogleMap(String address) {
        IntentUtils.intentToGoogleMap((BaseActivity) getActivity(), address);
    }

    public void requestUserLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 在這裡處理位置更新
                EOrderApplication.lat = location.getLatitude();  // 獲取緯度
                EOrderApplication.lon = location.getLongitude();  // 獲取經度
            }

            @Override
            public void onProviderDisabled(String provider) {
                // 在這裡處理提供程序停用事件
            }

            @Override
            public void onProviderEnabled(String provider) {
                // 在這裡處理提供程序啟用事件
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // 在這裡處理狀態更改事件
            }
        };

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // 沒有權限，需要向用戶請求權限
            // 取得 GPS 權限
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Ask for permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
                return;
            }
        } else {
            // 已經有權限，可以繼續操作
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // 請求權限
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }
}