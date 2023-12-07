package com.hamels.daybydayegg.Main.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Product.View.ProductFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Widget.AppToolbar;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.Objects;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_SHOPPING_CART;

public class ShoppingCartFragment extends BaseFragment {
    public static final String TAG = ShoppingCartFragment.class.getSimpleName();

    private static ShoppingCartFragment fragment;
    private WebView webView;
    protected AppToolbar appToolbar;
    private static final String ORDERTYPE = "orderType";
    private String orderType = "";
    private RepositoryManager repositoryManager;

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

        orderType = "E";

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

        //webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_SHOPPING_CART_URL2);

        if(EOrderApplication.CUSTOMER_ID.equals("") || EOrderApplication.sApiUrl.equals("") || EOrderApplication.dbConnectName.equals("") ||
                repositoryManager.getCustomerID().equals("") || !repositoryManager.getUserLogin()){
            new androidx.appcompat.app.AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("登入資訊不完整，請重新登入")
                    .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
                            fragment.getActivity().startActivityForResult(intent, REQUEST_SHOPPING_CART);
                        }
                    }).show();
        }else {
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
