package com.hamels.daybydayegg.Main.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hamels.daybydayegg.Donate.View.DonateFragment;
import com.hamels.daybydayegg.Product.View.ProductDetailFragment;
import com.hamels.daybydayegg.Product.View.ProductMainTypeFragment;
import com.hamels.daybydayegg.Repository.ApiRepository.ApiRepository;
import com.hamels.daybydayegg.Repository.ApiRepository.MemberRepository;
import com.hamels.daybydayegg.Repository.Model.Customer;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Utils.SharedUtils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
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
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Utils.IntentUtils;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONObject;

import java.util.List;

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

public class MainActivity extends BaseActivity implements MainContract.View {
    public static final String TAG = MainActivity.class.getSimpleName();
    private final int BACK_STACK_CLEAR = 0;
    private final int FRAGMENT_REMOVE = 1;

    private BaseFragment willChangeFragment;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private MainContract.Presenter mainPresenter;
    private WebView webView;
    private static TextView tvShoppingCart, tvShoppingCartETicket, tvMessageUnread;

    // toolbar
    private ConstraintLayout constClose, constBackground, constBase;
    private LinearLayout llOpen, llItemButton;

    // navigation
    private FloatingActionButton btnShopping;
    private LinearLayout layoutHome, layoutOrder, layoutPurchase, layoutMember;
    private ImageView imgHome, imgOrder, imgPurchase, imgMember;
    private TextView txtHome, txtOrder, txtPurchase, txtMember;

    // qrcode
    private PopupWindow popupWindow;
    private ImageView dialog_img_qrcode;
    private int barcodeWidth = 100;
    private int barcodeHeight = 100;
    private int brightnessNow = 0;
    private TextView text_invite_code;

