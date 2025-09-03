package com.hamels.daybydayegg;

import android.app.Application;

import java.util.HashMap;

public class EOrderApplication extends Application {
    public static final String TAG = EOrderApplication.class.getSimpleName();
    public static final boolean isTest = false;

    //public static final String DOMAIN_SIT = "https://eorder.hamels.com.tw:9903/";
    //public static final String DOMAIN_UAT = "https://eorder.hamels.com.tw:9920/";
    //public static final String DOMAIN_PRD = "https://eorder.hamels.com.tw:9920/";
    public static final String sPecialCustomerNo = "JCINN";

    //test
    public static final String DOMAIN_ADMIN_PRO = "https://newmanagement.maverick.com.tw";
    //public static final String DOMAIN_ADMIN_UAT = "https://www.hamels.com.tw:9941/";
    public static final String DOMAIN_ADMIN_SIT = "https://eorder.hamels.com.tw:9940/";

    //public static String ADMIN_DOMAIN = sAppVersion.equals("prod") ? DOMAIN_ADMIN_PRO : isUat ? DOMAIN_ADMIN_UAT : DOMAIN_ADMIN_SIT;
    public static String ADMIN_DOMAIN = isTest ? DOMAIN_ADMIN_SIT : DOMAIN_ADMIN_PRO;
    public static String CUSTOMER_ID = "111";
    public static String CUSTOMER_NAME = "日日好蛋";
    public static String sApiUrl = "";
    public static String WEB_SOCKET_PATH = "";
    public static String WEB_SOCKET_PATH_NAME = "WebSocketHandler.ashx";
    public static String WEB_SOCKET_MOBILE = "";
    public static String WEB_SOCKET_MOBILE_CHK = "";
    public static boolean isLogin = false;
    public static String dbConnectName = "";
    //  購物車數
    public static String cartBadgeCount = "0";
    //  票卷購物車數
    public static String cartTicketBadgeCount = "0";
    //  票卷數
    public static String memberTicketBadgeCount = "0";
    //  訊息
    public static String mailBadgeCount = "0";
    //  客服
    public static String messageBadgeCount = "0";
    //  登入前頁面
    public static int REQUEST_PAGE = 0;

    public static String DeliverCodeUid = "";
    public static String OrderListTag = "";
    public static String OrderListScrollTop = "0";
    public static String WEBVIEW_COUPONS_URL = "/coupon.html";
    public static String WEBVIEW_TERMS_URL = "/faq.html?id=1";
    public static String WEBVIEW_LOCATION_URL = "/location.html";
    public static String WEBVIEW_DELIVER_CART_URL = "/delivercart_location.html";
    public static String WEBVIEW_SHOPPING_CART_URL = "/shoppingcart_list.html";
    public static String WEBVIEW_SHOPPING_CART_URL2 = "/shoppingcart_list.html?ordertype=E";
    public static String WEBVIEW_NEWS_URL = "/news_detail.html";
    public static String WEBVIEW_SMILEPOINT_URL = "/my_points.html";
    public static String WEBVIEW_SIZE_SPEC_URL = "/product_spec.html";
    public static String WEBVIEW_MESSAGE_URL = "/message_board.html";
    public static String WEBVIEW_PUSHMAIL_URL = "/push_folder.html";
    public static String WEBVIEW_ORDER_URL = "/order.html";
    public static String WEBVIEW_ORDERDETAIL_URL = "/orderDetail.html";
    public static String WEBVIEW_PAY_COMPLETE_URL = "/pay_complete.html";
    public static String WEBVIEW_CONTENT_URL = "/content.html";
    public static String DEFAULT_PICTURE_URL = "/UploadImages/Product/default.png";

    public static double lat = 0, lon = 0;

    private static EOrderApplication application;

    public static EOrderApplication getInstance() {
        return application;
    }

    // 建立城市代稱與座標的對應關係
    public static HashMap<String, String> cityCenterItems = new HashMap<>();

    public void setCityCenterItems() {
        cityCenterItems.put("KEE", "25.121548494171105, 121.72244294093696"); // 基隆市
        cityCenterItems.put("NWT", "25.014264872988026, 121.4638364525016"); // 新北市
        cityCenterItems.put("TPE", "25.05121225087254, 121.5504942644944"); // 臺北市
        cityCenterItems.put("TAO", "24.957548036618288, 121.24077639598674"); // 桃園市
        cityCenterItems.put("HSQ", "24.723574807245818, 121.09939937041177"); // 新竹縣
        cityCenterItems.put("HSZ", "24.800370344124158, 120.97994325365435"); // 新竹市
        cityCenterItems.put("MIA", "24.578542505186086, 120.82249685652936"); // 苗栗縣
        cityCenterItems.put("TXG", "24.136957030376042, 120.68499865493263"); // 臺中市
        cityCenterItems.put("CHA", "23.952336394857223, 120.48081973741228"); // 彰化縣
        cityCenterItems.put("NAN", "23.832845886011185, 120.86576145362285"); // 南投縣
        cityCenterItems.put("YUN", "23.709390268445183, 120.43357159673828"); // 雲林縣
        cityCenterItems.put("CYQ", "23.47887600865977, 120.4415274937309"); // 嘉義縣
        cityCenterItems.put("CYI", "23.47887600865977, 120.4415274937309"); // 嘉義市
        cityCenterItems.put("TNN", "23.1016124861523, 120.2835775889087"); // 臺南市
        cityCenterItems.put("KHH", "22.66093703063071, 120.35381183292542"); // 高雄市
        cityCenterItems.put("PIF", "22.683221227817754, 120.48791334616459"); // 屏東縣
        cityCenterItems.put("ILA", "24.67810172029387, 121.77319426765386"); // 宜蘭縣
        cityCenterItems.put("HUA", "23.746407690798886, 121.44698877382433"); // 花蓮縣
        cityCenterItems.put("TTT", "22.755807666504946, 121.15035646346827"); // 台東縣
    }

    public static String getCityCenterItems(String sCityCode){
        return cityCenterItems.get(sCityCode);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        setCityCenterItems();
    }
}
