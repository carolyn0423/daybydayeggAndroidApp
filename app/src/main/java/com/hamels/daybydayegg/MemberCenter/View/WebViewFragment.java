package com.hamels.daybydayegg.MemberCenter.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.R;

import org.json.JSONObject;

import java.util.Objects;

public class WebViewFragment extends BaseFragment {
    public static final String TAG = WebViewFragment.class.getSimpleName();
    private static final String TITLE = "title";
    private static final String URL = "url";
    private static final String NEWS_ID = "news_id";

    private static WebViewFragment fragment;

    private String url = "";
    private @StringRes
    int resTitleString = 0;
    private String news_id = "";

    public static WebViewFragment getInstance(@StringRes int title, String url) {
        if (fragment == null) {
            fragment = new WebViewFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, title);
        bundle.putString(URL, url);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static WebViewFragment getInstance(@StringRes int title, String url, String news_id) {
        if (fragment == null) {
            fragment = new WebViewFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, title);
        bundle.putString(URL, url);
        bundle.putString(NEWS_ID, news_id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        if (getArguments() != null) {
            url = getArguments().getString(URL, "");
            resTitleString = getArguments().getInt(TITLE, 0);
            news_id = getArguments().getString(NEWS_ID, "");
            initView(view);
        }
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(View view) {
        WebView webView = view.findViewById(R.id.web_view);
        setAppTitle(resTitleString);
        setBackButtonVisibility(true);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        setSortButtonVisibility(false);
        setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                result.cancel();
                return true;
            }
        });
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebContentsDebuggingEnabled(true);
        webView.addJavascriptInterface(new AndroidJsInterface(), "hamels");
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient());
        if (news_id != "") {
            webView.loadUrl(url + "?uid=" + news_id);
        } else {
            webView.loadUrl(url);
        }

    }

    public class AndroidJsInterface {
        @JavascriptInterface
        public String jsCall_getVariable(String Info) {
            Log.e(TAG, "1");
            String sData = "";
            try {
                Log.e(TAG, "1.5");
                JSONObject oMemberData = new JSONObject(((MainActivity) getActivity()).getUser().toString());
                sData = oMemberData.getString(Info);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
//                e.printStackTrace();
            }
            Log.e(TAG, "2");
            return sData;
        }

//        @JavascriptInterface
//        public String jsCall_getConnectName() {
//            return EOrderApplication.dbConnectName;
//        }
//        @JavascriptInterface
//        public void jsCall_goLoginPage(String page, String function) {
//            Log.e(TAG, "JsCallgoPage");
//            ((MainActivity) getActivity()).goPage();
//        }
//
//        @JavascriptInterface
//        public void jsCall_goOrderPage(String queryParam) {
//
//            ((MainActivity) getActivity()).goOrderPage(orderType, OrderID, MealNo);
//        }
//
//        @JavascriptInterface
//        public void jsCall_goShopPage(String salesType) {
//            String isETicket= salesType.equals("E") ? "Y" :"N";
//            ((MainActivity) getActivity()).goProductPage(isETicket);
//        }
//
//        @JavascriptInterface
//        public void jsCall_setShopCartNumToApp() {
//            ((MainActivity) getActivity()).refreshBadge();
//        }
    }
}
