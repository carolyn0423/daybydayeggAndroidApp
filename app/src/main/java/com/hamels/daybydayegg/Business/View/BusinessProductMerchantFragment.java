package com.hamels.daybydayegg.Business.View;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Business.Adapter.BusinessMerchantListAdapter;
import com.hamels.daybydayegg.Business.Contract.BusinessProductMerchantContract;
import com.hamels.daybydayegg.Business.Presenter.BusinessProductMerchantPresenter;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Merchant;
import com.hamels.daybydayegg.Repository.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class BusinessProductMerchantFragment extends BaseFragment implements BusinessProductMerchantContract.View{
    public static final String TAG = BusinessProductMerchantFragment.class.getSimpleName();

    private static BusinessProductMerchantFragment fragment;
    private static final String BUSINESS_SALE_ID = "business_sale_id";
    private BusinessProductMerchantContract.Presenter productmerchantPresenter;
    private RecyclerView recyclerView;
    private EditText edit_keyword;
    private TextView txtProductNoResult;
    private String business_sale_id;
    private String isETicket= "N";
    private static final String IS_E_TICKET = "isETicket";

    Drawable drawable;
    private ConstraintLayout layout_clean;
    private BusinessMerchantListAdapter businessMerchantListAdapter;
    public static BusinessProductMerchantFragment getInstance(String business_sale_id, String mIsETicket) {
        if (fragment == null) {
            fragment = new BusinessProductMerchantFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putString(BUSINESS_SALE_ID, business_sale_id);
        bundle.putString(IS_E_TICKET, mIsETicket);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static BusinessProductMerchantFragment getInstance() {
        if (fragment == null) {
            fragment = new BusinessProductMerchantFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_merchant, container, false);
        business_sale_id = getArguments().getString(BUSINESS_SALE_ID, "");
        isETicket = getArguments().getString(IS_E_TICKET,"N");
        initView(view);
        return view;
    }
    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_shop);
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setBackButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        //((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        Log.e(TAG,business_sale_id);

        edit_keyword = view.findViewById(R.id.edit_keyword);
        recyclerView = view.findViewById(R.id.product_recycler_view);
        txtProductNoResult= view.findViewById(R.id.product_no_result);
        layout_clean = view.findViewById(R.id.layout_clean);
        layout_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_keyword.setText("");
                productmerchantPresenter.getMerchantList();
                layout_clean.setVisibility(View.GONE);
            }
        });

        //監聽 EditText Focus事件
        View.OnFocusChangeListener mFocusChangedListener = null;

        mFocusChangedListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    layout_clean.setVisibility(View.VISIBLE);
                }else {
                    layout_clean.setVisibility(View.GONE);
                }
            }
        };
        edit_keyword.setOnFocusChangeListener(mFocusChangedListener);
        //監聽 EditText Focus事件

        edit_keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_clean.setVisibility(View.VISIBLE);
                setClearList(false, false);
            }
        });

        //監聽送出事件
        edit_keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(!v.getText().toString().equals("")){
                    productmerchantPresenter.getProductList(Integer.toString(v.getId()),"NEW","" , "" , v.getText().toString() , business_sale_id, isETicket);
                }
                return false;
            }
        });

        productmerchantPresenter = new BusinessProductMerchantPresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);
        businessMerchantListAdapter = new BusinessMerchantListAdapter(productmerchantPresenter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(businessMerchantListAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productmerchantPresenter.getMerchantList();
    }


    //取得品牌列表
    @Override
    public void setMerchantlList(List<Merchant> merchantlList) {
        hideSoftKeyboard(((MainActivity) getActivity()));
        setClearList(merchantlList.size()>0, true);

        Log.e(TAG,merchantlList.get(0).getLocation_name());
        Log.e(TAG,""+(merchantlList.size()>0));

        if (merchantlList.size()>0){
            Resources res = this.getResources();
            drawable = res.getDrawable(R.color.white);
            recyclerView.setBackground(drawable);
            List<Merchant> merchantsleft = new ArrayList<>();
            List<Merchant> merchantsright = new ArrayList<>();
            Log.e(TAG,merchantlList.get(0).getPicture_url());
            for(int i = 0 ; i < merchantlList.size() ; i++){
                Log.e(TAG,merchantlList.get(i).getPicture_url());

                if(i % 2 == 0){
                    merchantsleft.add(merchantlList.get(i));
                }
                else{
                    merchantsright.add(merchantlList.get(i));
                }
            }
            businessMerchantListAdapter.setMerchant(merchantsleft ,merchantsright);
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void goPageProductList(int product_type_main_id) {
        edit_keyword.setText("");
        ((MainActivity) getActivity()).addFragment(BusinessProductFragment.getInstance(product_type_main_id, isETicket));
    }

    @Override
    public void goPageProductDetail(int product_id) {
        edit_keyword.setText("");
        ((MainActivity) getActivity()).addFragment(BusinessProductDetailFragment.getInstance(product_id, isETicket));
    }

    //品牌列表頁面EditText送出後搜尋商品
    @Override
    public void setProductList(List<Product> productList) {
        hideSoftKeyboard(((MainActivity) getActivity()));
        setClearList(productList.size() > 0, true);

        if(productList.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            txtProductNoResult.setVisibility(View.GONE);

            Resources res = this.getResources();
            drawable = res.getDrawable(R.color.greyBackground);
            recyclerView.setBackground(drawable);
            List<Product> productleft = new ArrayList<>();
            List<Product> productright = new ArrayList<>();
            if (productList.size() < 1){
                Log.e(TAG,"clean");
                businessMerchantListAdapter.clean();
            }
            else{
                for(int i = 0 ; i < productList.size() ; i++){
                    if(i % 2 == 0){
                        productleft.add(productList.get(i));
                    }
                    else{
                        productright.add(productList.get(i));
                    }
                }
            }
            businessMerchantListAdapter.setProduct(productleft ,productright);
            recyclerView.scrollToPosition(0);
        }
    }
    public void hideSoftKeyboard(Activity mActivity) {
        InputMethodManager inputMethodManager =(InputMethodManager) mActivity.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        View focusedView = getActivity().getCurrentFocus();
        if(focusedView!=null){
            inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void setClearList(boolean atLeastOneItem, boolean isText){
        if(atLeastOneItem){
            recyclerView.setVisibility(View.VISIBLE);
            txtProductNoResult.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.GONE);
            txtProductNoResult.setVisibility(View.VISIBLE);
        }

        if(isText){
            txtProductNoResult.setText(R.string.product_no_result);
        }else{
            txtProductNoResult.setText("");
        }
    }
}

