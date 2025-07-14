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
        ((BaseActivity) requireActivity()).setAppToolbar(appToolbarId);
    }

    @Override
    public void setAppTitle(int resString) {
        ((BaseActivity) requireActivity()).setAppTitle(resString);
    }

    @Override
    public void setCartBadge(int CartBadgeID, int CartBadgeTicketID) {
        ((BaseActivity) requireActivity()).setCartBadge(CartBadgeID, CartBadgeTicketID);
    }

    public void setAppTitleString(String sAppTitle) {
        ((BaseActivity) requireActivity()).setAppTitleString(sAppTitle);
    }

    @Override
    public void setAppBadge() {
        ((BaseActivity) requireActivity()).setAppBadge();
    }

    @Override
    public void setAppToolbarVisibility(boolean isVisible) {
        ((BaseActivity) requireActivity()).setAppToolbarVisibility(isVisible);
    }

    @Override
    public void setCartBadgeVisibility(boolean isVisible) {
        ((BaseActivity) requireActivity()).setCartBadgeVisibility(isVisible);
    }

    @Override
    public void setBackButtonVisibility(boolean isVisible) {
        ((BaseActivity) requireActivity()).setBackButtonVisibility(isVisible);
    }

    @Override
    public void setMailButtonVisibility(boolean isVisible) {
        ((BaseActivity) requireActivity()).setMailButtonVisibility(isVisible);
    }

    @Override
    public void setMessageButtonVisibility(boolean isVisible) {
        ((BaseActivity) requireActivity()).setMessageButtonVisibility(isVisible);
    }

    @Override
    public void setSortButtonVisibility(boolean isVisible) {
        ((BaseActivity) requireActivity()).setSortButtonVisibility(isVisible);
    }

    @Override
    public void showToast(String message) {
        ((BaseActivity) requireActivity()).showToast(message);
    }

    @Override
    public void checkPermission(BaseContract.ValueCallback<Boolean> callback, String... permission) {
        ((BaseActivity) requireActivity()).checkPermission(callback, permission);
    }

    @Override
    public void showLoadingDialog() {
        if (isAdded()) {
            ((BaseActivity) requireActivity()).showLoadingDialog();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (isAdded()) {
            ((BaseActivity) requireActivity()).hideLoadingDialog();
        }
    }
}
