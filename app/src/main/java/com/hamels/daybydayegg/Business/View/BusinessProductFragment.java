package com.hamels.daybydayegg.Business.View;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Business.Adapter.BusinessProductListAdapter;
import com.hamels.daybydayegg.Business.Contract.BusinessProductContract;
import com.hamels.daybydayegg.Business.Presenter.BusinessProductPresenter;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Repository.Model.ProductType;

import java.util.ArrayList;
import java.util.List;

public class BusinessProductFragment extends BaseFragment implements BusinessProductContract.View{
    public static final String TAG = BusinessProductFragment.class.getSimpleName();

    private static BusinessProductFragment fragment;
    private BusinessProductContract.Presenter productPresenter;
    private RecyclerView recyclerView;
    private BusinessProductListAdapter businessProductListAdapter;
    private ImageView img_go_top;
    private TabLayout tabLayout;

    private static final String BUSINESS_SALE_ID = "business_sale_id";
    private static final String PRODUCT_TYPE_MAIN_ID = "product_type_main_id";
    private static final String IS_E_TICKET = "isETicket";
    private int product_type_main_id = 0;
    private String location_id = "";
    private String business_sale_id = "";
    private String sort = "NEW";
    private String type_id ="";
    private String isETicket= "N";

    public static BusinessProductFragment getInstance(int product_type_main_id, String mIsETicket) {
        if (fragment == null) {
            fragment = new BusinessProductFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_TYPE_MAIN_ID, product_type_main_id);
        bundle.putString(IS_E_TICKET, mIsETicket);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static BusinessProductFragment getInstance() {
        if (fragment == null) {
            fragment = new BusinessProductFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        if(getArguments()!=null){
            product_type_main_id = getArguments().getInt(PRODUCT_TYPE_MAIN_ID, 0);
            business_sale_id = getArguments().getString(BUSINESS_SALE_ID,"");
            isETicket = getArguments().getString(IS_E_TICKET,"N");
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_shop);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        Log.e(TAG,"product_type_main_id : " + product_type_main_id + "business_sale_id : " + business_sale_id);
        tabLayout = view.findViewById(R.id.tab_layout);
        recyclerView = view.findViewById(R.id.product_recycler_view);
        img_go_top = view.findViewById(R.id.img_go_top);
        img_go_top.bringToFront();
        img_go_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });
        productPresenter = new BusinessProductPresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);
        businessProductListAdapter = new BusinessProductListAdapter(productPresenter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(businessProductListAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productPresenter.getTypeList(product_type_main_id);
    }

    @Override
    public void setProductTypeList(List<ProductType> productTypeList) {
        tabLayout.addTab(tabLayout.newTab().setText("全部").setTag(""));
        for(int i = 0 ; i < productTypeList.size() ; i++){
            tabLayout.addTab(tabLayout.newTab().setText(productTypeList.get(i).getType_name()).setTag(Integer.toString(productTypeList.get(i).getId())));
        }
        //init 第一個tab(全部) 的 selected font
        TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.noto_sans_tc_medium);
        title.setTypeface(typeface);
        //
        productPresenter.getProductList(location_id,sort , Integer.toString(product_type_main_id) , "" , "" , business_sale_id, isETicket);

        //上方分頁功能監聽觸發事件
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type_id = tab.getTag().toString();
                productPresenter.getProductList(location_id,sort , Integer.toString(product_type_main_id) , type_id , "" , business_sale_id, isETicket);
                //改變Unselected tab font
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.noto_sans_tc_medium);
                title.setTypeface(typeface);
                //
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //改變Unselected tab font
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.noto_sans_tc_regular);
                title.setTypeface(typeface);
                //
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void goPageProductDetail(int product_id) {
        ((MainActivity) getActivity()).addFragment(BusinessProductDetailFragment.getInstance(product_id , isETicket));
    }

    @Override
    public void setProductList(List<Product> productList) {
        for(int i = 0 ; i < productList.size() ; i++){
            Log.e(TAG,productList.get(i).getProduct_name());
        }
        List<Product> productleft = new ArrayList<>();
        List<Product> productright = new ArrayList<>();
        for(int i = 0 ; i < productList.size() ; i++){
            if(i % 2 == 0){
                productleft.add(productList.get(i));
            }
            else{
                productright.add(productList.get(i));
            }
        }
        businessProductListAdapter.setProduct(productleft ,productright);
        recyclerView.scrollToPosition(0);
    }

    //MainActivity呼叫用  排序 傳入後重新排序
    public void SortMode(String sSortMode){
        Log.e(TAG,sSortMode);
        sort = sSortMode;
        productPresenter.getProductList(location_id,sort , Integer.toString(product_type_main_id) , type_id , "" , business_sale_id, isETicket);
    }
}



