package com.hamels.daybydayegg.Main.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.EOrderApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Cookie;

public class TransRecordFragment extends BaseFragment {
    public static final String TAG = TransRecordFragment.class.getSimpleName();

    private static TransRecordFragment fragment;
    private WebView webView;
    private String mFilter = "";
    public String mOrderType= "G", sOrderID = "", sMealNo = "";
    private static String ORDER_TYPE="ORDER_TYPE", OrderID = "ORDER_ID", MealNo = "MEAL_NO";

    public String  getOrderType(){
        return  mOrderType;
    }

    public static TransRecordFragment getInstance() {
        if (fragment == null) {
            fragment = new TransRecordFragment();
        }
        return fragment;
    }

    public static TransRecordFragment getInstance(String orderType, String order_id, String meal_no) {
        if (fragment == null) {
            fragment = new TransRecordFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_TYPE, orderType);
        bundle.putString(OrderID, order_id);
        bundle.putString(MealNo, meal_no);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        if(getArguments()!=null){
            mOrderType = getArguments().getString(ORDER_TYPE,"G");
            sOrderID = getArguments().getString(OrderID,"");
            sMealNo = getArguments().getString(MealNo,"");
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        webView = view.findViewById(R.id.web_view);
        ((MainActivity) getActivity()).setAppTitle(R.string.trans_record);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).bindWebView(webView);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        //setCookies();
        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_ORDER_URL + "?orderType=" + mOrderType + "&order_id=" + sOrderID + "&meal_no=" + sMealNo);
    }

    private void setCookies() {
        final CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        SharedPrefsCookiePersistor sharedPrefsCookiePersistor = new SharedPrefsCookiePersistor(getContext());
        final List<Cookie> cookies = sharedPrefsCookiePersistor.loadAll();

        cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {
                for (Cookie cookie : cookies) {
                    String cookieString = cookie.name() + "=" + cookie.value() + "; Domain=" + cookie.domain();
                    cookieManager.setCookie(cookie.domain(), cookieString);
                    Log.d(TAG, "cookieString: " + cookieString);
                }
            }
        });
    }


    //MainActivity呼叫用  過濾訂單狀態 傳入後重新撈資料
    public void orderFilterMode(String filter){
        mFilter= filter;
        webView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("filter", mFilter);
                    webView.loadUrl("javascript:" + "appCall_getOrderStatusFilter('" + jsonObject + "')");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