    public static String sSourceActive = "";
    public static String sCustomerID = "";
    private Boolean isUpdate = false;
    private int VersionCode = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BACK_STACK_CLEAR:
                    removeExistFragment();
                    break;
                case FRAGMENT_REMOVE:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, willChangeFragment, "").commit();
                    break;
            }
            super.handleMessage(msg);
        }
    };

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
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this, getRepositoryManager(this));

        //mainPresenter.saveSourceActive("");

        sSourceActive = mainPresenter.getSourceActive();

        sCustomerID = EOrderApplication.CUSTOMER_ID;

        setCustomerData();
        initView();
        initAnimation(this);
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
        SharedUtils.getInstance().saveCustomerID(EOrderApplication.getInstance(), EOrderApplication.CUSTOMER_ID);
        sSourceActive = mainPresenter.getSourceActive();

        //  toolbar
        llOpen = findViewById(R.id.ll_title_open);
        llItemButton = findViewById(R.id.ll_item_button);
        constClose = findViewById(R.id.const_title_close);
        constBackground = findViewById(R.id.const_background);
        constBase = findViewById(R.id.const_base);

        tvShoppingCart = findViewById(R.id.tv_shopping_cart);
        tvShoppingCartETicket = findViewById(R.id.tv_shopping_cart_e_ticket);
        tvMessageUnread = findViewById(R.id.tv_message_unread);

        // floating_action_bar
        btnShopping = (FloatingActionButton) findViewById(R.id.floating_action_bar);
        btnShopping.setOnClickListener(onClickListener);

        // navigation
        layoutHome = findViewById(R.id.home);
        layoutHome.setOnClickListener(onClickListener);
        layoutOrder = findViewById(R.id.order);
        layoutOrder.setOnClickListener(onClickListener);
        layoutPurchase = findViewById(R.id.purchase);
        layoutPurchase.setOnClickListener(onClickListener);
        layoutMember = findViewById(R.id.member);
        layoutMember.setOnClickListener(onClickListener);
        imgHome = findViewById(R.id.img_home);
        imgOrder = findViewById(R.id.img_order);
        imgPurchase = findViewById(R.id.img_purchase);
        imgMember = findViewById(R.id.img_member);
        txtHome = findViewById(R.id.txt_home);
        txtOrder = findViewById(R.id.txt_order);
        txtPurchase = findViewById(R.id.txt_purchase);
        txtMember = findViewById(R.id.txt_member);

        LinearLayout qrcode = findViewById(R.id.qrcode);
        qrcode.setOnClickListener(onClickListener);
        LinearLayout message = findViewById(R.id.message);
        message.setOnClickListener(onClickListener);
        LinearLayout store = findViewById(R.id.store);
        store.setOnClickListener(onClickListener);
        LinearLayout donate = findViewById(R.id.donate);
        donate.setOnClickListener(onClickListener);

        setAppToolbar(R.id.toolbar);
        appToolbar.getBtnMail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                mainPresenter.checkLoginForMail(fragment.getClass().getSimpleName());
            }
        });
        appToolbar.getBtnMessage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                mainPresenter.checkLoginForMessage(fragment.getClass().getSimpleName());
                appToolbar.setMessageBadgeCount(Integer.parseInt("0"));
            }
        });

        appToolbar.getBtnSort().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = -1;
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                // 交易紀錄
                if (TransRecordFragment.getInstance().isVisible()) {
                    popupMenu.getMenuInflater().inflate(R.menu.menu_sort_order, popupMenu.getMenu());
                    page = 1;
                }
                // 商品列表
                else if (ProductFragment.getInstance().isVisible()) {
                    popupMenu.getMenuInflater().inflate(R.menu.menu_sort, popupMenu.getMenu());
                    page = 2;
                }
                // 企業商品列表
                else if (BusinessProductFragment.getInstance().isVisible()) {
                    popupMenu.getMenuInflater().inflate(R.menu.menu_sort, popupMenu.getMenu());
                    page = 3;
                }
                final int mPage = page;
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String sort = "NEW";
                        String filter = "";
                        int id = item.getItemId();

                        // product
                        if (id == R.id.item_CHEAP){
                            sort = "CHEAP";
                        }else if (id == R.id.item_EXPENSIVE){
                            sort = "EXPENSIVE";
                        }else if (id == R.id.item_NEW){
                            sort = "NEW";
                        }

                        // order
                        else if (id == R.id.item_order_status_all){
                            filter = "";
                        }else if (id == R.id.item_order_status_1){
                            filter = "1";
                        }else if (id == R.id.item_order_status_2){
                            filter = "2";
                        }else if (id == R.id.item_order_status_3){
                            filter = "3";
                        }else if (id == R.id.item_order_status_4){
                            filter = "4";
                        }
                        switch (mPage) {
                            case 1: //order
                                TransRecordFragment.getInstance().orderFilterMode(filter);
                                break;
                            case 2: //product
                                ProductFragment.getInstance().SortMode(sort);
                                break;
                            case 3: //business product
                                BusinessProductFragment.getInstance().SortMode(sort);
                                break;
                        }
                        return true;
                    }
                });
            }
        });
        refreshBadge();
        bottomNavigationViewEx = findViewById(R.id.navigation);
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.setIconSize(20, 20);
        for (int i = 0; i < bottomNavigationViewEx.getItemCount(); i++) {
            bottomNavigationViewEx.getIconAt(i).setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.item_news){
                    setMainIndexMessageUnreadVisibility(true);
                    changeTabFragment(MainIndexFragment.getInstance());
                }else if (id == R.id.item_shop){
                    setMainIndexMessageUnreadVisibility(false);
                    checkMerchantCount("PRODUCT", "N"); //非電子商品
                }else if (id == R.id.item_member_card){
                    setMainIndexMessageUnreadVisibility(false);
                    mainPresenter.checkLoginForMemberCenter();
                }else if (id == R.id.item_location){
                    setMainIndexMessageUnreadVisibility(false);
                    mainPresenter.saveFragmentLocation("");
                    changeTabFragment(LocationFragment.getInstance());
                }
                return true;
            }
        });



        //bottomNavigationViewEx.setCurrentItem(0);
        //====for notification 當此MainActivity是由FcmService呼叫帶起時,要直接將畫面轉至訊息中心========
        Intent intent = getIntent();
        String notifyExtra = intent.getStringExtra("NOTIFY_EXTRA"); //前景才會有值
        String notifyExtraBg = getIntent().getExtras().getString("click_action");  // 背景才會有值

        if ((notifyExtra != null && notifyExtra.equals("NOTIFY")) || (null != notifyExtraBg && notifyExtraBg.equals("NOTIFY_EXTRA"))) {
            notifyChangeToMail();
        } else {
            switch (sSourceActive){
                case "PRODUCT_WELCOME":
                case "LOCATION_WELCOME":
                    //  外帶外送 未登入的情況
                    if(sSourceActive.equals("PRODUCT_WELCOME")){
                        changeNavigationColor(R.id.order);
                    }else{
                        changeNavigationColor(R.id.home);
                    }
                    mainPresenter.saveSourceActive("");
                    mainPresenter.saveFragmentLocation("");
                    changeTabFragment(LocationFragment.getInstance());
                    break;
                case "ETICKET_WELCOME":
                    //  買提貨卷 未登入的情況
                    changeNavigationColor(R.id.purchase);
                    mainPresenter.saveSourceActive("");
                    mainPresenter.saveFragmentMainType("", "Y");
                    changeTabFragment(ProductMainTypeFragment.getInstance());
                    break;
                default:
                    changeNavigationColor(R.id.home);
                    changeTabFragment(MainIndexFragment.getInstance());
                    break;
            }
        }

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            /*下方menu*/
            if (id == R.id.home){ //首頁
                changeNavigationColor(v.getId());
                changeTabFragment(MainIndexFragment.getInstance());
            }else if (id == R.id.order){
                //  外帶外送
                changeNavigationColor(v.getId());
                mainPresenter.saveSourceActive("");
                checkMerchantCount("PRODUCT", "N"); //非電子商品
//                if (mainPresenter.getUserLogin()) {
//                    mainPresenter.saveSourceActive("");
//                    checkMerchantCount("PRODUCT", "N"); //非電子商品
//                } else {
//                    mainPresenter.saveSourceActive("PRODUCT_WELCOME");
//                    //initSelectCustomer("isSetLove");
//                    changeTabFragment(CustomerFragment.getInstance());
//                }

            }else if (id == R.id.purchase){
                //  買提貨券
                changeNavigationColor(v.getId());
                mainPresenter.saveSourceActive("");
                checkMerchantCount("PRODUCT", "Y"); //電子商品
//                if (mainPresenter.getUserLogin()) {
//                    mainPresenter.saveSourceActive("");
//                    checkMerchantCount("PRODUCT", "Y"); //電子商品
//                } else {
//                    mainPresenter.saveSourceActive("ETICKET_WELCOME");
//                    //initSelectCustomer("isSetLove");
//                    changeTabFragment(CustomerFragment.getInstance());
//                }

            }else if (id == R.id.member){ //會員中心
                changeNavigationColor(v.getId());
                mainPresenter.checkLoginForMemberCenter();
            }else if (id == R.id.floating_action_bar){ //購物車
                changeNavigationColor(v.getId());
                mainPresenter.checkLoginForShoppingCart("G");
            }

            /*首頁上方menu*/
            else if (id == R.id.qrcode){
                if (mainPresenter.getUserLogin()) {
                    View view = LayoutInflater.from(willChangeFragment.getActivity()).inflate(R.layout.dialog_qrcode, null);

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
                    intentToLogin(REQUEST_MAIN_INDEX);
                }
            }else if (id == R.id.message){
                setMainIndexMessageUnreadVisibility(false);
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                mainPresenter.checkLoginForMail(fragment.getClass().getSimpleName());
            }else if (id == R.id.store){
                addFragment(MachineFragment.getInstance());
//                if(mainPresenter.getUserLogin()){
//                    mainPresenter.saveFragmentLocation("");
//                    addFragment(LocationFragment.getInstance());
//                }else{
//                    mainPresenter.saveSourceActive("LOCATION_WELCOME");
//                    addFragment(LocationFragment.getInstance());
//                }
            }else if (id == R.id.donate){
                setMainIndexMessageUnreadVisibility(false);
                mainPresenter.checkLoginForDonate();
            }
        }
    };

    public void changeNavigationColor(int layoutID) {

        if (layoutID == R.id.home){
            setMainIndexMessageUnreadVisibility(true);
            changeHomeColor(true);
            changeOrderColor(false);
            changeStoreColor(false);
            changeMemberColor(false);
        }else if (layoutID == R.id.order){
            setMainIndexMessageUnreadVisibility(false);
            changeHomeColor(false);
            changeOrderColor(true);
            changeStoreColor(false);
            changeMemberColor(false);
        }else if (layoutID == R.id.purchase){
            setMainIndexMessageUnreadVisibility(false);
            changeHomeColor(false);
            changeOrderColor(false);
            changeStoreColor(true);
            changeMemberColor(false);
        }else if (layoutID == R.id.member){
            setMainIndexMessageUnreadVisibility(false);
            changeHomeColor(false);
            changeOrderColor(false);
            changeStoreColor(false);
            changeMemberColor(true);
        }
    }

    private void changeHomeColor(boolean isClicked) {
        if (isClicked) {
            imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home_fill));
            txtHome.setTextColor(getResources().getColor(R.color.colorYunlinhn));
        } else {
            imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home_line));
            txtHome.setTextColor(getResources().getColor(R.color.colorYunlinhn));
        }
    }

    private void changeOrderColor(boolean isClicked) {
        if (isClicked) {
            imgOrder.setImageDrawable(getResources().getDrawable(R.drawable.bag_fill));
            txtOrder.setTextColor(getResources().getColor(R.color.colorYunlinhn));
        } else {
            imgOrder.setImageDrawable(getResources().getDrawable(R.drawable.bag_line));
            txtOrder.setTextColor(getResources().getColor(R.color.colorYunlinhn));
        }
    }

    private void changeStoreColor(boolean isClicked) {
        if (isClicked) {
            imgPurchase.setImageDrawable(getResources().getDrawable(R.drawable.cart_fill));
            txtPurchase.setTextColor(getResources().getColor(R.color.colorYunlinhn));
        } else {
            imgPurchase.setImageDrawable(getResources().getDrawable(R.drawable.cart_line));
            txtPurchase.setTextColor(getResources().getColor(R.color.colorYunlinhn));
        }
    }

    private void changeMemberColor(boolean isClicked) {
        if (isClicked) {
            imgMember.setImageDrawable(getResources().getDrawable(R.drawable.member_fill));
            txtMember.setTextColor(getResources().getColor(R.color.colorYunlinhn));
        } else {
            imgMember.setImageDrawable(getResources().getDrawable(R.drawable.member_line));
            txtMember.setTextColor(getResources().getColor(R.color.colorYunlinhn));
        }
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
                setMainIndexMessageUnreadVisibility(true);
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
                setMainIndexMessageUnreadVisibility(false);
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
                changeTabFragment(MainIndexFragment.getInstance());
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
                addFragment(MessageListFragment.getInstance());
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

    // 判斷畫面上顯示的是否為MainIndexFragment
    public boolean isMainIndex() {
        boolean isMainIndex = false;
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        switch (fragmentList.size()) {
            case 0:
                isMainIndex = false;
                break;
            case 1:
                if (getSupportFragmentManager().getFragments().get(0).equals(MainIndexFragment.getInstance())) {
                    isMainIndex = true;
                }
                break;
            case 2:
                for (Fragment f : fragmentList) {
                    if (f.equals(MainIndexFragment.getInstance())) {
                        isMainIndex = true;
                    }
                }
                break;
            default:
                isMainIndex = false;
        }
        return isMainIndex;
    }

    public void OpenShared(){
        // 获取剪贴板管理器
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String copiedText = mainPresenter.getUserName() + " 邀請您下載 " + EOrderApplication.CUSTOMER_NAME + " APP，註冊時輸入邀請碼: " + mainPresenter.getInvitationCode() + "，即可獲得新會員獎勵";
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
    }

    @Override
    public void setAllBadge(String count) {
        // messageUnreadNum_pushUnreadNum_cartTotalQuantity
        String[] array = count.split("_");

        // 訊息夾未讀、客服留言未讀
        if (array.length >= 2) {
            appToolbar.setMessageBadgeCount(Integer.parseInt(array[0]));
            appToolbar.setMailBadgeCount(Integer.parseInt(array[1]));

            if (array[1].equals("0") || !isMainIndex()) {
                tvMessageUnread.setText("0");
                setMainIndexMessageUnreadVisibility(false);
            } else {
                tvMessageUnread.setText(array[1]);
                setMainIndexMessageUnreadVisibility(true);
            }
        } else {
            appToolbar.setMessageBadgeCount(Integer.parseInt("0"));
            appToolbar.setMailBadgeCount(Integer.parseInt("0"));
            tvMessageUnread.setText("0");
            setMainIndexMessageUnreadVisibility(false);
        }

        // 購物車商品數量
        if (array.length == 4) {
            if (array[3].equals("0")) {
                tvShoppingCart.setVisibility(View.GONE);
            } else {
                tvShoppingCart.setVisibility(View.VISIBLE);
                tvShoppingCart.setText(array[3]);
            }
            if (array[2].equals("0")) {
                tvShoppingCartETicket.setVisibility(View.GONE);
            } else {
                tvShoppingCartETicket.setVisibility(View.VISIBLE);
                tvShoppingCartETicket.setText(array[2]);
            }
        }
        // 購物車沒東西時未讀數量會收到空字串
        else {
            tvShoppingCart.setVisibility(View.GONE);
            tvShoppingCartETicket.setVisibility(View.GONE);
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

    public BottomNavigationViewEx getBottomNavigationViewEx() {
        return bottomNavigationViewEx;
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
        Log.e(TAG, "changeTabFragment" + willChangeFragment + "");
        removeAllStackFragment();
    }

    @Override
    public void addFragment(BaseFragment baseFragment) {
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
        webView.getSettings().setUseWideViewPort(true);
        webView.addJavascriptInterface(new AndroidJsInterface(), "hamels");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
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
                JSONObject oMemberData = new JSONObject(getUser().toString());
                sData = oMemberData.getString(Info);
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
            String isETicket = salesType.equals("E") ? "Y" : "N";
            goProductPage(isETicket);
        }
        @JavascriptInterface
        public void jsCall_goHomePage() {
            changeTabFragment(MainIndexFragment.getInstance());
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
    }

    private void savePaySchemeOrderData(String sParam){
        getRepositoryManager(this).savePaySchemeOrderData(sParam);
    }

    public void detachWebView() {
        webView = null;
    }

    public void setTabPage(int page) {
        bottomNavigationViewEx.setCurrentItem(page);
    }

    public void resetPassword() {
        setTabPage(0);
        mainPresenter.logout();
    }

    public void hideBottomNavigation() {
        bottomNavigationViewEx.setVisibility(View.GONE);
    }

    public void showBottomNavigation() {
        bottomNavigationViewEx.setVisibility(View.VISIBLE);
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
            if (currentPage.indexOf("orderDetail.html") > 0 && goBackPage.indexOf("order.html") > 0) {
                webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_ORDER_URL + "?orderType=" + "");
            } else if (currentPage.indexOf("order.html") > 0) {
                changeTabFragment(MemberCenterFragment.getInstance());
                webView = null;
            } else {
                webView.goBack();
            }
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                // 首頁才顯示回上一頁
                if (isMainIndex()) {
                    new AlertDialog.Builder(this).setTitle(null).setMessage(R.string.close_hint)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();

                } else {
                    // 其他就顯示首頁
                    changeNavigationColor(R.id.home);
                    changeTabFragment(MainIndexFragment.getInstance());
                }

//                boolean isAlert= false;
//                for(Fragment f: getSupportFragmentManager().getFragments()){
//                    // 首頁才顯示回上一頁
//                    if(f.equals(MainIndexFragment.getInstance())){
//                        new AlertDialog.Builder(this).setTitle(null).setMessage(R.string.close_hint)
//                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        System.exit(0);
//                                    }
//                                })
//                                .setNegativeButton(android.R.string.no, null)
//                                .show();
//                        isAlert= true;
//                        break;
//                    }
//                }
//                // 其他就顯示首頁
//                if(!isAlert){
//                    changeNavigationColor(R.id.home);
//                    changeTabFragment(MainIndexFragment.getInstance());
//                }
            } else {
                // 點推播通知進來的
                if (getSupportFragmentManager().getBackStackEntryCount() == 1
                        && getSupportFragmentManager().getFragments().get(0).equals(MailFileFragment.getInstance())) {
                    changeNavigationColor(R.id.home);
                    changeTabFragment(MainIndexFragment.getInstance());
                } else {
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame);

                    if(currentFragment instanceof ProductDetailFragment){   //  商品內頁
                        addFragment(ProductFragment.getInstance());
                    }else if(currentFragment instanceof ProductFragment){   //  商品列表
                        addFragment(ProductMainTypeFragment.getInstance());
                    }else if(currentFragment instanceof ProductMainTypeFragment){
                        changeTabFragment(MainIndexFragment.getInstance());
                    } else{
                        super.onBackPressed();
                    }
                }
            }
        }
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
            tvShoppingCart.setVisibility(View.VISIBLE);
            tvShoppingCart.setText(Count);
        } else {
            tvShoppingCart.setVisibility(View.GONE);
        }
    }

    public void setMainIndexMessageUnreadVisibility(boolean isVisible) {
        if (isVisible && (!tvMessageUnread.getText().toString().equals("") && !tvMessageUnread.getText().toString().equals("0"))) {
            tvMessageUnread.setVisibility(View.VISIBLE);
        } else {
            tvMessageUnread.setVisibility(View.GONE);
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
                    changeNavigationColor(R.id.purchase); // 買提貨券
                } else {
                    changeNavigationColor(R.id.order); //我要點餐
                }
            }
        });

        checkMerchantCount("PRODUCT", isETicket);
    }

    // 商店清單: 當商店數量小於一時，直接顯示商品清單
    public void checkMerchantCount(final String type, final String isETicket) {

        mainPresenter.saveFragmentMainType("", isETicket);
        changeTabFragment(ProductMainTypeFragment.getInstance());

//        if (isETicket.equals("Y")) {
//            //  買提貨卷
//            mainPresenter.saveFragmentMainType("", isETicket);
//            changeTabFragment(ProductMainTypeFragment.getInstance());
//        } else {
//            //  外帶外送
//            mainPresenter.saveFragmentLocation("");
//            addFragment(LocationFragment.getInstance());
//        }


//        BasePresenter basePresenter = new BasePresenter(this, getRepositoryManager(this));

//        MemberRepository.getInstance().getMerchantList(sCustomerID, new ApiCallback<BaseModel<List<Merchant>>>(basePresenter) {
//            @Override
//            public void onApiSuccess(BaseModel<List<Merchant>> response) {
//                super.onApiSuccess(response);
//                List<Merchant> merchantList = response.getItems();
//                if (merchantList != null) {
//                    switch (type) {
//                        case "PRODUCT":
//                            if(isETicket.equals("Y")){
//                                changeTabFragment(ProductMainTypeFragment.getInstance(0, isETicket));
//                            }else{
//                                if (merchantList.size() == 1) {
//                                    //int merchantID = merchantList.get(0).getId();
//                                    changeTabFragment(ProductMainTypeFragment.getInstance(merchantList.get(0).getId(), isETicket));
//                                } else {
//                                    changeTabFragment(ProductMerchantFragment.getInstance("PRODUCT", isETicket));
//                                }
//                            }
//                            break;
//                        case "STORE":
//                            if (merchantList.size() == 1) {
//                                addFragment(LocationFragment.getInstance(merchantList.get(0).getId()));
//                            } else {
//                                addFragment(ProductMerchantFragment.getInstance("STORE", isETicket));
//                            }
//                            break;
//                    }
//                }
//            }
//        });
    }

    private void createQRcodeImage(String qrcodeNum, ImageView qrcode_img) {
        if (qrcodeNum != null && !qrcodeNum.equals("")) {
            qrcode_img.setBackgroundColor(Color.WHITE);
            if (willChangeFragment.isAdded()) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = new MultiFormatWriter().encode(qrcodeNum, BarcodeFormat.QR_CODE, barcodeWidth, barcodeHeight);

                    int newWidth = 300;
                    int newHeight = 300;

                    Bitmap bitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
                    float scaleX = (float) newWidth / barcodeWidth;
                    float scaleY = (float) newHeight / barcodeHeight;
                    for (int x = 0; x < barcodeWidth; x++) {
                        for (int y = 0; y < barcodeHeight; y++) {
                            if (bitMatrix.get(x, y)) {
                                for (int scaledX = (int) (x * scaleX); scaledX < (int) ((x + 1) * scaleX); scaledX++) {
                                    for (int scaledY = (int) (y * scaleY); scaledY < (int) ((y + 1) * scaleY); scaledY++) {
                                        bitmap.setPixel(scaledX, scaledY, Color.BLACK);
                                    }
                                }
                            }
                        }
                    }
                    qrcode_img.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            brightnessNow = getSystemBrightness();
            changeAppBrightness(255);
        }
    }

    private int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(willChangeFragment.getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }

    public void changeAppBrightness(int brightness) {
        Window window = willChangeFragment.getActivity().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
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
}