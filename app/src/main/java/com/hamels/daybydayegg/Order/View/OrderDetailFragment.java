package com.hamels.daybydayegg.Order.View;

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
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.R;

import java.util.Objects;

public class OrderDetailFragment extends BaseFragment {
    public static final String TAG = OrderDetailFragment.class.getSimpleName();

    private static OrderDetailFragment fragment;
    private WebView webView;

    private static final String ORDERID = "orderid";
    private static final String MEALNO = "meal_no";
    private String orderid = "";
    private String meal_no = "";
    private String order_source = "";

    public static OrderDetailFragment getInstance() {
        if (fragment == null) {
            fragment = new OrderDetailFragment();
        }

        return fragment;
    }

    public static OrderDetailFragment getInstance(String orderid, String meal_no, String order_source) {
        Log.e(TAG, "----------------------------" + orderid);

        if (fragment == null) {
            fragment = new OrderDetailFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putString(ORDERID, orderid);
        bundle.putString(MEALNO, meal_no);
        bundle.putString("ORDERSOURCE", order_source);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cketitor, container, false);

        orderid = getArguments().getString(ORDERID, "");
        meal_no = getArguments().getString(MEALNO, "");
        order_source = getArguments().getString("ORDERSOURCE", "");

        initView(view);

        return view;
    }

    private void initView(View view) {
        webView = view.findViewById(R.id.web_view);
        setAppTitle(R.string.trans_record);
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

        //Toast.makeText(getActivity(), "order_id: " + orderid, Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(), "meal_no: " + meal_no, Toast.LENGTH_LONG).show();

        EOrderApplication.OrderListTag = "";
        EOrderApplication.OrderListScrollTop = "0";
        Log.e(TAG, "WebView Url : " + EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_ORDERDETAIL_URL + "?order_id=" + orderid + "&meal_no=" + meal_no + "&order_source=" + order_source);
        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_ORDERDETAIL_URL + "?order_id=" + orderid + "&meal_no=" + meal_no + "&order_source=" + order_source);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }
}
