package com.hamels.daybydayegg.Main.View;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.R;

import java.util.Objects;

public class MessageFragment extends BaseFragment {
    public static final String TAG = MessageFragment.class.getSimpleName();

    private static MessageFragment fragment;
    private WebView webView;

    public static MessageFragment getInstance() {
        if (fragment == null) {
            fragment = new MessageFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cketitor, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        webView = view.findViewById(R.id.web_view);
        setAppTitle(R.string.message_list);
        setBackButtonVisibility(true);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).bindWebView(webView);

//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (isAdded()) {
                    ((MainActivity) getActivity()).setBackButtonVisibility(view.canGoBack());
                    ((MainActivity) getActivity()).setTopBarVisibility(false);
                    ((MainActivity) getActivity()).setAppToolbarVisibility(true);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isAdded()) {
                    ((MainActivity) getActivity()).setBackButtonVisibility(view.canGoBack());
                    ((MainActivity) getActivity()).setTopBarVisibility(false);
                    ((MainActivity) getActivity()).setAppToolbarVisibility(true);
                    ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
                }
            }
        });
        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_MESSAGE_URL);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }
}
