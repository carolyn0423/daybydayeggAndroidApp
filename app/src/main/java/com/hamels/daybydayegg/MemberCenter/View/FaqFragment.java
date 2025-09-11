package com.hamels.daybydayegg.MemberCenter.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Contract.FaqContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Faq;

import java.util.Objects;

public class FaqFragment extends BaseFragment implements FaqContract.View{
    public static final String TAG = FaqFragment.class.getSimpleName();

    private static FaqFragment fragment;
    private String faq_id;
    private static final String FAQ_ID = "faq_id";
    private WebView webView;

    public static FaqFragment getInstance(String faq_id) {
        if (fragment == null) {
            fragment = new FaqFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(FAQ_ID, faq_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cketitor, container, false);
        if (getArguments() != null) {
            faq_id = getArguments().getString(FAQ_ID, "");
            initView(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view) {
        switch (faq_id){
            case "1":
                ((MainActivity) getActivity()).setAppTitle(R.string.privacy_policy);
                break;
            case "2":
                ((MainActivity) getActivity()).setAppTitle(R.string.terms_of_use);
                break;
        }

        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        //((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        webView = view.findViewById(R.id.web_view);
        ((MainActivity) getActivity()).bindWebView(webView);

        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_CONTENT_URL + "?mode=Faq&id=" + faq_id);
    }

    @Override
    public void setFaqData(Faq faq) {

    }
    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) requireActivity()).detachWebView();
    }
}
