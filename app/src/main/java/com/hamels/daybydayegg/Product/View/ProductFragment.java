package com.hamels.daybydayegg.Product.View;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.Product.Adapter.ProductETicketListAdapter;
import com.hamels.daybydayegg.Product.Adapter.ProductListAdapter;
import com.hamels.daybydayegg.Product.Contract.ProductContract;
import com.hamels.daybydayegg.Product.Presenter.ProductPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Repository.Model.ProductType;

import java.util.List;

public class ProductFragment extends BaseFragment implements ProductContract.View{
    public static final String TAG = ProductFragment.class.getSimpleName();

    private static ProductFragment fragment;
    private ProductContract.Presenter productPresenter;
    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private ProductETicketListAdapter productETicketListAdapter;
    private ImageView img_go_top;
    private TabLayout tabLayout;

    private static final String PRODUCT_TYPE_MAIN_ID = "product_type_main_id";
    private static final String LOCATION_ID = "location_id";
    private static final String IS_E_TICKET = "isETicket";
    private int product_type_main_id = 0;
    private String location_id = "";
    private String location_name = "";
    private String sort = "NEW";
    private String type_id ="";
    private String isETicket= "N";

    public static ProductFragment getInstance(String location_id,int product_type_main_id, String mIsETicket) {
        if (fragment == null) {
            fragment = new ProductFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(LOCATION_ID, location_id);
        bundle.putInt(PRODUCT_TYPE_MAIN_ID, product_type_main_id);
        bundle.putString(IS_E_TICKET, mIsETicket);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ProductFragment getInstance() {
       if (fragment == null) {
            fragment = new ProductFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        location_id = getArguments().getString(LOCATION_ID, "");
        product_type_main_id = getArguments().getInt(PRODUCT_TYPE_MAIN_ID, 0);
        isETicket = getArguments().getString(IS_E_TICKET,"N");
        type_id ="";
        initView(view);

        return view;

    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
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
        productPresenter = new ProductPresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        if(isETicket.equals("Y")){
            ((MainActivity) getActivity()).setAppTitle(R.string.tab_ticket);
            productETicketListAdapter = new ProductETicketListAdapter(productPresenter);
            recyclerView.setAdapter(productETicketListAdapter);
        }else{
            ((MainActivity) getActivity()).setAppTitle(R.string.tab_shop);
            productListAdapter = new ProductListAdapter(productPresenter);
            recyclerView.setAdapter(productListAdapter);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productPresenter.getTypeList(product_type_main_id);
    }

    @Override
    public void setProductTypeList(List<ProductType> productTypeList) {
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("全部").setTag(""));
        for(int i = 0 ; i < productTypeList.size() ; i++){
            tabLayout.addTab(tabLayout.newTab().setText(productTypeList.get(i).getType_name()).setTag(Integer.toString(productTypeList.get(i).getId())));
        }
        //init 第一個tab(全部) 的 selected font
        if(getActivity()!=null){
            TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
            Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.noto_sans_tc_medium);
            title.setTypeface(typeface);
        }
        //
        productPresenter.getProductList(location_id,sort , Integer.toString(product_type_main_id) , "" , "", isETicket);

        //上方分頁功能監聽觸發事件
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type_id = tab.getTag().toString();
                productPresenter.getProductList(location_id,sort , Integer.toString(product_type_main_id) , type_id , "", isETicket);
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
        ((MainActivity) getActivity()).addFragment(ProductDetailFragment.getInstance(product_id, isETicket));
    }

    @Override
    public void setProductList(List<Product> productList) {
        if(isETicket.equals("Y")){
            productETicketListAdapter.setProduct(productList);
        }else{
            productListAdapter.setProduct(productList);
        }
        recyclerView.scrollToPosition(0);
    }

    //MainActivity呼叫用  排序 傳入後重新排序
    public void SortMode(String sSortMode){
        sort = sSortMode;
        productPresenter.getProductList(location_id,sort , Integer.toString(product_type_main_id) , type_id , "", isETicket);
    }
}



