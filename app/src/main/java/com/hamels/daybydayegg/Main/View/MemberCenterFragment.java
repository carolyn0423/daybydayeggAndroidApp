package com.hamels.daybydayegg.Main.View;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.Contract.MemberCenterContract;
import com.hamels.daybydayegg.Main.Presenter.MemberCenterPresenter;
import com.hamels.daybydayegg.MemberCenter.View.AboutFragment;
import com.hamels.daybydayegg.MemberCenter.View.FaqFragment;
import com.hamels.daybydayegg.MemberCenter.View.MemberGiftFragment;
import com.hamels.daybydayegg.MemberCenter.View.MemberInfoChangeFragment;
import com.hamels.daybydayegg.MemberCenter.View.MemberPointFragment;
import com.hamels.daybydayegg.MemberCenter.View.MessageListFragment;
import com.hamels.daybydayegg.MemberCenter.View.PasswordChangeFragment;
import com.hamels.daybydayegg.MemberCenter.View.WebViewFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Repository.Model.User;

import java.util.Objects;

public class MemberCenterFragment extends BaseFragment implements View.OnClickListener, MemberCenterContract.View {
    public static final String TAG = MemberCenterFragment.class.getSimpleName();

    private LinearLayout btnChangePassword, btnTransRecord, btnPoint, btnCoupon, btnContactUs, btnPrivacy, btnTerms, btnCustomerservice, btnMemberGift;
    private TextView tvName, tvPoint, tvPhone;
    private ImageView btnMemberInfo;
    private PopupWindow popupWindow;
    private ConstraintLayout btnlogout;

    private int barcodeWidth = 350;
    private int barcodeHeight = 350;
    private int brightnessNow = 0;

    private static MemberCenterFragment fragment;
    private MemberCenterContract.Presenter memberPresenter;

    public static MemberCenterFragment getInstance() {
        if (fragment == null) {
            fragment = new MemberCenterFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_center, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        memberPresenter = new MemberCenterPresenter(this, getRepositoryManager(getContext()));
        memberPresenter.getUserInfo();
        memberPresenter.getPointData();
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_member_info);
        ((MainActivity) getActivity()).refreshBadge();

        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);

        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);

        memberPresenter = new MemberCenterPresenter(this, getRepositoryManager(getContext()));

        btnlogout = view.findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(this);

//        img_qrcode = view.findViewById(R.id.img_qrcode);
//        img_qrcode.setOnClickListener(this);
        tvName = view.findViewById(R.id.tv_name);
        tvPoint = view.findViewById(R.id.tv_point);
        tvPhone = view.findViewById(R.id.tv_phone);

        btnMemberInfo = view.findViewById(R.id.btn_member_info);
        btnMemberInfo.setOnClickListener(this);
        btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnChangePassword.setOnClickListener(this);

        btnTransRecord = view.findViewById(R.id.btn_trans_record);
        btnTransRecord.setOnClickListener(this);
        btnPoint = view.findViewById(R.id.btn_point);
        btnPoint.setOnClickListener(this);
        btnCoupon = view.findViewById(R.id.btn_coupon);
        btnCoupon.setOnClickListener(this);

        btnContactUs = view.findViewById(R.id.btn_contact_us);
        btnContactUs.setOnClickListener(this);
        btnPrivacy = view.findViewById(R.id.btn_privacy);
        btnPrivacy.setOnClickListener(this);
        btnTerms = view.findViewById(R.id.btn_terms);
        btnTerms.setOnClickListener(this);

        btnCustomerservice = view.findViewById(R.id.btn_customerservice);
        btnCustomerservice.setOnClickListener(this);

        btnMemberGift = view.findViewById(R.id.btn_member_gift);
        btnMemberGift.setOnClickListener(this);

        if(!memberPresenter.getUserLogin()){
            memberPresenter.saveSourceActive("");
            ((MainActivity) getActivity()).changeTabFragment(MainIndexFragment.getInstance());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_member_info){
            ((MainActivity) getActivity()).addFragment(MemberInfoChangeFragment.getInstance());
        }else if (id == R.id.btn_change_password){
            ((MainActivity) getActivity()).addFragment(PasswordChangeFragment.getInstance());
        }else if (id == R.id.btn_point){
            ((MainActivity) getActivity()).addFragment(MemberPointFragment.getInstance());
        }else if (id == R.id.btn_coupon){
            ((MainActivity) getActivity()).addFragment(WebViewFragment.getInstance(R.string.coupon, EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_COUPONS_URL));
        }else if (id == R.id.btn_contact_us){
            ((MainActivity) getActivity()).addFragment(AboutFragment.getInstance());
        }else if (id == R.id.btn_terms){
            ((MainActivity) getActivity()).addFragment(FaqFragment.getInstance("1"));
        }else if (id == R.id.btn_privacy){
            ((MainActivity) getActivity()).addFragment(FaqFragment.getInstance("2"));
        }else if (id == R.id.btn_trans_record){
            ((MainActivity) getActivity()).addFragment(TransRecordFragment.getInstance("G", "", ""));
        }else if (id == R.id.btn_logout){
            memberPresenter.logout();
        }else if (id == R.id.btn_customerservice){
            ((MainActivity) getActivity()).addFragment(MessageListFragment.getInstance());
//                Toast.makeText(getActivity(), "此功能未開放", Toast.LENGTH_LONG).show();
        }else if(id == R.id.btn_member_gift){
            ((MainActivity) getActivity()).addFragment(MemberGiftFragment.getInstance());
        }
    }

    @Override
    public void setUserName(String name) {
        tvName.setText(name);
    }

    @Override
    public void setUserPoint(String point) {
        tvPoint.setText(point);
    }

    @Override
    public void setUserMobile(String mobile) {
        tvPhone.setText(mobile);
    }

    @Override
    public void redirectToMainPage() {
        ((MainActivity) Objects.requireNonNull(getActivity())).setTabPage(0);
        ((MainActivity) Objects.requireNonNull(getActivity())).refreshBadge();
    }

    @Override
    public void setPointData(User user) {
        tvPoint.setText(user.getPoint());
        //tvExpireTime.setText(String.format(getString(R.string.expire_date), Calendar.getInstance().get(Calendar.YEAR) + "/12/31",user.getBonusexpiredsoon()));
    }

    public void deleteMember(){
        new AlertDialog.Builder(getContext()).setTitle(null).setMessage(R.string.logout_success)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        memberPresenter.logout();
                    }
                })
                .show();
    }
}