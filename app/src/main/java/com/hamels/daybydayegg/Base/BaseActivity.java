package com.hamels.daybydayegg.Base;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Repository.RepositoryManager;
import com.hamels.daybydayegg.Widget.AppToolbar;
import me.leolin.shortcutbadger.ShortcutBadger;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

import static com.hamels.daybydayegg.Constant.PermissionConstant.P_PHONE;

@RuntimePermissions
public class BaseActivity extends AppCompatActivity implements BaseContract.View {
    protected AppToolbar appToolbar;
    private BaseContract.ValueCallback<Boolean> callback;
    private ProgressDialog progressDialog;
    private TextView tvShoppingCart, tvShoppingCartETicket;
    private Boolean mStateEnable;
    private PopupWindow popupWindow;
    @Override
    public RepositoryManager getRepositoryManager(Context context) {
        return new RepositoryManager(context);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
    }
    @Override
    protected void onStart() {
        super.onStart();
        // super.onStart();中将mStateSaved置为false
        mStateEnable = true;
    }
    @Override
    protected void onResume() {
        // onPause之后便可能调用onSaveInstanceState，因此onresume中也需要置true
        mStateEnable = true;
        super.onResume();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState();中将mStateSaved置为true
        mStateEnable = false;
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onStop() {
        // super.onStop();中将mStateSaved置为true
        mStateEnable = false;
        super.onStop();
    }
    /**
     *
     * activity状态是否处于可修改周期内，避免状态丢失的错误
     * @return
     */
    public boolean isStateEnable() {
        return mStateEnable;
    }
    @Override
    public void setAppToolbar(@IdRes int appToolbarId) {
        appToolbar = findViewById(appToolbarId);
    }

    @Override
    public void setCartBadge(@IdRes int CartBadgeID, @IdRes int CartBadgeTicketID) {
        //tvShoppingCart = findViewById(CartBadgeID);
        tvShoppingCartETicket = findViewById(CartBadgeTicketID);
    }

    @Override
    public void setAppTitle(@StringRes int resString) {
        if (appToolbar != null) {
            appToolbar.getTvTitle().setText(resString);
        }
    }

    public void setAppTitleString(String sAppTitle) {
        if (appToolbar != null) {
            appToolbar.setTvTitle(sAppTitle);
        }
    }

    public void setAppBadge() {
        int appBadgeCount = appToolbar.getiMailCount();
        appBadgeCount += appToolbar.getiMessageCount();
        if(appBadgeCount > 0) {
            ShortcutBadger.applyCount(getApplicationContext(), appBadgeCount);
        }else{
            ShortcutBadger.removeCount(getApplicationContext());
        }
    }
    @Override
    public void setAppToolbarVisibility(boolean isVisible) {
        if (appToolbar != null) {
            appToolbar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setCartBadgeVisibility(boolean isVisible) {
        if (tvShoppingCart != null) {
            if(isVisible && !EOrderApplication.cartBadgeCount.equals("0")){
                tvShoppingCart.setVisibility(View.VISIBLE);
            }else{
                tvShoppingCart.setVisibility(View.GONE);
            }
        }

        if (tvShoppingCartETicket != null) {
            if(isVisible && !EOrderApplication.cartTicketBadgeCount.equals("0")){
                tvShoppingCartETicket.setVisibility(View.VISIBLE);
            }else{
                tvShoppingCartETicket.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setBackButtonVisibility(boolean isVisible) {
        if (appToolbar != null) {
            appToolbar.getBtnBack().setVisibility(isVisible ? View.VISIBLE : View.GONE);
            appToolbar.getBtnBack().setOnClickListener(isVisible ? new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            } : null);
        }
    }

    public void setBackButtonVisibilityNoClick(boolean isVisible) {
        if (appToolbar != null) {
            appToolbar.getBtnBack().setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setMailButtonVisibility(boolean isVisible) {
        appToolbar.getBtnMail().setVisibility(isVisible ? View.VISIBLE : View.GONE);
        if(isVisible){
            if(appToolbar.getiMailCount() > 0){
                if (appToolbar.getBtnMail().getVisibility() == View.VISIBLE){
                    if (appToolbar.getBtnMail().getVisibility() == View.VISIBLE) {
                        appToolbar.getMailBadge().setVisibility(isVisible ? View.VISIBLE : View.GONE);
                    }
                }
                else{
                    appToolbar.getMailBadge().setVisibility(View.GONE);
                }
            }
            else{
                appToolbar.getMailBadge().setVisibility(View.GONE);
            }
        }
        else{
            appToolbar.getMailBadge().setVisibility(View.GONE);
        }

    }

    @Override
    public void setMessageButtonVisibility(boolean isVisible) {
        appToolbar.getBtnMessage().setVisibility(isVisible ? View.VISIBLE : View.GONE);
        if (appToolbar.getMessageBadge().getVisibility() == View.VISIBLE) {
            appToolbar.getMessageBadge().setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setSortButtonVisibility(boolean isVisible) {
        appToolbar.getBtnSort().setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkPermission(BaseContract.ValueCallback<Boolean> callback, String... permission) {
        this.callback = callback;
        for (String s : permission) {
            if (s.equals(Manifest.permission.CALL_PHONE)) {
                BaseActivityPermissionsDispatcher.onPhoneGrantWithPermissionCheck(this);
            }
        }
    }

    @Override
    public void showLoadingDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    public void onPhoneGrant() {
        if (callback != null) {
            callback.onValueCallback(P_PHONE, true);
        }
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    public void onPhoneDenied() {
        if (callback != null) {
            callback.onValueCallback(P_PHONE, false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}