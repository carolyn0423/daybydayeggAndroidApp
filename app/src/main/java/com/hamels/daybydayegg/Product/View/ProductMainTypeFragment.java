package com.hamels.daybydayegg.Product.View;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.Main.View.MainIndexFragment;
import com.hamels.daybydayegg.Product.Adapter.ProductETicketListAdapter;
import com.hamels.daybydayegg.Product.Adapter.ProductListAdapter;
import com.hamels.daybydayegg.Product.Adapter.ProductMainTypeAdapter;
import com.hamels.daybydayegg.Product.Contract.ProductContract;
import com.hamels.daybydayegg.Product.Contract.ProductMainTypeContract;
import com.hamels.daybydayegg.Product.Presenter.ProductMainTypePresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.ApiRepository.ApiRepository;
import com.hamels.daybydayegg.Repository.ApiRepository.MemberRepository;
import com.hamels.daybydayegg.Repository.Model.ProductMainType;

import java.util.ArrayList;
import java.util.List;

public class ProductMainTypeFragment extends BaseFragment implements ProductMainTypeContract.View{
    public static final String TAG = ProductMainTypeFragment.class.getSimpleName();

    private static ProductMainTypeFragment fragment;
    private ProductMainTypeContract.Presenter productMainTypePresenter;
    private ProductContract.Presenter productPresenter;
    private RecyclerView recyclerView;

    private ProductMainTypeAdapter productMainTypeAdapter;
    private ProductListAdapter productListAdapter;
    private ProductETicketListAdapter productETicketListAdapter;
    private ImageView img_go_top;
    private Group noLocationGroup;
    private TextView tvGoHome;

    private int location_id = 0;

    public String isETicket= "";

    public static ProductMainTypeFragment getInstance() {
        if (fragment == null) {
            fragment = new ProductMainTypeFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_main_type, container, false);
        initView(view);

        return view;

    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        noLocationGroup = view.findViewById(R.id.no_location_group);

        //  清除API 暫存
        ApiRepository.repository = null;
        MemberRepository.memberRepository = null;
        ApiRepository.getInstance();
        MemberRepository.getInstance();

        productMainTypePresenter = new ProductMainTypePresenter(this, getRepositoryManager(getContext()));
        productMainTypeAdapter = new ProductMainTypeAdapter(productMainTypePresenter, isETicket);

        String sLocationID = productMainTypePresenter.getFragmentMainType("LOCATIONID");

        location_id = sLocationID.equals("") ? 0 : Integer.parseInt(sLocationID);
        isETicket = productMainTypePresenter.getFragmentMainType("ISETICKET");

        if(isETicket.equals("N")){
            ((MainActivity) getActivity()).setAppTitle(R.string.tab_shop);
        }else{
            ((MainActivity) getActivity()).setAppTitle(R.string.tab_ticket);
        }

        recyclerView = view.findViewById(R.id.product_recycler_view);
        img_go_top = view.findViewById(R.id.img_go_top);
        img_go_top.bringToFront();
        img_go_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });

        tvGoHome = view.findViewById(R.id.tv_go_home);
        tvGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeTabFragment(MainIndexFragment.getInstance());
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(productMainTypeAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productMainTypePresenter.getProductMainTypeList(Integer.toString(location_id));
    }

    //取得菜單分類列表
    @Override
    public void setProductMainTypeList(List<ProductMainType> mainTypeList) {
        hideSoftKeyboard(((MainActivity) getActivity()));
        setClearList(mainTypeList.size()>0, true);

        if (mainTypeList.size()>0){
            List<ProductMainType> mainTypeleft = new ArrayList<>();
            List<ProductMainType> mainTyperight = new ArrayList<>();
            for(int i = 0 ; i < mainTypeList.size() ; i++){
                Log.e(TAG,mainTypeList.get(i).getPicture_url());

                recyclerView.setBackgroundResource(R.color.white);

                if(i % 2 == 0){
                    mainTypeleft.add(mainTypeList.get(i));
                }
                else{
                    mainTyperight.add(mainTypeList.get(i));
                }
            }
            productMainTypeAdapter.setProductMainType(mainTypeleft ,mainTyperight);
            recyclerView.scrollToPosition(0);
        }

        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
    }

    @Override
    public void getProductList(int product_type_main_id) {
        ((MainActivity) getActivity()).addFragment(ProductFragment.getInstance(Integer.toString(location_id),product_type_main_id, isETicket));
    }

    private void hideSoftKeyboard(Activity mActivity) {
        if(mActivity!=null){
            InputMethodManager inputMethodManager =(InputMethodManager) mActivity.getSystemService(
                    Activity.INPUT_METHOD_SERVICE);
            View focusedView = getActivity().getCurrentFocus();
            if(focusedView!=null){
                inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    private void setClearList(boolean atLeastOneItem, boolean isText){
        if(atLeastOneItem){
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.GONE);
        }
    }

    public void setOnlineShoppingFlag(boolean isFlag){
        if(isFlag) {
            noLocationGroup.setVisibility(getView().GONE);
        }else{
            noLocationGroup.setVisibility(getView().VISIBLE);
        }
    }
}



