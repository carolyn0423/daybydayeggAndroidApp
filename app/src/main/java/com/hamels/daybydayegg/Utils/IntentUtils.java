package com.hamels.daybydayegg.Utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.StringRes;
import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.Base.WebViewActivity;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Init.NotificationControlActivity;
import com.hamels.daybydayegg.Login.VIew.ForgetPasswordActivity;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Login.VIew.CustomerActivity;
import com.hamels.daybydayegg.Login.VIew.RegisterActivity;
import com.hamels.daybydayegg.Login.VIew.ScannerActivity;
import com.hamels.daybydayegg.Login.VIew.TermsOfUseActivity;
import com.hamels.daybydayegg.Login.VIew.VerifyCodeActivity;
import com.hamels.daybydayegg.Main.View.MainActivity;

import static com.hamels.daybydayegg.Constant.Constant.PHONE;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_MEMBER_CENTER;
import static com.hamels.daybydayegg.Constant.Constant.TITLE;
import static com.hamels.daybydayegg.Constant.Constant.URL;

public class IntentUtils {
    public static void intentToNotificationControl(BaseActivity baseActivity) {
        Intent intent = new Intent(baseActivity, NotificationControlActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        baseActivity.startActivity(intent);
    }

    public static void intentToSystemPermissionSetting(BaseActivity baseActivity) {
        Intent intent = new Intent();
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", baseActivity.getPackageName());
        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", baseActivity.getPackageName());
            intent.putExtra("app_uid", baseActivity.getApplicationInfo().uid);
        } else {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + baseActivity.getPackageName()));
        }

        baseActivity.startActivity(intent);
    }

    public static void intentToLogin(BaseActivity baseActivity, int requestCode) {
        Intent intent = new Intent(baseActivity, LoginActivity.class);
        baseActivity.startActivityForResult(intent, requestCode);
    }

    public static void intentToCustomer(BaseActivity baseActivity, String sMode) {
        Intent intent = new Intent(baseActivity, CustomerActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("MODE", sMode);
        intent.putExtras(bundle);
        baseActivity.startActivity(intent);
    }

    public static void intentToForgetPassword(BaseActivity baseActivity, String cellphone) {
        Intent intent = new Intent(baseActivity, ForgetPasswordActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(PHONE, cellphone);
        intent.putExtras(bundle);
        baseActivity.startActivity(intent);
    }

    public static void intentToRegister(BaseActivity baseActivity) {
        Intent intent = new Intent(baseActivity, RegisterActivity.class);
        baseActivity.startActivity(intent);
    }

    public static void intentToScanner(BaseActivity baseActivity) {
        Intent intent = new Intent(baseActivity, ScannerActivity.class);
        baseActivity.startActivity(intent);
    }

    public static void intentToTermsOfUse(BaseActivity baseActivity) {
        Intent intent = new Intent(baseActivity, TermsOfUseActivity.class);
        baseActivity.startActivity(intent);
    }

    public static void intentToVerifyCode(BaseActivity baseActivity, boolean needResendSms) {
        Intent intent = new Intent(baseActivity, VerifyCodeActivity.class);

        Bundle bundle = new Bundle();
        bundle.putBoolean(VerifyCodeActivity.TAG, needResendSms);
        intent.putExtras(bundle);
        baseActivity.startActivity(intent);
    }

    public static void intentToMain(BaseActivity baseActivity, boolean needClearStack, String sCustomerID, boolean showMemberCenter) {
        Intent intent = new Intent(baseActivity, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("CUSTOMER_ID", sCustomerID);
        intent.putExtras(bundle);
        if (needClearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(MainActivity.TAG, true);
        }

        if(showMemberCenter){
            baseActivity.startActivityForResult(intent, REQUEST_MEMBER_CENTER);
        }else{
            baseActivity.startActivity(intent);
        }
    }


    public static void intentToWebView(BaseActivity baseActivity, @StringRes int stringRes, String url) {
        Intent intent = new Intent(baseActivity, WebViewActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt(TITLE, stringRes);
        bundle.putString(URL, url);
        intent.putExtras(bundle);

        baseActivity.startActivity(intent);
    }

    public static void intentToGoogleMap(BaseActivity baseActivity, String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        baseActivity.startActivity(mapIntent);
    }

    public static void intentToShare(BaseActivity baseActivity, String text) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        baseActivity.startActivity(Intent.createChooser(sharingIntent, ""));
    }

    public static void intentToEmail(BaseActivity baseActivity, String text) {
        String[] TO = {text};
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        baseActivity.startActivity(Intent.createChooser(sharingIntent, ""));
    }

    public static void intentToOutWeb(BaseActivity baseActivity, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        baseActivity.startActivity(intent);
    }

    @SuppressLint("MissingPermission")
    public static void intentToCall(BaseActivity baseActivity, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        baseActivity.startActivity(intent);
    }
}
