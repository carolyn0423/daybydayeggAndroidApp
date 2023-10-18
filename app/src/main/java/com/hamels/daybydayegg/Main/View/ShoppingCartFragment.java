package com.hamels.daybydayegg.Main.View;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Widget.AppToolbar;

import java.util.Objects;

public class ShoppingCartFragment extends BaseFragment {
    public static final String TAG = ShoppingCartFragment.class.getSimpleName();

    private static ShoppingCartFragment fragment;
    private WebView webView;
    protected AppToolbar appToolbar;
    private static final String ORDERTYPE = "orderType";
    private String orderType = "";

    public static ShoppingCartFragment getInstance() {
        if (fragment == null) {
            fragment = new ShoppingCartFragment();
        }

        return fragment;
    }

    public static ShoppingCartFragment getInstance(String orderType) {
        Log.e(TAG, "----------------------------" + orderType);

        if (fragment == null) {
            fragment = new ShoppingCartFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putString(ORDERTYPE, orderType);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        orderType = "G";

        //  orderType = getArguments().getString("G", "");

        initView(view);

        return view;
    }

    private void initView(View view) {
        webView = view.findViewById(R.id.web_view);
        //setAppTitle(R.string.tab_shopping_cart);
        ((MainActivity) getActivity()).refreshBadge();
        setBackButtonVisibility(true);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).bindWebView(webView);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        switch (orderType) {
            case "G":
                webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_SHOPPING_CART_URL);
                break;
            case "E":
                webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_SHOPPING_CART_URL2);
                break;
            default:
                webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_SHOPPING_CART_URL);
                break;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }

    public void setShoppingCartAppTitle(String sAppTitle){
        //setAppTitleString("購物車 - " + sAppTitle);
    }
}
