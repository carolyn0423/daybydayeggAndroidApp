package com.hamels.daybydayegg.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class NoHorizontalScrollWebView extends WebView {

    public NoHorizontalScrollWebView(Context context) {
        super(context);
    }

    public NoHorizontalScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoHorizontalScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // 禁用左右滑動
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // 計算水平和垂直滾動的差距
            float deltaX = event.getX() - event.getHistoricalX(0);
            float deltaY = event.getY() - event.getHistoricalY(0);

            // 如果水平滾動差距比垂直滾動差距大，阻止水平滾動
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                return true;
            }

            return false; // 不攔截滑動事件
        }

        return super.onInterceptTouchEvent(event);
    }
}