package com.hamels.daybydayegg.Base;

import android.content.Context;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.Objects;

public class BaseFragment extends Fragment implements BaseContract.View {
    @Override
    public RepositoryManager getRepositoryManager(Context context) {
        return new RepositoryManager(context);
    }

    @Override
    public void setAppToolbar(@IdRes int appToolbarId) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setAppToolbar(appToolbarId);
    }

    @Override
    public void setAppTitle(int resString) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setAppTitle(resString);
    }

    @Override
    public void setCartBadge(int CartBadgeID, int CartBadgeTicketID) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setCartBadge(CartBadgeID, CartBadgeTicketID);
    }

    public void setAppTitleString(String sAppTitle) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setAppTitleString(sAppTitle);
    }

    @Override
    public void setAppBadge() {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setAppBadge();
    }

    @Override
    public void setAppToolbarVisibility(boolean isVisible) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setAppToolbarVisibility(isVisible);
    }

    @Override
    public void setCartBadgeVisibility(boolean isVisible) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setCartBadgeVisibility(isVisible);
    }

    @Override
    public void setBackButtonVisibility(boolean isVisible) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setBackButtonVisibility(isVisible);
    }

    @Override
    public void setMailButtonVisibility(boolean isVisible) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setMailButtonVisibility(isVisible);
    }

    @Override
    public void setMessageButtonVisibility(boolean isVisible) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setMessageButtonVisibility(isVisible);
    }

    @Override
    public void setSortButtonVisibility(boolean isVisible) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).setSortButtonVisibility(isVisible);
    }

    @Override
    public void showToast(String message) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).showToast(message);
    }

    @Override
    public void checkPermission(BaseContract.ValueCallback<Boolean> callback, String... permission) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).checkPermission(callback, permission);
    }

    @Override
    public void showLoadingDialog() {
        if (isAdded()) {
            ((BaseActivity) Objects.requireNonNull(getActivity())).showLoadingDialog();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (isAdded()) {
            ((BaseActivity) Objects.requireNonNull(getActivity())).hideLoadingDialog();
        }
    }
}
