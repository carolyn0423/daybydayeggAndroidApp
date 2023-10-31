package com.hamels.daybydayegg.Main.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Main.Contract.MainIndexContract;
import com.hamels.daybydayegg.Main.Presenter.MainIndexPresenter;
import com.hamels.daybydayegg.MemberCenter.View.MemberPointFragment;
import com.hamels.daybydayegg.MemberCenter.View.WebViewFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Carousel;
import com.hamels.daybydayegg.Utils.IntentUtils;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static com.hamels.daybydayegg.Constant.Constant.REQUEST_MAIN_INDEX;

import static com.hamels.daybydayegg.EOrderApplication.*;

public class MainIndexFragment extends BaseFragment implements MainIndexContract.View{
    public static final String TAG = MainIndexFragment.class.getSimpleName();

    private static MainIndexFragment fragment;
    private XBanner mXBanner;
    private ConstraintLayout layout_coupon, layout_point, layout_aboutegg, layout_man, layout_eggfood;
    private MainIndexContract.Presenter mainindexPresenter;
    private TextView tvCouponNum, tvPointNum;
    public static MainIndexFragment getInstance() {
        if (fragment == null) {
            fragment = new MainIndexFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        mainindexPresenter = new MainIndexPresenter(this, getRepositoryManager(getContext()));

        ((MainActivity) getActivity()).setCustomerData();

        initView(view);
        return view;
    }
    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_index);
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setBackButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(true);
        ((MainActivity) getActivity()).setAppToolbarVisibility(false);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(true);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        mXBanner = view.findViewById(R.id.xbanner);

        tvCouponNum = view.findViewById(R.id.coupon_num);
        tvPointNum = view.findViewById(R.id.point_num);

//        // 獲取屏幕高度
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int screenHeight = displayMetrics.heightPixels;
//
//        // 設置 mXBanner 的高度為屏幕高度
//        mXBanner.setLayoutParams(new ConstraintLayout.LayoutParams(
//                ConstraintLayout.LayoutParams.MATCH_PARENT,
//                screenHeight
//        ));

        //  優惠劵
        layout_coupon = view.findViewById(R.id.layout_coupon);
        layout_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainindexPresenter.getUserLogin()) {
                    ((MainActivity) getActivity()).addFragment(WebViewFragment.getInstance(R.string.coupon, EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_COUPONS_URL));
                }else{
                    ((MainActivity) getActivity()).intentToLogin(REQUEST_MAIN_INDEX);
                }
            }
        });

        //  點數
        layout_point = view.findViewById(R.id.layout_point);
        layout_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainindexPresenter.getUserLogin()) {
                    ((MainActivity) getActivity()).addFragment(MemberPointFragment.getInstance());
                }else{
                    ((MainActivity) getActivity()).intentToLogin(REQUEST_MAIN_INDEX);
                }
            }
        });

        //  認識雞蛋
        layout_aboutegg = view.findViewById(R.id.layout_aboutegg);
        layout_aboutegg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //  職人介紹
        layout_man = view.findViewById(R.id.layout_man);
        layout_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //  雞蛋美味
        layout_eggfood = view.findViewById(R.id.layout_eggfood);
        layout_eggfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mainindexPresenter.getCarouselList(CUSTOMER_ID);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //mainindexPresenter.getCarouselList(CUSTOMER_ID);
    }

    @Override
    public void intentToLogin(int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.getActivity().startActivityForResult(intent, requestCode);
    }

    public void CustomerOnlineISFalse() {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("此商家無開放APP購物").setPositiveButton(android.R.string.ok, null).show();
        ((MainActivity) Objects.requireNonNull(getActivity())).resetPassword();
    }

    @Override
    public void setMemberCouponPointData(String sTxt) {
        String[] array= sTxt.split("_");

        if(array.length >= 5){
            //  優惠劵
            int iCouponNum = Integer.parseInt(array[4]);
            if(iCouponNum > 999){
                tvCouponNum.setText("999+");
            }else{
                tvCouponNum.setText(iCouponNum + "");
            }
        }else{
            tvCouponNum.setText("0");
        }

        if(array.length >= 6){
            //  點數
            int iPoint = Integer.parseInt(array[5]);
            if(iPoint > 999){
                tvPointNum.setText("999+");
            }else{
                tvPointNum.setText(iPoint + "");
            }
        }else{
            tvPointNum.setText("0");
        }
    }

    @Override
    public void setCarouselList(final List<Carousel> carouselList) {
        List<CustomViewsInfo> data = new ArrayList<>();
        String sDoMain = EOrderApplication.sApiUrl.equals("") ? ADMIN_DOMAIN : EOrderApplication.sApiUrl;
        for(int i = 0 ; i < carouselList.size();i++){
            data.add(new CustomViewsInfo(sDoMain + carouselList.get(i).getPicture_url(),carouselList.get(i).getId()));
        }
        mXBanner.setBannerData(R.layout.layout_main_activity_center_crop, data);
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ImageView img_carousel = (ImageView) view.findViewById(R.id.img_carousel);
                Glide
                    .with(getActivity())
                        .load(((CustomViewsInfo) model).getXBannerUrl())
                        // .load(R.drawable.e_order)
                    .into(img_carousel);
            }
        });
        mXBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Log.e(TAG,carouselList.get(position).getTitle());
                ((MainActivity) getActivity()).addFragment(NewsFragment.getInstance(carouselList.get(position)));
//                ((MainActivity) getActivity()).addFragment(NewsFragment.getInstance(((CustomViewsInfo)model).getXBannerTitle()));
//                ((MainActivity) getActivity()).goNewsDetail(((CustomViewsInfo)model).getXBannerTitle());
            }
        });

        mainindexPresenter.checkMemberData();

        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).refreshBadge();
        }
    }
}

