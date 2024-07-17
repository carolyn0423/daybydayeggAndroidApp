package com.hamels.daybydayegg.Login.VIew;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.Contract.LoginContract;
import com.hamels.daybydayegg.Login.Presenter.LoginPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.ApiRepository.ApiRepository;
import com.hamels.daybydayegg.Repository.ApiRepository.MemberRepository;
import com.hamels.daybydayegg.Repository.Model.Customer;
import com.hamels.daybydayegg.Utils.IntentUtils;
import com.hamels.daybydayegg.Utils.SharedUtils;
import com.hamels.daybydayegg.Utils.ViewUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity implements LoginContract.View{
    public static final String TAG = LoginActivity.class.getSimpleName();
    private TextView tvEOrder, tvTitleHint, tvTermsOfUse, tvForgetPsd, tvSearchCustomer, tvScan;
    private EditText etPhone, etPassword;
    private ConstraintLayout btnRegister, btnLogin;
    private LoginContract.Presenter loginPresenter;

    private String sCustomerID = EOrderApplication.CUSTOMER_ID;

    final Activity activity = this;

//    private Handler uiHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 1:
//                    new AlertDialog.Builder(activity).setTitle(R.string.dialog_hint).setMessage("掃描結果 : " + msg.obj.toString())
//                            .setPositiveButton("將此商家加入最愛", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    loginPresenter.checkCustomerNo(msg.obj.toString());
//
//                                    intentToLogin();
//                                }
//                            })
//                            .show();
//                    break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenter(this, getRepositoryManager(this));

        initView();
    }

    //Add by Carolyn 20220730讓選擇最愛商家頁點擊回車鍵時,此頁會重新refresh
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
        initView();

//        if(loginPresenter.getUserLogin()){
//            setResultOkFinishActivity();
//        }
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.login);
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);
        setAppToolbarVisibility(true);

        //  清除API 暫存, 重新取得URL
        ApiRepository.repository = null;
        MemberRepository.memberRepository = null;
        ApiRepository.getInstance();
        MemberRepository.getInstance();

        tvTitleHint = findViewById(R.id.tv_title_hint);
        ViewUtils.addUnderLine(tvTitleHint);

        //  選擇登入商家(從最愛挑選)
