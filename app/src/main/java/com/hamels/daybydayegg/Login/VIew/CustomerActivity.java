package com.hamels.daybydayegg.Login.VIew;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.Adapter.CustomerListAdapter;
import com.hamels.daybydayegg.Login.Contract.CustomerListContract;
import com.hamels.daybydayegg.Login.Presenter.CustomerListPresenter;
import com.hamels.daybydayegg.Main.View.LocationFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.Product.View.ProductMainTypeFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.Customer;
import com.hamels.daybydayegg.Utils.IntentUtils;
import com.hamels.daybydayegg.Utils.SharedUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerActivity extends BaseActivity implements CustomerListContract.View {
    public static final String TAG = CustomerActivity.class.getSimpleName();

    private static CustomerActivity fragment;
    private RecyclerView recyclerView;

    private com.hamels.daybydayegg.Login.Adapter.CustomerListAdapter CustomerListAdapter;
    private CustomerListContract.Presenter CustomerListPresenter;

    //  Layout
    private ConstraintLayout item_customer;
    private ConstraintLayout layoutCustomer;
    private Spinner spSpinnerCity; // 城市
    private Button btnCutomerSearch;
    private EditText etCustomerName;

    //  Param
    private ArrayAdapter<String> CitySpinnerList;
    private String CUSTOMER_ID = "CUSTOMER_ID";
    private int customer_id = 0;
    public static String sMode = "";
    public static String sSourceActive = "";
    private String sCity = "";

    private static final int REQUEST_LOCATION_PERMISSION = 100;

    @Nullable
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer);
        sMode = Objects.requireNonNull(getIntent().getExtras()).getString("MODE", "");
        //sSourceActive = Objects.requireNonNull(getIntent().getExtras()).getString("SOURCE_ACTIVE", "");

        initView();
    }

    private void initView() {
        CustomerListPresenter = new CustomerListPresenter(this, getRepositoryManager(this), sMode);

        sSourceActive = CustomerListPresenter.getSourceActive();

        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.tab_customer);
        //  setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);
        setAppToolbarVisibility(true);

        requestUserLocation();

        layoutCustomer = findViewById(R.id.layout_customer);
        spSpinnerCity = findViewById(R.id.spinner_city);
        recyclerView = findViewById(R.id.customer_view);
        btnCutomerSearch = findViewById(R.id.btn_cutomer_search);
        etCustomerName = findViewById(R.id.et_customer_name);

        CustomerListAdapter = new CustomerListAdapter(this, CustomerListPresenter, sMode, SharedUtils.getInstance().getLoveCustomer(this));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(CustomerListAdapter);

        if(sMode.equals(("isSetLove"))){
            //  搜尋商家
            layoutCustomer.setVisibility(View.VISIBLE);
        }else{
            //  選擇登入商家(從最愛)
            sMode = "isSelectLoveCustomer";
            layoutCustomer.setVisibility(View.GONE);
        }

        btnCutomerSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerListPresenter.getCustomerList(sMode, sCity, etCustomerName.getText().toString());
            }
        });
    }

    public void goCustomer(){
        if(sSourceActive.equals("Login")){
            IntentUtils.intentToLogin(this, 0);
        }else{

            MainActivity.setCustomerID("");
            onBackPressed();
//            IntentUtils.intentToMain(this, false, sSourceActive, "");
        }
    }

    public void goLocation(String sCustomerID){
        //  強制重新建立
        Fragment frg = this.getSupportFragmentManager().findFragmentByTag(LocationFragment.TAG);
        if(frg != null) {
            getSupportFragmentManager().beginTransaction().remove(frg).commit();
        }
        IntentUtils.intentToMain(this, false, sCustomerID,false, false);
    }

    public void goETicketProductMainType(String sCustomerID){
        //  強制重新建立
        Fragment frg = this.getSupportFragmentManager().findFragmentByTag(ProductMainTypeFragment.TAG);
        if(frg != null) {
            getSupportFragmentManager().beginTransaction().remove(frg).commit();
        }
        IntentUtils.intentToMain(this, false, sCustomerID,false, false);
    }

    @Override
    public void setCustomerList(List<Customer> customers, String sMode) {
        String sLoveCustomer = SharedUtils.getInstance().getLoveCustomer(this);
        List<Customer> newCustomer = new ArrayList<>();

        if(sMode.equals("isSelectLoveCustomer")) {
            int i=0;
            for (Customer customer : customers) {
                //  Log.e(TAG, customer.getCustomerName());
                if (sLoveCustomer.indexOf("|" + customer.getCustomerID() + "|") != -1) {
                    newCustomer.add(i, customer);
                    i++;
                }
            }
        }else{
            newCustomer = customers;
        }

        CustomerListAdapter.setData(newCustomer);
    }

    @Override
    public void setPropertyCode(final List<Address> addressList) {
        final ArrayList<String> CityArrayList = new ArrayList<>();

        CityArrayList.add("全部地區");

        for (int i = 0; i < addressList.size(); i++) {
            if (!CityArrayList.contains(addressList.get(i).getCity())) {
                CityArrayList.add(addressList.get(i).getCity());
            }
        }

        CitySpinnerList = new ArrayAdapter<>(this,
                R.layout.spinner_style,
                CityArrayList);
        spSpinnerCity.setAdapter(CitySpinnerList);
        //點選下拉選單後觸發事件
        spSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    sCity = "";
                }else{
                    sCity = addressList.get(position - 1).getCity();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void requestUserLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

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
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Ask for permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
                return;
            }
        } else {
            // 已經有權限，可以繼續操作
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // 請求權限
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }
}