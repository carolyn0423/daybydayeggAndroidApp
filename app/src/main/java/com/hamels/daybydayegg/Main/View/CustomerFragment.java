package com.hamels.daybydayegg.Main.View;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Main.Adapter.CustomerAdapter;
import com.hamels.daybydayegg.Main.Contract.CustomerContract;
import com.hamels.daybydayegg.Main.Presenter.CustomerPresenter;
import com.hamels.daybydayegg.Product.View.ProductMainTypeFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerFragment extends BaseFragment implements CustomerContract.View {
    public static final String TAG = CustomerFragment.class.getSimpleName();

    private static CustomerFragment fragment;
    private RecyclerView recyclerView;

    private CustomerPresenter Presenter;
    private CustomerAdapter Adapter;

    //  Layout
    private ConstraintLayout item_customer;
    private ConstraintLayout layoutCustomer;
    private Spinner spSpinnerCity; // 城市
    private Button btnCutomerSearch;
    private EditText etCustomerName;

    //  Param
    private ArrayAdapter<String> CitySpinnerList;
    public static String sMode = "";
    public static String sSourceActive = "";
    private String lat = "";
    private String lon = "";
    private String sCity = "";

    public static CustomerFragment getInstance() {
        if (fragment == null) {
            fragment = new CustomerFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_customer);
        ((MainActivity) getActivity()).refreshBadge();

        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);

        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);

        layoutCustomer = view.findViewById(R.id.layout_customer);
        spSpinnerCity = view.findViewById(R.id.spinner_city);
        recyclerView = view.findViewById(R.id.customer_view);
        btnCutomerSearch = view.findViewById(R.id.btn_cutomer_search);
        etCustomerName = view.findViewById(R.id.et_customer_name);

        Presenter = new CustomerPresenter(this, getRepositoryManager(getContext()));
        Adapter = new CustomerAdapter(this, Presenter);

        sSourceActive = Presenter.getSourceActive();

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(Adapter);

        if(sSourceActive.equals("PRODUCT_WELCOME") || sSourceActive.equals("ETICKET_WELCOME") || sSourceActive.equals("LOCATION_WELCOME")){
            //  搜尋商家
            layoutCustomer.setVisibility(View.VISIBLE);
        }else{
            //  選擇登入商家(從最愛)
            layoutCustomer.setVisibility(View.GONE);
        }

        btnCutomerSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.getCustomerList(sMode, sCity, etCustomerName.getText().toString(), lat, lon);
            }
        });
    }

    public void goCustomer(){
        if(sSourceActive.equals("Login")){
            Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
            fragment.getActivity().startActivityForResult(intent, 0);
        }else{
            MainActivity.setCustomerID("");
        }
    }

    public void goLocation(String sCustomerID){
        ((MainActivity) getActivity()).addFragment(LocationFragment.getInstance());
    }

    public void goETicketProductMainType(String sCustomerID){
        ((MainActivity) getActivity()).addFragment(ProductMainTypeFragment.getInstance());
    }

    @Override
    public void setCustomerList(List<Customer> customers) {
        Adapter.setData(customers);
    }

    @Override
    public void setPropertyCode(final List<Address> addressList) {
        final ArrayList<String> CityArrayList = new ArrayList<>();

        CityArrayList.add("全部地區");

        if(getActivity() != null) {
            for (int i = 0; i < addressList.size(); i++) {
                if (!CityArrayList.contains(addressList.get(i).getCity())) {
                    CityArrayList.add(addressList.get(i).getCity());
                }
            }

            CitySpinnerList = new ArrayAdapter<>(getActivity(),
                    R.layout.spinner_style,
                    CityArrayList);
            spSpinnerCity.setAdapter(CitySpinnerList);
            //點選下拉選單後觸發事件
            spSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        sCity = "";
                    } else {
                        sCity = addressList.get(position - 1).getCity();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

}
