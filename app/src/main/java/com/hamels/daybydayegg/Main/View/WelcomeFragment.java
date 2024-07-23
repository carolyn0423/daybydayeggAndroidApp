package com.hamels.daybydayegg.Main.View;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Main.Contract.WelcomeContract;
import com.hamels.daybydayegg.Main.Presenter.WelcomePresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Utils.SharedUtils;
import com.hamels.daybydayegg.Utils.ViewUtils;

public class WelcomeFragment extends BaseFragment implements WelcomeContract.View {
    public static final String TAG = WelcomeFragment.class.getSimpleName();

    private static WelcomeFragment fragment;
    private TextView tvEOrder, tvTitleHint, tvSearchCustomer, tvScan, tvWalkAround;
    public static String sSourceActive = "";
    private WelcomeContract.Presenter welcomePresenter;

    public static WelcomeFragment getInstance() {
        if (fragment == null) {
            fragment = new WelcomeFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        welcomePresenter = new WelcomePresenter(this, getRepositoryManager(getContext()));
        initView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");

        ((MainActivity) getActivity()).getCustomer();
        initView(getView());
    }

    private void initView(View view) {
        sSourceActive = welcomePresenter.getSourceActive();
        ((MainActivity) getActivity()).setAppTitle(R.string.select_customer);
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setBackButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(false);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(false);
        ((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        tvTitleHint = view.findViewById(R.id.tv_title_hint);
        ViewUtils.addUnderLine(tvTitleHint);

        //  選擇登入商家(從最愛挑選)
        tvEOrder = view.findViewById(R.id.tv_eorder);
        String sLoveCustomer = SharedUtils.getInstance().getLoveCustomer(getContext());
        tvEOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sLoveCustomer.equals((""))){
                    ((MainActivity) getActivity()).showErrorAlert("請添加最愛商家");
                }else{
                    ((MainActivity) getActivity()).initSelectCustomer("isSelectLoveCustomer");
                }

            }
        });
        ViewUtils.addUnderLine(tvEOrder);

        //  商家搜尋
        tvSearchCustomer = view.findViewById(R.id.tv_search_customer);
        tvSearchCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).initSelectCustomer("isSetLove");
            }
        });
        ViewUtils.addUnderLine(tvSearchCustomer);

        //  掃碼
        tvScan = view.findViewById(R.id.tv_scan);
        tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), qrCode, Toast.LENGTH_SHORT).show();
                //initScanner();
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        tvWalkAround = view.findViewById(R.id.tv_walk_around);
        tvWalkAround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EOrderApplication.CUSTOMER_ID == ""){
                    ((MainActivity) getActivity()).showErrorAlert("請選擇商家");
                }else{
                    //  go 逛逛
                    String isETICKET = "";
                    if(sSourceActive.equals("PRODUCT")){
                        isETICKET = "N";
                    }else{
                        isETICKET = "Y";
                    }

                    ((MainActivity) getActivity()).checkMerchantCount(sSourceActive, isETICKET);
                }

            }
        });


        if(sSourceActive.equals("PRODUCT")){
           // ((MainActivity) getActivity()).changeNavigationColor(R.id.order);
        }else{
            ((MainActivity) getActivity()).changeNavigationColor(R.id.shop);
        }

        if(welcomePresenter.getUserInfo()){
            if(sSourceActive.equals("PRODUCT")){
                ((MainActivity) getActivity()).checkMerchantCount(sSourceActive, "N");
            }else{
                ((MainActivity) getActivity()).checkMerchantCount(sSourceActive, "Y");
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!= null)
        {
            if (result.getContents()==null)
            {
                Toast.makeText(getActivity(), "You cancelled the scanning", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(), "掃描結果 : " + result.getContents(), Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).getCheckCustomerNo(result.getContents());
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
