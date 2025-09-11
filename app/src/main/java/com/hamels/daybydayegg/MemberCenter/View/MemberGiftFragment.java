package com.hamels.daybydayegg.MemberCenter.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Contract.MemberGiftContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.MemberGiftPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Coupon;

public class MemberGiftFragment extends BaseFragment implements MemberGiftContract.View, View.OnClickListener {
    public static final String TAG = MemberGiftFragment.class.getSimpleName();

    private static MemberGiftFragment fragment;
    private TextView tvInvitationCode, tvShareInfoText2, tvShareInfoText3, tvShareInfoText5;
    private Button btnShareButton;
    private MemberGiftPresenter memberGiftPresenter;
    private Coupon coupon;

    public static MemberGiftFragment getInstance() {
        if (fragment == null) {
            fragment = new MemberGiftFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_gift, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.title_member_gift);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        //((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        tvInvitationCode = view.findViewById(R.id.invitation_code);
        tvShareInfoText2 = view.findViewById(R.id.shareInfoText2);
        tvShareInfoText3 = view.findViewById(R.id.shareInfoText3);
        tvShareInfoText5 = view.findViewById(R.id.shareInfoText5);
        memberGiftPresenter = new MemberGiftPresenter(this, getRepositoryManager(getContext()));

        tvInvitationCode.setText(memberGiftPresenter.getInvitationCode());
        memberGiftPresenter.getCouponData();

        btnShareButton = view.findViewById(R.id.shareButton);
        btnShareButton.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        memberGiftPresenter = new MemberGiftPresenter(this, getRepositoryManager(getContext()));
        memberGiftPresenter.getCouponData();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shareButton){
            ((MainActivity) getActivity()).OpenShared();
        }
    }

    public void setCouponData(Coupon coupon){
        String ShareInfoText2 = coupon.getDiscountModeRN().equals("1") ? coupon.getDiscountContentRN() + " 元優惠卷" : coupon.getDiscountContentRN() + " 折優惠卷";
        String ShareInfoText3 = coupon.getDiscountModeRM().equals("1") ? coupon.getDiscountContentRM() + " 元優惠卷" : coupon.getDiscountContentRM() + " 折優惠卷";
        String ShareInfoText4 = coupon.getLowerLimitRM().equals("0") ? "將可獲得" : "消費金額達 " + coupon.getLowerLimitRM() + " 元，您將可獲得";
        String ShareInfoText5 = coupon.getPermanentFlag().equals("Y") ? "無" : coupon.getValidityStartdate() + " ~ " + coupon.getValidityEnddate();

        tvShareInfoText2.setText("1. 好友完成註冊，將可獲得新會員獎勵: " + ShareInfoText2);
        tvShareInfoText3.setText("2. 好友首次消費，" + ShareInfoText4 + "推薦人獎勵: " + ShareInfoText3);
        tvShareInfoText5.setText("4. 活動期限: " + ShareInfoText5);
    }
}
