package com.hamels.daybydayegg.Main.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hamels.daybydayegg.Donate.View.DonateCartFragment;
import com.hamels.daybydayegg.Donate.View.DonateDetailFragment;
import com.hamels.daybydayegg.Donate.View.DonateFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.MemberCenter.View.AboutFragment;
import com.hamels.daybydayegg.MemberCenter.View.AdminMessageFragment;
import com.hamels.daybydayegg.MemberCenter.View.MailDetailFragment;
import com.hamels.daybydayegg.MemberCenter.View.MemberInfoChangeFragment;
import com.hamels.daybydayegg.MemberCenter.View.PasswordChangeFragment;
import com.hamels.daybydayegg.Product.View.ProductDetailDescFragment;
import com.hamels.daybydayegg.Product.View.ProductDetailFragment;
import com.hamels.daybydayegg.Product.View.ProductMainTypeFragment;
import com.hamels.daybydayegg.Repository.ApiRepository.ApiRepository;
import com.hamels.daybydayegg.Repository.ApiRepository.MemberRepository;
import com.hamels.daybydayegg.Repository.Model.Customer;
import com.hamels.daybydayegg.Repository.Model.MessageEvent;
import com.hamels.daybydayegg.Utils.CustomBottomNavigationView;
import com.hamels.daybydayegg.Utils.SharedUtils;
import com.hamels.daybydayegg.Widget.AppToolbar;
import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Business.View.BusinessFragment;
import com.hamels.daybydayegg.Business.View.BusinessProductFragment;
import com.hamels.daybydayegg.DrawLots.View.DrawLotsFragment;
import com.hamels.daybydayegg.Main.Contract.MainContract;
import com.hamels.daybydayegg.Main.Presenter.MainPresenter;
import com.hamels.daybydayegg.MemberCenter.View.MailFileFragment;
import com.hamels.daybydayegg.MemberCenter.View.MessageListFragment;
import com.hamels.daybydayegg.MemberCenter.View.WebViewFragment;
import com.hamels.daybydayegg.Product.View.ProductFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.User;
import com.hamels.daybydayegg.Utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hamels.daybydayegg.Constant.Constant.REQUEST_COUPON;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_MAIL;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_MAIN_INDEX;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_MEMBER_CARD;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_MEMBER_CENTER;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_MESSAGE;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_POINT;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_SHOPPING_CART;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_BUSINESS;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_LOT_LIST;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_DONATE;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class MainActivity extends BaseActivity implements MainContract.View {
    public static final String TAG = MainActivity.class.getSimpleName();
    private final int BACK_STACK_CLEAR = 0;
    private final int FRAGMENT_REMOVE = 1;

    private BaseFragment willChangeFragment;
    private MainContract.Presenter mainPresenter;
    private WebView webView;
    //private static TextView tvShoppingCart, tvShoppingCartETicket, tvMessageUnread;
    private static TextView  tvBadgeShoppingCart, tvMessageUnread, tvBadgeMemberTicket;

    // toolbar
    private ConstraintLayout constClose, constBackground, constBase;
    private LinearLayout llOpen, llItemButton;

    // navigation
    private CustomBottomNavigationView bottomNavigationView;
    //private FloatingActionButton btnShopping;
    private LinearLayout layoutHome, layoutEgg, layoutShop, layoutShop2, layoutShoppingCart;
    private ImageView imgHome, imgEgg, imgShop, imgShop2, imgShoppingCart;
    private TextView txtHome, txtEgg, txtShop, txtShop2, txtShoppingCart;

    // qrcode
    private PopupWindow popupWindow;
    private ImageView dialog_img_qrcode;
    private int barcodeWidth = 250;
    private int barcodeHeight = 250;
    private int brightnessNow = 0;
    private TextView text_invite_code;

    public static String sSourceActive = "";
    public static String sCustomerID = "";
    private Boolean isUpdate = false;
    private int VersionCode = 0;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private String sPDFDir = "";
    private String sUurrentURL = "";
    private static final int REQUEST_LOCATION_PERMISSION = 100;
    private static final int PERMISSION_REQUEST_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private float originalBrightness = -1; // 保存原始亮度值
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BACK_STACK_CLEAR:
                    removeExistFragment();
                    break;
                case FRAGMENT_REMOVE:
                    if (!getSupportFragmentManager().isStateSaved()) {
                        // 执行 Fragment 事务
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, willChangeFragment, "").commit();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    //  機台核銷限制提醒
    private final BroadcastReceiver pushNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showAlert("兌換失敗", intent.getStringExtra("body"));
        }
    };
    //  WebSocket
    private WebSocket webSocket;
    private OkHttpClient client;
    private Gson gson;
    private TextView tvMessageBadge;


    public void WebSocketManager() {
        gson = new Gson();
        client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getExtras() != null){
            String OrderData = getRepositoryManager(this).getPaySchemeOrderData();
            if(!OrderData.equals("")){
                String[] oData = OrderData.split("\\|");
                savePaySchemeOrderData("");
                goOrderPage(oData[0], oData[1], oData[2]);
            } else {
                notifyChangeToMail();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        tvMessageBadge = findViewById(R.id.tvMessageBadge);

        appToolbar = findViewById(R.id.toolbar);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mainPresenter = new MainPresenter(this, getRepositoryManager(this));

        sSourceActive = mainPresenter.getSourceActive();
        sCustomerID = EOrderApplication.CUSTOMER_ID;

        setCustomerData();
        initView();
        initAnimation(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(pushNotificationReceiver, new IntentFilter("WRITE_OFF_MESSAGE"), Context.RECEIVER_EXPORTED);
        }

        WebSocketManager();

        // 🚀 一進來就載入首頁 Fragment（輪播圖 + 按鈕）
        if (savedInstanceState == null) {
            GetMainIndexFragment();

            // 同步選中首頁 tab
            CustomBottomNavigationView navView = findViewById(R.id.navigation);
            if (navView != null) {
                navView.setSelectedItemId(R.id.item_home);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable(){
            @Override
            public void run() {
//                if(EOrderApplication.isPrd){
//                    try {
//                        VersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
//
//                        ChkVision();
//                    } catch (PackageManager.NameNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }

                try {
                    VersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;

                    ChkVision();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void setCustomerID(String CustomerID) {
        sCustomerID = CustomerID;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        refreshBadge();

        // 版本更新提醒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "isUpdate : " + isUpdate);
                if(isUpdate){
                    AlertUpdateApp();
                }
            }
        }, 3000);


    }

    private void notifyChangeToMail() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        if (fragment != null) {
            mainPresenter.checkLoginForMail(fragment.getClass().getSimpleName());
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, MainIndexFragment.getInstance(), "").commit();
            mainPresenter.checkLoginForMail(MainIndexFragment.TAG);
        }
    }

    private void initView() {
        // --- 1. 從父 layout 拿子 view ---
        ConstraintLayout constraintLayout3 = findViewById(R.id.constraintLayout3);
        llOpen = constraintLayout3.findViewById(R.id.ll_title_open);
        llItemButton = constraintLayout3.findViewById(R.id.ll_item_button);
        constClose = constraintLayout3.findViewById(R.id.const_title_close);
        constBackground = constraintLayout3.findViewById(R.id.const_background);
        constBase = constraintLayout3.findViewById(R.id.const_base);

// --- 2. 其他 view ---
        LinearLayout qrcode = constraintLayout3.findViewById(R.id.qrcode);
        qrcode.setOnClickListener(onClickListener);
        LinearLayout message = constraintLayout3.findViewById(R.id.message);
        setMailBadgeCount(EOrderApplication.mailBadgeCount); // 取代原本的 tvMessageUnread
        message.setOnClickListener(onClickListener);
        LinearLayout machine = constraintLayout3.findViewById(R.id.machine);
        machine.setOnClickListener(onClickListener);
        LinearLayout member = constraintLayout3.findViewById(R.id.member);
        member.setOnClickListener(onClickListener);

        appToolbar.getBtnMessage().setOnClickListener(v -> {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
            mainPresenter.checkLoginForMessage(fragment.getClass().getSimpleName());
            appToolbar.setMessageBadgeCount(0); // 點擊後清除 badge
        });

        appToolbar.getBtnMail().setOnClickListener(v -> {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
            mainPresenter.checkLoginForMail(fragment.getClass().getSimpleName());
        });



// --- 3. 初始化動畫 ---
        initAnimation(this);

// --- 4. BottomNavigationView ---
        bottomNavigationView = findViewById(R.id.navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.item_home:
                        changeNavigationColor(item.getItemId());
                        setMailBadgeCount("0");
                        GetMainIndexFragment();
                        return true;
                    case R.id.item_egg:
                        changeNavigationColor(item.getItemId());
                        setMailBadgeCount("0"); // 清除 badge
                        mainPresenter.checkLoginForDonate();
                        return true;
                    case R.id.item_shop:
                        changeNavigationColor(item.getItemId());
                        setMailBadgeCount("0"); // 清除 badge
                        checkMerchantCount("PRODUCT", "Y");
                        return true;
                    case R.id.item_shop2:
                        changeNavigationColor(item.getItemId());
                        setMailBadgeCount("0"); // 清除 badge
                        checkMerchantCount("PRODUCT", "N");
                        return true;
                    case R.id.item_cart:
                        changeNavigationColor(item.getItemId());
                        setMailBadgeCount("0"); // 清除 badge
                        mainPresenter.checkLoginForShoppingCart("");
                        return true;
                }

                return false;
            });

            // --- 5. 初始化 Badge ---
            BadgeDrawable cartBadge = bottomNavigationView.getOrCreateBadge(R.id.item_cart);
            cartBadge.setVisible(false);
            cartBadge.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

            BadgeDrawable msgBadge = bottomNavigationView.getOrCreateBadge(R.id.item_egg);
            msgBadge.setVisible(false);
            msgBadge.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            Log.e("MainActivity", "bottomNavigationView is null!");
        }

    }

    private void updateCartBadge(int count) {
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.item_cart);
        if (count > 0) {
            badge.setVisible(true);
            badge.setNumber(count);
        } else {
            badge.setVisible(false);
        }
    }

    private void updateMessageBadge(int count) {
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.item_egg);
        if (count > 0) {
            badge.setVisible(true);
            badge.setNumber(count);
        } else {
            badge.setVisible(false);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            /*下方menu*/
//            if (id == R.id.home) {
//                changeNavigationColor(v.getId());
//                changeTabFragment(MainIndexFragment.getInstance());
//            }else if(id == R.id.egg){
//                changeNavigationColor(v.getId());
//                mainPresenter.checkLoginForDonate();
//            }else if (id == R.id.shop){
//                changeNavigationColor(v.getId());
//                mainPresenter.saveSourceActive("");
//                checkMerchantCount("PRODUCT", "Y");
//            }else if (id == R.id.shop2){
//                changeNavigationColor(v.getId());
//                mainPresenter.saveSourceActive("");
//                checkMerchantCount("PRODUCT", "N");
//            }else if (id == R.id.shopping_cart){
//                changeNavigationColor(v.getId());
//                mainPresenter.checkLoginForShoppingCart("");
//            }

            /*首頁上方menu*/
            if (id == R.id.qrcode){
                if (mainPresenter.getUserLogin()) {
                    try {
                        originalBrightness = Settings.System.getInt(getContentResolver(),
                                Settings.System.SCREEN_BRIGHTNESS);
                    } catch (Settings.SettingNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    // 设置屏幕亮度为最亮
                    setScreenBrightnessToMax();

                    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_qrcode, null);

                    popupWindow = new PopupWindow(view);
                    popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

                    dialog_img_qrcode = view.findViewById(R.id.dialog_img_qrcode);
                    text_invite_code = view.findViewById(R.id.text_invite_code);

                    User user = getUser();
                    createQRcodeImage(user.getMembershipCode(), dialog_img_qrcode);
                    text_invite_code.setText(mainPresenter.getInvitationCode());


                    Button dialog_btn_close = view.findViewById(R.id.dialog_btn_close);
                    dialog_btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            changeAppBrightness(brightnessNow);
                        }
                    });

                    text_invite_code.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OpenShared();
                        }
                    });

                    popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    EOrderApplication.REQUEST_PAGE = REQUEST_MAIN_INDEX;
                    intentToLogin(REQUEST_MAIN_INDEX);
                }
            }else if (id == R.id.message){
                //setMainIndexMailUnreadVisibility(false);
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                mainPresenter.checkLoginForMail(fragment.getClass().getSimpleName());
            }else if (id == R.id.machine){
                addFragment(MachineFragment.getInstance());
            }else if (id == R.id.member){
                changeNavigationColor(v.getId());
                mainPresenter.checkLoginForMemberCenter();
            }
        }
    };

    public void changeNavigationColor(int menuId) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        if (bottomNavigationView == null) return;

        // 只更新 drawable，不改選中狀態
        MenuItem homeItem = bottomNavigationView.getMenu().findItem(R.id.item_home);
        MenuItem eggItem = bottomNavigationView.getMenu().findItem(R.id.item_egg);
        MenuItem shopItem = bottomNavigationView.getMenu().findItem(R.id.item_shop);
        MenuItem shop2Item = bottomNavigationView.getMenu().findItem(R.id.item_shop2);
        MenuItem cartItem = bottomNavigationView.getMenu().findItem(R.id.item_cart);

        homeItem.setIcon(menuId == R.id.item_home ? R.drawable.home_fill : R.drawable.home_line);
        eggItem.setIcon(menuId == R.id.item_egg ? R.drawable.egg3_fill : R.drawable.egg3_line);
        shopItem.setIcon(menuId == R.id.item_shop ? R.drawable.ticket_fill : R.drawable.ticket_line);
        shop2Item.setIcon(menuId == R.id.item_shop2 ? R.drawable.bag_fill : R.drawable.bag_line);
        cartItem.setIcon(menuId == R.id.item_cart ? R.drawable.cart_fill : R.drawable.cart_line);
    }
    private void initAnimation(Context context) {
        final Animation openAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_down);
        final Animation open2Animation = AnimationUtils.loadAnimation(context, R.anim.anim_down2);
        final Animation visibleAnimation = AnimationUtils.loadAnimation(context, R.anim.anime_visiable);

        final Animation upAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_up);

        openAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llOpen.setVisibility(View.VISIBLE);
                //setMainIndexMailUnreadVisibility(true);
                constClose.setVisibility(View.GONE);
                constBackground.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        upAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //setMainIndexMailUnreadVisibility(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llOpen.setVisibility(View.GONE);
                constClose.setVisibility(View.VISIBLE);
                constBackground.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        constClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.const_title_close).startAnimation(openAnimation);
                findViewById(R.id.const_background).startAnimation(open2Animation);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setConstBase(false);
                    }
                }, 100);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        llOpen.setVisibility(View.VISIBLE);
                        llItemButton.startAnimation(visibleAnimation);
                    }
                }, 200);
            }
        });

        llOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setConstBase(true);
                llOpen.startAnimation(upAnimation);
            }
        });
    }

    public void member(View view) {
        Toast.makeText(this, "member", Toast.LENGTH_SHORT).show();
    }

    public void message(View view) {
        Toast.makeText(this, "message", Toast.LENGTH_SHORT).show();
    }

    public void lottery(View view) {
        Toast.makeText(this, "lottery", Toast.LENGTH_SHORT).show();
    }

    public void donate(View view) {
        Toast.makeText(this, "donate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MAIN_INDEX:
                GetMainIndexFragment();
                break;
            case REQUEST_MEMBER_CARD:
                changeTabFragment(MemberCardFragment.getInstance());
                break;
            case REQUEST_MEMBER_CENTER:
                changeTabFragment(MemberCenterFragment.getInstance());
                break;
            case REQUEST_COUPON:
                addFragment(WebViewFragment.getInstance(R.string.coupon, EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_COUPONS_URL));
                break;
            case REQUEST_POINT:
                addFragment(WebViewFragment.getInstance(R.string.my_point, EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_SMILEPOINT_URL));
                break;
            case REQUEST_MAIL:
                addFragment(MailFileFragment.getInstance());
                break;
            case REQUEST_MESSAGE:
                if(mainPresenter.getShopkeeper().equals("Y")){
                    addFragment(AdminMessageFragment.getInstance());
                }else{
                    addFragment(MessageListFragment.getInstance(mainPresenter.getUserID(), mainPresenter.getMobile(), "N"));
                }
                break;
            case REQUEST_SHOPPING_CART:
                changeTabFragment(ShoppingCartFragment.getInstance());
                break;
            case REQUEST_BUSINESS:
                addFragment(BusinessFragment.getInstance());
                break;
            case REQUEST_LOT_LIST:
                addFragment(DrawLotsFragment.getInstance());
                break;
            case REQUEST_DONATE:
                DonateFragment.getInstance().type_idMode("0");
                addFragment(DonateFragment.getInstance());
                break;
        }
    }

    @Override
    public void refreshBadge() {
        mainPresenter.getMailBadgeFromApi();
        mainPresenter.getMessageBadgeFromApi();
        mainPresenter.getBadgeNumberFromApi();

        setAppBadge();
    }

    @Override
    public void setMailBadge(String count) {
        appToolbar.setMailBadgeCount(Integer.parseInt(count));
    }

    @Override
    public void setMessageBadge(String count) {
        appToolbar.setMessageBadgeCount(Integer.parseInt(count));
    }

    public AppToolbar getAppToolbarObj(){
        return appToolbar;
    }

    // 判斷畫面上顯示的是否為MainIndexFragment
    public boolean isMainIndex() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        for (Fragment fragment : fragmentList) {
            if (fragment != null && fragment.isVisible() && fragment instanceof MainIndexFragment) {
                return true;
            }
        }
        return false;
    }

    public void OpenShared(){
        // 获取剪贴板管理器
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String copiedText = mainPresenter.getUserName() + " 邀請您下載 " + EOrderApplication.CUSTOMER_NAME + " APP，註冊時輸入邀請碼，即可獲得新會員獎勵";
        copiedText += "\n\niOS下載連結 : https://itunes.apple.com/app/id6463211336";
        copiedText += "\n\nAndroid下載連結 : https://play.google.com/store/apps/details?id=com.hamels.daybydayegg";
        copiedText += "\n\n邀請碼 :      " +  mainPresenter.getInvitationCode() + "\n";

        if (clipboard != null) {
            // 创建一个ClipData对象，将文本复制到剪贴板
            ClipData clip = ClipData.newPlainText("Copied Text", copiedText);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "已複製", Toast.LENGTH_SHORT).show();
        }

        // 打开分享对话框
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, copiedText);
        startActivity(Intent.createChooser(shareIntent, "分享到"));

        // 恢复屏幕亮度
        getWindow().getAttributes().screenBrightness = originalBrightness;
        getWindow().setAttributes(getWindow().getAttributes());
    }

    @Override
    public void setAllBadge(String count) {
        String[] array = count.split("_");

        if (array.length >= 1) {
            EOrderApplication.messageBadgeCount = array[1];
            int msgCount = Integer.parseInt(array[1]);

            // Toolbar badge
            if (msgCount > 0) {
                tvMessageBadge.setText(String.valueOf(msgCount));
                tvMessageBadge.setVisibility(View.VISIBLE);
            } else {
                tvMessageBadge.setVisibility(View.GONE);
            }
        }

        // 3. 購物車商品數量
        if (array.length >= 4) {
            EOrderApplication.cartTicketBadgeCount = array[2].equals("") ? "0" : array[2];
            EOrderApplication.cartBadgeCount = array[3].equals("") ? "0" : array[3];
            int iCartQuantity = Integer.parseInt(EOrderApplication.cartTicketBadgeCount)
                    + Integer.parseInt(EOrderApplication.cartBadgeCount);

            BadgeDrawable cartBadge = bottomNavigationView.getOrCreateBadge(R.id.item_cart);
            if (iCartQuantity > 0) {
                cartBadge.setVisible(true);
                cartBadge.setNumber(iCartQuantity);
            } else {
                cartBadge.setVisible(false);
            }
        }

        if (array.length >= 5) {
            EOrderApplication.memberTicketBadgeCount = array[4].equals("") ? "0" : array[4];
            int eggCount = Integer.parseInt(EOrderApplication.memberTicketBadgeCount);

            BadgeDrawable eggBadge = bottomNavigationView.getOrCreateBadge(R.id.item_egg);
            if (eggCount > 0) {
                eggBadge.setVisible(true);
                eggBadge.setNumber(eggCount);
            } else {
                eggBadge.setVisible(false);
            }
        }
    }

    public void checkLoginForLot() {
        mainPresenter.checkLoginForLot();
    }

    public void setTopBarVisibility(boolean isVisible) {
        if (isVisible) { //回到MainIndexFragment
            llOpen.setVisibility(View.VISIBLE); //開起來的選單
            llItemButton.setVisibility(View.VISIBLE);
            constBase.setVisibility(View.VISIBLE);
            constBackground.setVisibility(View.VISIBLE);
            constClose.setVisibility(View.GONE);
            setConstBase(false);
        } else {
            llOpen.setVisibility(View.GONE);
            llItemButton.setVisibility(View.GONE);
            constBase.setVisibility(View.GONE);
            constBackground.setVisibility(View.GONE);
            constClose.setVisibility(View.GONE);
        }
    }

    public void setConstBase(boolean isOpen) {
        ViewGroup.LayoutParams params = constBase.getLayoutParams();
        if (isOpen) {
//            params.height = 150;
            params.height = 50;
        } else {
//            params.height = 100;
            params.height = 30;
        }
        constBase.setLayoutParams(params);
    }

    @Override
    public void intentToLogin(int requestCode) {
        IntentUtils.intentToLogin(this, requestCode);
    }

    public void intentToVerifyCode() { IntentUtils.intentToVerifyCode(this, true); }

    public CustomBottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    private void removeAllStackFragment() {

        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }

        Message message = new Message();
        message.what = BACK_STACK_CLEAR;
        handler.sendMessage(message);
    }

    private void removeExistFragment() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        Message message = new Message();
        message.what = FRAGMENT_REMOVE;
        handler.sendMessage(message);
    }

    @Override
    public void changeTabFragment(BaseFragment willChangeFragment) {
        this.willChangeFragment = willChangeFragment;
        Log.e(TAG, "changeTabFragment" + willChangeFragment);
        removeAllStackFragment();
        addFragment(willChangeFragment);
    }

    @Override
    public void addFragment(BaseFragment baseFragment) {
        this.willChangeFragment = baseFragment;
        Fragment fragment = getSupportFragmentManager().findFragmentById(baseFragment.getId());
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commitNow();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame, baseFragment, "");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void bindWebView(WebView webView) {
        this.webView = webView;
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                result.cancel();
                return true;
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(title.indexOf("html") == -1 && title.indexOf("com") == -1) {
                    setAppTitleString(title);
                }
            }
        });
        // 關閉調試模式以提高性能
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(false);
        }
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        // 禁用滾動條
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        // WebView启用混合内容
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.getSettings().setUseWideViewPort(true);
        webView.addJavascriptInterface(new AndroidJsInterface(), "hamels");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setBackgroundColor(getResources().getColor(R.color.gray));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                setBackButtonVisibility(view.canGoBack());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                setBackButtonVisibility(view.canGoBack());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                try {
                    sUurrentURL = url;
                    if (url.startsWith("https://maps.app.goo.gl/") || url.startsWith("https://www.google.com.tw/")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } else if (url.startsWith("http:") || url.startsWith("https:")) {
                        return false;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    return false;
                }
//            webView.loadUrl(url);
                return true;
            }
        });
    }

    public void setCustomerData() {
        mainPresenter.setCustomerData();
    }

    public class AndroidJsInterface {

        @JavascriptInterface
        public String jsCall_getVariable(String Info) {
            String sData = "";
            try {
                switch (Info){
                    case "customer_id":
                        sData = EOrderApplication.CUSTOMER_ID;
                        break;
                    case "connection_name":
                        sData = EOrderApplication.dbConnectName;
                        break;
                    case "OrderListTag":
                        sData = EOrderApplication.OrderListTag;
                        break;
                    case "OrderListScrollTop":
                        sData = EOrderApplication.OrderListScrollTop;
                        break;
                    default:
                        JSONObject oMemberData = new JSONObject(getUser().toString());
                        sData = oMemberData.getString(Info);
                        break;
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
            return sData;
        }

        @JavascriptInterface
        public void jsCall_goLoginPage(String page, String function) {
            goPage();
        }

        @JavascriptInterface
        public void jsCall_goOrderPage(String orderType) {
            goOrderPage(orderType, "", "");
        }

        @JavascriptInterface
        public void jsCall_goShopPage(String salesType) {
            //String isETicket = salesType.equals("E") ? "Y" : "N";
            goProductPage("Y");
        }
        @JavascriptInterface
        public void jsCall_goHomePage()  {
            GetMainIndexFragment();
            //changeTabFragment(MainIndexFragment.getInstance());
        }
        @JavascriptInterface
        public void jsCall_goTicketPage() {
            changeTabFragment(DonateFragment.getInstance());
        }
        @JavascriptInterface
        public void jsCall_setShopCartNumToApp() {
            refreshBadge();
        }

        @JavascriptInterface
        public void jsCall_goShoppingCart(String orderType) {
            mainPresenter.checkLoginForShoppingCart(orderType);
        }

        @JavascriptInterface
        public void jsCall_SavePaySchemeOrderData(String sParam){
            savePaySchemeOrderData(sParam);
        }

        @JavascriptInterface
        public void jsCall_PaySchemeGoOrderDetail(String sParam){
            savePaySchemeOrderData(sParam);
            String[] oData = sParam.split("\\|");

            Uri data = Uri.parse("maverickfood://com.hamels.daybydayegg?order_type=" + oData[0] + " + order_id=" + oData[1] + "&meal_no=" + oData[2]);
            Intent intent = new Intent(Intent.ACTION_VIEW, data);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, RESULT_OK);
        }

        @JavascriptInterface
        public void jsCall_setShoppingCartAppTitle(String sParam) {
            ShoppingCartFragment.getInstance().setShoppingCartAppTitle(sParam);
        }
        @JavascriptInterface
        public void jsCall_SharedPDF(String sPDFUrl) {
            new DownloadFile().execute(sPDFUrl, "Shared");
        }
        @JavascriptInterface
        public void jsCall_DownloadPDF(String sPDFUrl) {
            new DownloadFile().execute(sPDFUrl, "Download");
        }
        @JavascriptInterface
        public void jsCall_copyToClipboard(String sMessage) {
            ClipboardManager clipboard = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", sMessage);

            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getBaseContext(), "已複製", Toast.LENGTH_SHORT).show();
            }
        }
        @JavascriptInterface
        public void jsCall_saveOrderListTag(String OrderListTag) {
            EOrderApplication.OrderListTag = OrderListTag;
        }
        @JavascriptInterface
        public void jsCall_saveOrderListScrollTop(String OrderListScrollTop) {
            EOrderApplication.OrderListScrollTop = OrderListScrollTop;
        }
    }

    private void savePaySchemeOrderData(String sParam){
        getRepositoryManager(this).savePaySchemeOrderData(sParam);
    }

    public void detachWebView() {
        webView = null;
    }

    public void setTabPage(int page) {
        MenuItem menuItem = bottomNavigationView.getMenu().getItem(page);
        bottomNavigationView.setIconScaleType(menuItem);
    }

    public void resetPassword() {
        setTabPage(0);
        mainPresenter.logout();
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            String currentPage = "";
            String goBackPage = "";

            WebBackForwardList mWebBackForwardList = webView.copyBackForwardList();
            if (mWebBackForwardList.getCurrentIndex() > 0) {
                currentPage = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()).getUrl();
                goBackPage = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();
            }
            if (currentPage.indexOf("orderDetail.html") > 0
                    || goBackPage.indexOf("order.html") > 0
                    || currentPage.indexOf("pay_complete.html") > 0) {
                webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_ORDER_URL + "?orderType=" + "");
            } else if (currentPage.indexOf("order.html") > 0) {
                changeTabFragment(MemberCenterFragment.getInstance());
                webView = null;
            } else if(currentPage.indexOf("ebook.html") > 0){
                GetMainIndexFragment();
                //changeTabFragment(MainIndexFragment.getInstance());
            } else if(currentPage.indexOf("shoppingcart_list_product.html") > 0){
                mainPresenter.GetShopCartLocationQuantity();
            } else if(currentPage.indexOf("AioCheckOut") > 0 || sUurrentURL.indexOf("ecpay.com.tw") > 0) {
                webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_PAY_COMPLETE_URL + "?isSuccess=false" + "");
            } else if(currentPage.indexOf("delivercart") > 0 || currentPage.indexOf("delivercart_location") > 0){
                if(EOrderApplication.DeliverCodeUid.equals("")){
                    changeTabFragment(DonateCartFragment.getInstance());
                }else{
                    changeTabFragment(DonateDetailFragment.getInstance(Integer.parseInt(EOrderApplication.DeliverCodeUid)));
                }
            } else {
                webView.goBack();
            }
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    // 其他就顯示首頁
                    GetMainIndexFragment();
                    //changeTabFragment(MainIndexFragment.getInstance());

            } else {
                // 點推播通知進來的
                if (getSupportFragmentManager().getBackStackEntryCount() == 1
                        && ( getSupportFragmentManager().getFragments().size() == 0
                        || getSupportFragmentManager().getFragments().get(0).equals(MailFileFragment.getInstance()))
                ) {
                    GetMainIndexFragment();
                    //changeTabFragment(MainIndexFragment.getInstance());
                } else {
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                    Log.d("BackPress", "CurrentFragment: " + currentFragment);
                    if(currentFragment instanceof ProductDetailDescFragment){     //  商品詳細
                        super.onBackPressed();
                    }else if(currentFragment instanceof ProductDetailFragment){         //  商品內頁
                        addFragment(ProductFragment.getInstance());
                    }else if(currentFragment instanceof ProductFragment){               //  商品列表
                        addFragment(ProductMainTypeFragment.getInstance());
                    }else if(
                            currentFragment instanceof MemberCenterFragment ||
                            currentFragment instanceof ProductMainTypeFragment ||
                            currentFragment instanceof DonateFragment ||
                            currentFragment instanceof MachineFragment ||
                            currentFragment instanceof ShoppingCartFragment ||
                            currentFragment instanceof MessageFragment
                    ){
                        GetMainIndexFragment();
                        //changeTabFragment(MainIndexFragment.getInstance());
                    }else if(currentFragment instanceof MachineMapFragment){
                        if(MachineMapFragment.getInstance().getPopupWindow() != null) {
                            MachineMapFragment.getInstance().getPopupWindow().dismiss();
                        }
                        GetMainIndexFragment();
                        //changeTabFragment(MainIndexFragment.getInstance());
                    }else if(currentFragment instanceof MemberInfoChangeFragment
                            || currentFragment instanceof PasswordChangeFragment
                            || currentFragment instanceof AboutFragment
                            || currentFragment instanceof TransRecordFragment) {
                        addFragment(MemberCenterFragment.getInstance());
                    } else if(currentFragment instanceof MailDetailFragment ){
                        addFragment(MailFileFragment.getInstance());
                    } else {
                        super.onBackPressed();
                    }
                }
            }
        }
    }

    public void ShoppingBackPage(String LocationQuantity){
        int iLocationQuantity = Integer.parseInt(LocationQuantity);
        if(iLocationQuantity < 2){
            changeTabFragment(MainIndexFragment.getInstance());
        }else{
            changeTabFragment(ShoppingCartFragment.getInstance());
        }
    }

    public void GetMainIndexFragment() {
        changeNavigationColor(R.id.item_home); // ✅ 改成 BottomNavigationView 的 id
        MainIndexFragment fragment = new MainIndexFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }


    public void goMemberCard() {
        mainPresenter.checkLoginForMemberCard();
    }

    public void checkLoginForCoupon() {
        mainPresenter.checkLoginForCoupon();
    }

    public void checkLoginForBusiness() {
        mainPresenter.checkLoginForBusiness();
    }

    public void setShoppingCartCount(String Count) {
        if (!Count.equals("")) {
            tvBadgeShoppingCart.setVisibility(View.VISIBLE);
            tvBadgeShoppingCart.setText(Count);
        } else {
            tvBadgeShoppingCart.setVisibility(View.GONE);
        }
    }

    public void setMailBadgeCount(String count) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        if(bottomNavigationView == null) return;

        if(count != null && !count.equals("") && !count.equals("0")) {
            BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.item_home);
            badge.setVisible(true);
            badge.setNumber(Integer.parseInt(count));
        } else {
            bottomNavigationView.removeBadge(R.id.item_home);
        }
    }

    public void goNewsDetail(String news_id) {
        mainPresenter.goNewsDetail(news_id);
    }

    public User getUser() {
        return mainPresenter.JsCallGetMemberInfo();
    }

    public void goPage() {
        mainPresenter.checkLoginForMemberCenter();
    }

    public void goOrderPage(String orderType, String sOrderID, String sMealNo) {
        runOnUiThread(new Runnable() {
            public void run() {
                changeNavigationColor(R.id.member); //會員中心
            }
        });
        changeTabFragment(TransRecordFragment.getInstance(orderType, sOrderID, sMealNo));
    }

    public void goProductPage(final String isETicket) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (isETicket.equals("Y")) {
                    changeNavigationColor(R.id.item_shop); // 買提貨券
                } else {
                    //changeNavigationColor(R.id.order); //我要點餐
                }
            }
        });

        checkMerchantCount("PRODUCT", isETicket);
    }

    // 商店清單: 當商店數量小於一時，直接顯示商品清單
    public void checkMerchantCount(final String type, final String isETicket) {
        mainPresenter.saveFragmentMainType("", isETicket);
        ProductMainTypeFragment fragment = new ProductMainTypeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
        addFragment(ProductMainTypeFragment.getInstance());
    }

    private void createQRcodeImage(String content, ImageView qrcode_img) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 设置纠错级别为最高
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();

            Bitmap qrBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrBitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.Black) : getResources().getColor(R.color.white));
                }
            }

            qrcode_img.setImageBitmap(qrBitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(getBaseContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }

    public void changeAppBrightness(int brightness) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);

        // 恢复屏幕亮度
        getWindow().getAttributes().screenBrightness = originalBrightness;
        getWindow().setAttributes(getWindow().getAttributes());
    }

    public void getPermissionsCamera(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }
    }

    public void showErrorAlert(String message) {
        if(!MainActivity.this.isFinishing()){
            new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        }
    }

    public void showAlert(String sTitle, String sMessage) {
        if(!MainActivity.this.isFinishing()){
            new AlertDialog.Builder(this).setTitle(sTitle).setMessage(sMessage).setPositiveButton(android.R.string.ok, null).show();
        }
    }

    public void initSelectCustomer(String sMode) { IntentUtils.intentToCustomer(this, sMode); }

    public void getCheckCustomerNo(String sCutomerNo){
        mainPresenter.checkCustomerNo(sCutomerNo);
    }
    @Override
    public void setCustomer(Customer customers, String sCustomerID, String sCustomerName, String sApiUrl) {
        TextView tvEOrder = findViewById(R.id.tv_eorder);
        if(customers == null && (sCustomerID.equals("") || sCustomerName.equals(""))){
            //tvEOrder.setText("全部商家");
            EOrderApplication.CUSTOMER_ID = "";
            EOrderApplication.CUSTOMER_NAME = "";
            EOrderApplication.sApiUrl = "";
        }else if(!sCustomerName.equals((""))){
            //tvEOrder.setText(sCustomerName);
            EOrderApplication.CUSTOMER_ID = sCustomerID;
            EOrderApplication.CUSTOMER_NAME = sCustomerName;
            EOrderApplication.sApiUrl = sApiUrl;
        }else{
            //tvEOrder.setText(customers.getCustomerName());
            EOrderApplication.CUSTOMER_ID = customers.getCustomerID();
            EOrderApplication.CUSTOMER_NAME = customers.getCustomerName();
            EOrderApplication.sApiUrl = customers.getApiUrl();
        }
        ApiRepository.repository = null;
        MemberRepository.memberRepository = null;
        ApiRepository.getInstance();
        MemberRepository.getInstance();
    }

    public void getCustomer(){
        mainPresenter = new MainPresenter(this, getRepositoryManager(this));
        mainPresenter.getCustomer();
    }

    public void ChkVision(){
        mainPresenter = new MainPresenter(this, getRepositoryManager(this));
        mainPresenter.getOnlineVision();
    }

    public void getVersion(String sOnlineVision) {

        if(compareVersion(VersionCode + "", sOnlineVision)){
            isUpdate = true;
        }
    }

    public void CreateWebSocket() {
        boolean isCreate = false;
        if((EOrderApplication.WEB_SOCKET_MOBILE_CHK != EOrderApplication.WEB_SOCKET_MOBILE) || webSocket == null){
            isCreate = true;
            EOrderApplication.WEB_SOCKET_MOBILE_CHK = EOrderApplication.WEB_SOCKET_MOBILE;
        }

        if (!EOrderApplication.WEB_SOCKET_PATH.equals("") && !EOrderApplication.WEB_SOCKET_MOBILE.equals("") && isCreate) {
            String sWssUrl = EOrderApplication.WEB_SOCKET_PATH.replace("https", "wss") + "?connector_id=" + EOrderApplication.WEB_SOCKET_MOBILE;
            Request request = new Request.Builder().url(sWssUrl).build();
            webSocket = client.newWebSocket(request, new WebSocketListener() {
                @Override
                public void onOpen(WebSocket webSocket, Response response) {
                    Log.d(TAG, "WebSocket Connected");
                }

                @Override
                public void onMessage(WebSocket webSocket, String message) {
                    Log.d(TAG, "Message Received: " + message);
                    JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
                    if (jsonObject.has("function_name")) {
                        String functionName = jsonObject.get("function_name").getAsString();
                        if ("leave_message".equals(functionName)) {
                            EventBus.getDefault().post(new MessageEvent(message));
                        }
                    }
                }

                @Override
                public void onClosing(WebSocket webSocket, int code, String reason) {
                    Log.d(TAG, "WebSocket Closing: " + reason);
                    closeWebSocket();
                    EOrderApplication.WEB_SOCKET_MOBILE_CHK = "";
                    scheduleReconnect(); // 安排重連
                }

                @Override
                public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                    Log.d(TAG, "WebSocket Failure: " + t.getMessage());
                    closeWebSocket();
                    EOrderApplication.WEB_SOCKET_MOBILE_CHK = "";
                    scheduleReconnect(); // 安排重連
                }
            });
        }
    }

    private void scheduleReconnect() {
        // 延遲重連，避免過於頻繁的重連導致伺服器過載
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeWebSocket();
                CreateWebSocket();
            }
        }, 5000);
    }

    public void closeWebSocket() {
        if (webSocket != null) {
            webSocket.close(1000, "Client closed");
            webSocket = null;
        }
    }

    public void sendMessageToWebSocket(String sMemberID) {
        if (webSocket != null) {
            JsonObject notification = new JsonObject();
            notification.addProperty("connection_name", EOrderApplication.dbConnectName);
            notification.addProperty("customer_id", EOrderApplication.CUSTOMER_ID);
            notification.addProperty("member_id", sMemberID);
            notification.addProperty("function_name", "leave_message");
            String notificationMessage = gson.toJson(notification);
            webSocket.send(notificationMessage);
            Log.d("WebSocket", "Sent: " + notificationMessage);
        }
    }

    private void AlertUpdateApp(){
        String PackageName = this.getPackageName();
        new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage("發現新版本")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isUpdate = true;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + PackageName + "&hl=zh-TW"));
                        startActivity(intent);
                    }
                })
                .show();
    }
    /**
     * 版本号比较
     */
    public static boolean compareVersion(String sAppVersion, String sOnlineVersion) {

        if(sAppVersion == null || sOnlineVersion == null){
            return false;
        }

        if (sAppVersion.equals("") || sOnlineVersion.equals("")) {
            return false;
        }

        double AppVersion = Double.parseDouble(sAppVersion);
        double OnlineVersion = Double.parseDouble(sOnlineVersion);

        if(AppVersion >= OnlineVersion){
            return false;
        }else{
            return true;
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean bResult = false;
            String fileUrl = strings[0];
            String Mode = strings[1];
            File pdffile = new File(fileUrl);
            String fileName = pdffile.getName();
            try {
                // 检查是否已经获得写入外部存储的权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有权限，请求权限
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }

                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                storageDir.mkdirs();
                sPDFDir = storageDir.getPath();
                File oldFile = new File(storageDir.getPath() + "/" + fileName);
                if (oldFile.exists()) {
                    // 如果文件存在，首先删除它
                    oldFile.delete();
                }
                File pdfFile = new File(storageDir, fileName);

                FileOutputStream output = new FileOutputStream(pdfFile);
                InputStream input = connection.getInputStream();

                byte[] buffer = new byte[1024];
                int len;
                while ((len = input.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                }

                output.close();
                input.close();

                bResult = true;

                if(Mode.equals("Shared")) {
                    // Share the downloaded PDF using FileProvider
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("application/pdf");

                    Uri pdfUri = FileProvider.getUriForFile(getApplicationContext(), "com.hamels.daybydayegg.fileprovider", pdfFile);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);

                    // Grant temporary read permission to the content URI
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivity(Intent.createChooser(shareIntent, "分享到"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bResult;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // 共享成功，显示成功消息
                Toast.makeText(getApplicationContext(), "文件已成功儲存至: " + sPDFDir, Toast.LENGTH_LONG).show();
            }
        }
    }
    private void showPermissionSettingsDialog() {
        // 開啟應用程式設定頁面
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    //  取得座標
    // 检查位置权限
    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // 如果没有位置权限，则请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_LOCATION);
        } else {
            // 如果已经有位置权限，则获取位置信息
            getLocation();
        }
    }
    private void setScreenBrightnessToMax() {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 1.0f; // 亮度值范围是 0.0 到 1.0
        getWindow().setAttributes(layoutParams);
    }
    // 处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果用户授予了位置权限，则获取位置信息
                getLocation();
            } else {
                // 用户拒绝了位置权限，可以在此处理相应的操作
                showPermissionSettingsDialog();
            }
        }
    }

    // 获取位置信息
    private void getLocation() {
        // 在获取位置信息之前应该检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 如果没有位置权限，则不执行位置获取操作
            EOrderApplication.lat = 0;
            EOrderApplication.lon = 0;
            return;
        }

        // 获取位置信息的代码
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // 处理获取到的位置信息
                            EOrderApplication.lat = location.getLatitude();
                            EOrderApplication.lon = location.getLongitude();
                        }
                    }
                });
    }
}