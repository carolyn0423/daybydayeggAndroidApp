package com.hamels.daybydayegg.Main.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Main.Adapter.MachineAdapter;
import com.hamels.daybydayegg.Main.Adapter.MachineMapAdapter;
import com.hamels.daybydayegg.Main.Contract.MachineMapContract;
import com.hamels.daybydayegg.Main.Presenter.MachineMapPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.ApiRepository.ApiRepository;
import com.hamels.daybydayegg.Repository.ApiRepository.MemberRepository;
import com.hamels.daybydayegg.Repository.Model.Machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineMapFragment extends BaseFragment implements MachineMapContract.View {
    public static final String TAG = MachineMapFragment.class.getSimpleName();

    private static MachineMapFragment fragment;
    private TabLayout tabLayout;
    private TabItem tabItem1, tabItem2, tabItem3, tabItem4;
    private MachineMapContract.Presenter machineMapPresenter;
    private List<Machine> machines;
    private MapView mapView;
    private LatLng currentLocation = new LatLng(EOrderApplication.lat, EOrderApplication.lon);
    private MachineMapAdapter machineMapAdapter;
    private boolean isOneLatLon = false;
    private Map<Marker, Machine> markerMachineMap = new HashMap<>();
    public static MachineMapFragment getInstance() {
        if (fragment == null) {
            fragment = new MachineMapFragment();
        }
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machine_map, container, false);

        tabLayout = view.findViewById(R.id.tab_machine_layout);
        tabItem1 = view.findViewById(R.id.tabItem1);
        //  tabItem2 = view.findViewById(R.id.tabItem2);
        tabItem3 = view.findViewById(R.id.tabItem3);
        tabItem4 = view.findViewById(R.id.tabItem4);

        initView(view);

        if(EOrderApplication.lat == 0 && EOrderApplication.lon == 0){
            requestUserLocation();
        }else{
            currentLocation = new LatLng(EOrderApplication.lat, EOrderApplication.lon);
        }

        // 初始化 MapView
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume(); // 必須在生命週期方法中管理地圖生命週期
        // 請注意，您可能需要在這裡設置地圖相關的其他配置
        mapView.getMapAsync(googleMap -> {
            if (googleMap != null) {
                // 在這裡處理地圖相關操作
                // 將地圖設置為可交互
                googleMap.getUiSettings().setAllGesturesEnabled(true);

                // 在地圖上添加目前位置標記
                googleMap.addMarker(new MarkerOptions().position(currentLocation).title("目前位置"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume(); // 確保管理地圖的生命週期
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause(); // 確保管理地圖的生命週期
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy(); // 確保管理地圖的生命週期
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory(); // 確保管理地圖的生命週期
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_store);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
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

        tabLayout.getTabAt(2).select();

        machineMapPresenter = new MachineMapPresenter(this, getRepositoryManager(getContext()));
        machineMapAdapter = new MachineMapAdapter(this, machineMapPresenter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String stTabText = tab.getText().toString();
                switch (stTabText){
                    case "常用據點":
                        //  常用機台
                        MachineFragment.lastSelectedTabPosition = 0;
                        ((MainActivity) getActivity()).addFragment(MachineFragment.getInstance());
                        break;
                    case "據點清單":
                        //  據點清單
                        MachineFragment.lastSelectedTabPosition = 1;
                        ((MainActivity) getActivity()).addFragment(MachineFragment.getInstance());
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

        machineMapPresenter.getMachineList(true);

        if(!machineMapPresenter.getUserLogin()){
            tabLayout.removeTabAt(0);
        }
    }

    public void setMachineList(List<Machine> machines, boolean isOne) {
        this.machines = machines;

        if(isOne) {
            mapView.getMapAsync(googleMap -> {
                boolean isMAx5000 = false;

                if (googleMap != null) {
                    for (Machine machine : machines) {
                        String sMarkerColor = "grey";

                        if (machine.getOnline().equals("N")) {
                            sMarkerColor = "grey";
                        } else {
                            if (getTotalQuantity(machine) == 0) {
                                sMarkerColor = "red";
                            } else {
                                sMarkerColor = "green";
                            }
                        }

                        addCustomMarkerToMap(getContext(), sMarkerColor, googleMap, machine);
                        LatLng targetLocation = new LatLng(machine.getLat(), machine.getLon());
                        if (calculateDistance(currentLocation, targetLocation) < 5000) {
                            isMAx5000 = true;
                        }
                    }

                    if (EOrderApplication.lat == 0 || EOrderApplication.lon == 0) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(machines.get(0).getLat(), machines.get(0).getLon()), 13));
                    } else {
                        if (isMAx5000) {
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13));
                        } else {
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 8));
                        }
                    }
                }
            });
        }
    }

    public void openBtn(ConstraintLayout btnOften, boolean isOpen){
        btnOften.setClickable(isOpen);
        btnOften.setFocusable(isOpen);
    }

    private int getTotalQuantity(Machine machine){
        int iQuantity = 0;

        for(int i = 0; i < machine.getProductList().size(); i++){
            iQuantity += Integer.parseInt(machine.getProductList().get(i).getQuantity());
        }

        return iQuantity;
    }

    private Machine getNowMachine(String MachineID){
        Machine machine = null;

        for(int i = 0; i < machines.size(); i++){
            if(machines.get(i).getMachineID().equals(MachineID)){
                machine = machines.get(i);
                break;
            }
        }

        return machine;
    }

    private void addCustomMarkerToMap(Context context, String sColor, GoogleMap googleMap, Machine machine) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(machine.getLat(), machine.getLon()))
                .icon(createCustomMarker(context, sColor))
                .title(machine.getTitle())
                .snippet(machine.getAddress());

        Marker marker = googleMap.addMarker(markerOptions);
        markerMachineMap.put(marker, machine); // 將 Marker 和對應的 Machine 物件放入映射中

        googleMap.setOnMarkerClickListener(now_marker -> {
            Machine clickedMachine = markerMachineMap.get(now_marker); // 根據點擊的 Marker 獲取對應的 Machine 物件
            if (clickedMachine != null) {
                // 顯示底部表單（Bottom Sheet）
                showBottomSheet(now_marker, clickedMachine);
            }
            return false;
        });
    }

    private BitmapDescriptor createCustomMarker(Context context, String sColor) {
        // Load your drawable resource
        int DrawableID = 0;
        switch (sColor){
            case "green":
                DrawableID = R.drawable.location_pin_green;
                break;
            case "red":
                DrawableID = R.drawable.location_pin_red;
                break;
            case "grey":
                DrawableID = R.drawable.location_pin_grey;
                break;
        }
        Drawable drawable = ContextCompat.getDrawable(context, DrawableID); // 替換成您的圖檔 R.drawable.your_custom_marker_icon

        // Convert the drawable to a bitmap
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth() * 2, drawable.getIntrinsicHeight() * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        // Add custom text "好蛋" on the marker
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(32);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Rect bounds = new Rect();
        textPaint.getTextBounds("好蛋", 0, "好蛋".length(), bounds);
        canvas.drawText("好蛋", bitmap.getWidth() / 2, bitmap.getHeight() / 2 + bounds.height() / 3, textPaint);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void showBottomSheet(Marker marker, Machine machine) {
        String sMachineID = machine.getMachineID();

        // 創建底部表單（Bottom Sheet）視圖
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_google_map_layout, null);

        // 顯示底部表單（Bottom Sheet）
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(bottomSheetView);

        // 設置 BottomSheetDialog 的背景為透明
        if (bottomSheetDialog.getWindow() != null) {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        }

        // 獲取底部表單中的元素，並設置相應的內容
        RecyclerView recyclerView = bottomSheetView.findViewById(R.id.recycler_view_products);
        TextView tvTitle = bottomSheetView.findViewById(R.id.bottom_sheet_title);
        TextView tvAddress = bottomSheetView.findViewById(R.id.bottom_sheet_address);
        TextView tvSheetFrequent = bottomSheetView.findViewById(R.id.tv_sheet_frequent);
        ConstraintLayout btnOften = bottomSheetView.findViewById(R.id.cl_sheet_frequent_button);
        ConstraintLayout btnRoute = bottomSheetView.findViewById(R.id.cl_sheet_route_button);

        machine = getNowMachine(sMachineID);

        if(machineMapPresenter.getUserLogin()) {
            //  設置常用圖形
            Drawable drawable = null;
            if (machine.geOftenID().equals("")) {
                drawable = getResources().getDrawable(R.drawable.ic_love_line);
            } else {
                drawable = getResources().getDrawable(R.drawable.ic_love_full);
            }

            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            tvSheetFrequent.setCompoundDrawables(drawable, null, null, null);

            btnOften.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openBtn(btnOften, false);

                    String uid = "";
                    String machine_id = sMachineID;

                    Machine nowmachine = getNowMachine(machine_id);

                    if(nowmachine != null) {
                        if (!nowmachine.geOftenID().equals("")) {
                            uid = nowmachine.geOftenID();
                        }

                        machineMapPresenter.setStoreOften(btnOften, machine_id, uid);

                        //  設置常用圖形
                        Drawable drawable = null;
                        if (nowmachine.geOftenID().equals("")) {
                            drawable = getResources().getDrawable(R.drawable.ic_love_full);
                        } else {
                            drawable = getResources().getDrawable(R.drawable.ic_love_line);
                        }

                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        tvSheetFrequent.setCompoundDrawables(drawable, null, null, null);
                    } else {
                        openBtn(btnOften, true);
                    }
                }
            });

        }else {
            btnOften.setVisibility(View.GONE);
        }

        btnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 創建 Intent 對象以打開 Google Maps 導航
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/dir/?api=1" +
                                "&destination=" + Uri.encode(marker.getSnippet())));

                // 設置 Intent 的標誌，以確保在 Google Maps 中顯示路線
                intent.setPackage("com.google.android.apps.maps");

                // 檢查設備上是否安裝了 Google Maps
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    // 如果安裝了 Google Maps，則啟動 Intent
                    startActivity(intent);
                } else {
                    // 如果設備上沒有安裝 Google Maps，請提供一個提示或使用其他方式來處理
                    new AlertDialog.Builder(fragment.getActivity())
                            .setTitle("尚未安裝Google Map APP")
                            .setPositiveButton(android.R.string.ok, null).show();
                }
            }
        });

        // 設置底部表單中的標題和內容
        tvTitle.setText(marker.getTitle());
        tvAddress.setText(marker.getSnippet());

        // 設置關閉按鈕的點擊事件
        //closeButton.setOnClickListener(view -> bottomSheetDialog.dismiss());

        // 顯示底部表單
        bottomSheetDialog.show();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(machineMapAdapter);

        // 找到 RecyclerView
        machineMapAdapter.setData(machine.getProductList());
    }

    // 計算兩個經緯度位置之間的距離（單位：公尺）
    private float calculateDistance(LatLng currentLocation, LatLng targetLocation) {
        Location current = new Location("");
        current.setLatitude(currentLocation.latitude);
        current.setLongitude(currentLocation.longitude);

        Location target = new Location("");
        target.setLatitude(targetLocation.latitude);
        target.setLongitude(targetLocation.longitude);

        return current.distanceTo(target);
    }

    public void requestUserLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 在這裡處理位置更新
                EOrderApplication.lat = location.getLatitude();  // 獲取緯度
                EOrderApplication.lon = location.getLongitude();  // 獲取經度
                if(EOrderApplication.lat != 0 && EOrderApplication.lon != 0 && !isOneLatLon) {
                    currentLocation = new LatLng(EOrderApplication.lat, EOrderApplication.lon);
                    mapView.getMapAsync(googleMap -> {
                        if (googleMap != null) {
                            // 在地圖上添加目前位置標記
                            isOneLatLon = true;
                            googleMap.addMarker(new MarkerOptions().position(currentLocation).title("目前位置"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13));
                        }
                    });
                }
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
                        100);
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
