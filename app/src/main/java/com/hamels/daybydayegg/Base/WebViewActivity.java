package com.hamels.daybydayegg.Base;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hamels.daybydayegg.R;

import static com.hamels.daybydayegg.Constant.Constant.TITLE;
import static com.hamels.daybydayegg.Constant.Constant.URL;

public class WebViewActivity extends BaseActivity {
    public static final String TAG = WebViewActivity.class.getSimpleName();

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(getIntent().getExtras().getInt(TITLE));
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);
        setAppToolbarVisibility(true);

        webView = findViewById(R.id.web_view);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getIntent().getExtras().getString(URL));
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