//        tvEOrder = findViewById(R.id.tv_eorder);
//        String sLoveCustomer = SharedUtils.getInstance().getLoveCustomer(this);
//        tvEOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(sLoveCustomer.equals((""))){
//                    showErrorAlert("請添加最愛商家");
//                }else{
//                    //loginPresenter.saveSourceActive("Login");
//                    initSelectCustomer("isSelectLoveCustomer");
//                }
//
//            }
//        });
//        ViewUtils.addUnderLine(tvEOrder);

        //  商家搜尋
        tvSearchCustomer = findViewById(R.id.tv_search_customer);
        tvSearchCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginPresenter.saveSourceActive("Login");
                initSelectCustomer("isSetLove");
            }
        });
        ViewUtils.addUnderLine(tvSearchCustomer);

        //  掃碼
        tvScan = findViewById(R.id.tv_scan);
        tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), qrCode, Toast.LENGTH_SHORT).show();
                //initScanner();
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        tvTermsOfUse = findViewById(R.id.tv_terms_of_use);
        tvTermsOfUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToTermsOfUse();
            }
        });
        ViewUtils.addUnderLine(tvTermsOfUse);

        tvForgetPsd = findViewById(R.id.tv_forget_psd);
        tvForgetPsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EOrderApplication.CUSTOMER_ID.equals("")){
                    showErrorAlert("請選擇商家");
                }else {
                    if(chkMobile(etPhone.getText().toString())) {
                        loginPresenter.checkAccount(SharedUtils.getInstance().getCustomerID(EOrderApplication.getInstance()), etPhone.getText().toString());
                    }else{
                        showErrorAlert("手機格式錯誤");
                    }
                }
            }
        });
        ViewUtils.addUnderLine(tvForgetPsd);

        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EOrderApplication.CUSTOMER_ID.equals("")){
                    showErrorAlert("請選擇商家");
                }else{
                    loginPresenter.register();
                }

            }
        });

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EOrderApplication.CUSTOMER_ID.equals("")){
                    showErrorAlert("請選擇商家");
                }else{
                    if(chkMobile(etPhone.getText().toString())) {
                        loginPresenter.login(EOrderApplication.CUSTOMER_ID, etPhone.getText().toString(), etPassword.getText().toString());
                    }else{
                        showErrorAlert("手機格式錯誤");
                    }
                }

            }
        });

        loginPresenter.getCustomer();
    }

    @Override
    public void onBackPressed() {
        IntentUtils.intentToMain(this, false, EOrderApplication.CUSTOMER_ID,false, true);
        setResult(RESULT_OK);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!= null)
        {
            if (result.getContents()==null)
            {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_SHORT).show();
            }
            else
            {
//                Message msg = new Message();
//                msg.what = 1;
//                msg.obj = result.getContents();
//                uiHandler.sendMessage(msg);

                //Toast.makeText(this, "掃描結果 : " + result.getContents(), Toast.LENGTH_SHORT).show();
                getCheckCustomerNo(result.getContents());

//                new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage("掃描結果 : " + result.getContents())
//                        .setPositiveButton("將此商家加入最愛", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                loginPresenter.checkCustomerNo(result.getContents());
//
//                                intentToLogin();
//                            }
//                        })
//                        .show();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void doForgetPassword(String customer_id,String cellphone) {
        loginPresenter.forgetPassword(customer_id,cellphone);
    }

    public void getCheckCustomerNo(String sCutomerNo){
        loginPresenter.checkCustomerNo(sCutomerNo);
    }

    private boolean chkMobile(String sMobile){
        // 定義手機號碼的正則表達式
        String phoneRegex = "^(09)\\d{8}$"; // 符合台灣手機號碼格式的正則表達式

        // 建立 Pattern 物件
        Pattern pattern = Pattern.compile(phoneRegex);

        // 使用 Matcher 進行檢驗
        Matcher matcher = pattern.matcher(sMobile);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }

    }

    public void setCustomerList(List<Customer> customers){
        if(customers.size() > 0) {
            //tvEOrder.setText(customers.get(0).getCustomerName());
            EOrderApplication.CUSTOMER_ID = customers.get(0).getCustomerID();
            EOrderApplication.CUSTOMER_NAME = customers.get(0).getCustomerName();
            EOrderApplication.sApiUrl = customers.get(0).getApiUrl();
            EOrderApplication.dbConnectName = customers.get(0).getdbConnectName();

            ApiRepository.repository = null;
            MemberRepository.memberRepository = null;
            ApiRepository.getInstance();
            MemberRepository.getInstance();
        }else{
            //tvEOrder.setText("全部商家");
            EOrderApplication.CUSTOMER_ID = "";
            EOrderApplication.CUSTOMER_NAME = "";
            EOrderApplication.sApiUrl = "";

            loginPresenter.checkCustomerNo(EOrderApplication.sPecialCustomerNo);
        }
    }

    @Override
    public void setCustomer(Customer customers, String sCustomerID, String sCustomerName, String sApiUrl) {
        //tvEOrder = findViewById(R.id.tv_eorder);
        if((customers == null || customers.getCustomerName() == null) && (sCustomerID.equals("") || sCustomerName.equals("")) || EOrderApplication.sApiUrl.equals("")){
            //  判斷是否有符合的最愛，若無則顯示 [全部商家]
            String sLoveCustomer = loginPresenter.getLoveCustomer();
            if(!sLoveCustomer.equals("")){
                loginPresenter.getCustomerDetailList(sLoveCustomer);
            }else {
                //tvEOrder.setText("全部商家");
                EOrderApplication.CUSTOMER_ID = "";
                EOrderApplication.CUSTOMER_NAME = "";
                EOrderApplication.sApiUrl = "";

                loginPresenter.checkCustomerNo(EOrderApplication.sPecialCustomerNo);
            }
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

    @Override
    public void showNoInputCellphoneHint() {
        tvTitleHint.setTextColor(getResources().getColor(R.color.redHint));
        tvTitleHint.getPaint().setFlags(0);
        tvTitleHint.setText(R.string.forget_password_error_hint);
        tvTitleHint.setVisibility(View.VISIBLE);
    }

    @Override
    public void intentToRegister() {
        IntentUtils.intentToRegister(this);
    }

    @Override
    public void intentToForgetPassword(String customer_id,String cellphone) {
        IntentUtils.intentToForgetPassword(this, cellphone);
    }

    public void initSelectCustomer(String sMode) {
        IntentUtils.intentToCustomer(this, sMode);
    }

    public void initScanner() {
        IntentUtils.intentToScanner(this);
    }

    @Override
    public void intentToTermsOfUse() {
//        IntentUtils.intentToWebView(this, R.string.privacy_policy, EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_TERMS_URL);
        IntentUtils.intentToTermsOfUse(this);
    }

    @Override
    public void showEmptyPhoneAlert() {
        showErrorAlert(getString(R.string.dialog_empty_phone));
    }
    @Override
    public void showEmptyPasswordAlert() { showErrorAlert(getString(R.string.dialog_empty_password)); }
    public void showEmptyCustomerNoAlert() { showErrorAlert(getString(R.string.dialog_empty_customer_no)); }
    public void showErrorCustomerNoAlert() { showErrorAlert(getString(R.string.dialog_error_customer)); }
    public void showAddLoveAlert() {
        showErrorAlert(getString(R.string.dialog_add_love_customer));
    }
    public void showExistLoveAlert() { showErrorAlert(getString(R.string.dialog_love_customer_exist)); }
    @Override
    public void showErrorAlert(String message) {
        if(!LoginActivity.this.isFinishing()){
            new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        }
    }

    public void showToast(String sMessageCode){
        switch (sMessageCode){
            case "EmptyCustomerNo":
                Toast.makeText(this, getString(R.string.dialog_empty_customer_no), Toast.LENGTH_SHORT).show();
                break;
            case "ErrorCustomerNo":
                Toast.makeText(this, getString(R.string.dialog_error_customer), Toast.LENGTH_SHORT).show();
                break;
            case "AddLove":
                Toast.makeText(this, getString(R.string.dialog_add_love_customer), Toast.LENGTH_SHORT).show();
                break;
            case "ExistLove":
                Toast.makeText(this, getString(R.string.dialog_love_customer_exist), Toast.LENGTH_SHORT).show();
                break;
            case "CustomerNoOnline":
                Toast.makeText(this, getString(R.string.dialog_customer_no_online), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showVerifyErrorAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_hint)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IntentUtils.intentToVerifyCode(LoginActivity.this, true);
                    }
                })
                .show();
    }

    @Override
    public void showGoRegistAlert(String message) {
        new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loginPresenter.register();
                    }
                })
                .show();
    }

    @Override
    public void showGoCustomerAlert(String message) {
        new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //loginPresenter.saveSourceActive("Login");
                        loginPresenter.initSelectCustomer("isSetLove");
                    }
                })
                .show();
    }

    @Override
    public void setResultOkFinishActivity() {
        //  防止登入後自動跳轉到交易明細頁
        getRepositoryManager(this).savePaySchemeOrderData("");
        IntentUtils.intentToMain(this, true, EOrderApplication.CUSTOMER_ID,false, true);
        setResult(RESULT_OK);
        finish();
    }
}