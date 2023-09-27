package com.hamels.daybydayegg.Utils;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArticleWebViewClient extends WebViewClient {

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        //重置webview中img标签的图片大小
        view.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++) " +
                "{"
                + "var img = objs[i]; " +
                " img.style.maxWidth = '100%'; img.style.height = 'auto'; " +
                "}" +
                "})()");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}