package com.hamels.daybydayegg.Main.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Main.Contract.NewsContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Carousel;

import java.util.Objects;

public class NewsFragment extends BaseFragment implements NewsContract.View {
    public static final String TAG = NewsFragment.class.getSimpleName();

    private static NewsFragment fragment;
    private static final String NEWS_ID = "news_id";
    private TextView  tv_news_title;
    private ImageView imageView;
    private Carousel carousel;
    private WebView webView;
    public static NewsFragment getInstance(Carousel carousel) {
        if (fragment == null) {
            fragment = new NewsFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(Carousel.TAG, carousel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        if (getArguments() != null && getArguments().containsKey(Carousel.TAG)) {
            carousel = getArguments().getParcelable(Carousel.TAG);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        setAppTitle(R.string.tab_news);
        setBackButtonVisibility(true);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        //((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        setAppToolbarVisibility(true);

        webView = view.findViewById(R.id.web_view);
        ((MainActivity) getActivity()).bindWebView(webView);

        imageView = view.findViewById(R.id.imageView);
        tv_news_title = view.findViewById(R.id.tv_news_title);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String sPictureUrl = carousel.getPicture_url2() == null || carousel.getPicture_url2().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : carousel.getPicture_url2();
        Glide.with(getActivity()).load(EOrderApplication.sApiUrl + sPictureUrl).into(imageView);
        tv_news_title.setText(carousel.getTitle());
        
        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_CONTENT_URL + "?mode=News&id=" + carousel.getId());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) requireActivity()).detachWebView();
    }
}
