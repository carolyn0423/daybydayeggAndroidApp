package com.hamels.daybydayegg;

import android.app.Application;

public class EOrderApplication extends Application {
    public static final String TAG = EOrderApplication.class.getSimpleName();

    public static final boolean isPrd = true;

    //public static final String DOMAIN_SIT = "https://eorder.hamels.com.tw:9903/";
    //public static final String DOMAIN_UAT = "https://eorder.hamels.com.tw:9920/";
    //public static final String DOMAIN_PRD = "https://eorder.hamels.com.tw:9920/";
    public static final String sPecialCustomerNo = "JCINN";

    public static final String DOMAIN_ADMIN_PRO = "https://management.maverick.com.tw";
    public static final String DOMAIN_ADMIN_UAT = "https://www.hamels.com.tw:9941/";
    public static final String DOMAIN_ADMIN_SIT = "https://eorder.hamels.com.tw:9940/";

    public static String ADMIN_DOMAIN = isPrd ? DOMAIN_ADMIN_UAT : DOMAIN_ADMIN_SIT;

    public static String CUSTOMER_ID = "111";
    public static String CUSTOMER_NAME = "日日好蛋";
    public static String sApiUrl = "";
    public static boolean isLogin = false;

    public static String WEBVIEW_COUPONS_URL = "/coupon.html";
    public static String WEBVIEW_TERMS_URL = "/faq.html?id=1";
    public static String WEBVIEW_LOCATION_URL = "/location.html";
    public static String WEBVIEW_SHOPPING_CART_URL = "/shoppingcart_list.html";
    public static String WEBVIEW_SHOPPING_CART_URL2 = "/shoppingcart_list.html?ordertype=E";
    public static String WEBVIEW_NEWS_URL = "/news_detail.html";
    public static String WEBVIEW_SMILEPOINT_URL = "/my_points.html";
    public static String WEBVIEW_SIZE_SPEC_URL = "/product_spec.html";
    public static String WEBVIEW_MESSAGE_URL = "/message_board.html";
    public static String WEBVIEW_PUSHMAIL_URL = "/push_folder.html";
    public static String WEBVIEW_ORDER_URL = "/order.html";
    public static String WEBVIEW_ORDERDETAIL_URL = "/orderDetail.html";

    public static double lat = 0, lon = 0;

    private static EOrderApplication application;

    public static EOrderApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
