package com.hamels.daybydayegg.Donate.View;

import static com.hamels.daybydayegg.Constant.Constant.REQUEST_MAIN_INDEX;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.RepositoryManager;
import com.hamels.daybydayegg.Widget.AppToolbar;

import java.util.Objects;

public class DeliverCartFragment extends BaseFragment {
    public static final String TAG = DeliverCartFragment.class.getSimpleName();

    private static DeliverCartFragment fragment;
    private WebView webView;
    protected AppToolbar appToolbar;
    private RepositoryManager repositoryManager;
    private String sCode = "";
    public static DeliverCartFragment getInstance(String sCode) {
        if (fragment == null) {
            fragment = new DeliverCartFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("TOCKET_CODE", sCode);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        if (getArguments() != null) {
            sCode = getArguments().getString("TOCKET_CODE", "");
        }
        initView(view);

        return view;
    }

    private void initView(View view) {
        webView = view.findViewById(R.id.web_view);
        ((MainActivity) getActivity()).refreshBadge();
        setBackButtonVisibility(true);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).bindWebView(webView);
        ((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        repositoryManager = getRepositoryManager(getContext());

        if(EOrderApplication.CUSTOMER_ID.equals("") || EOrderApplication.sApiUrl.equals("") || EOrderApplication.dbConnectName.equals("") ||
                repositoryManager == null || repositoryManager.getCustomerID() == null || repositoryManager.getCustomerID().equals("")){
            new androidx.appcompat.app.AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("登入資訊不完整，請重新登入")
                    .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
                            fragment.getActivity().startActivityForResult(intent, REQUEST_MAIN_INDEX);
                        }
                    }).show();
        }else {
            webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_DELIVER_CART_URL + "?ticket_code=" + sCode);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) requireActivity()).detachWebView();
    }
}
