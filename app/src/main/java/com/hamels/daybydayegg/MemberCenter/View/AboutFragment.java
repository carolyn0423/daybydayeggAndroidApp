package com.hamels.daybydayegg.MemberCenter.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Contract.AboutContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.AboutPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.About;
import com.hamels.daybydayegg.Utils.IntentUtils;
import com.hamels.daybydayegg.Utils.ViewUtils;

import java.util.List;

public class AboutFragment extends BaseFragment implements AboutContract.View{
    public static final String TAG = AboutFragment.class.getSimpleName();

    private static AboutFragment fragment;
    private AboutContract.Presenter presenter;

    private TextView tv_office, tv_office_address, tv_office_service, tv_office_tel, tv_office_line, tv_office_mail, tv_office_fb;
//    private TextView tv_enterprise_service, tv_enterprise_tel, tv_enterprise_mail;

    public static AboutFragment getInstance() {
        if (fragment == null) {
            fragment = new AboutFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new AboutPresenter(this, getRepositoryManager(getContext()));
        presenter.getAboutData();
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.contact_us);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        tv_office = view.findViewById(R.id.tv_office);
        tv_office_address = view.findViewById(R.id.tv_office_address);
        tv_office_service = view.findViewById(R.id.tv_office_service);
        tv_office_tel = view.findViewById(R.id.tv_office_tel);
        tv_office_line = view.findViewById(R.id.tv_office_line);
        tv_office_mail = view.findViewById(R.id.tv_office_mail);
        tv_office_fb = view.findViewById(R.id.tv_office_fb);
//        tv_enterprise_service = view.findViewById(R.id.tv_enterprise_service);
//        tv_enterprise_tel = view.findViewById(R.id.tv_enterprise_tel);
//        tv_enterprise_mail = view.findViewById(R.id.tv_enterprise_mail);
    }

    @Override
    public void setAboutData(List<About> aboutList) {
        final About office = aboutList.get(0);
//        final About enterprise = aboutList.get(1);

        tv_office.setText(office.getAbout_name());
        tv_office_address.setText(office.getAddress());
        tv_office_service.setText(office.getService_time());
        tv_office_tel.setText(office.getPhone());
        ViewUtils.addUnderLine(tv_office_tel);
        tv_office_line.setText(office.getLine_at());
        tv_office_mail.setText(office.getEmail());
        ViewUtils.addUnderLine(tv_office_mail);
        tv_office_fb.setText(office.getFb_url());
        ViewUtils.addUnderLine(tv_office_fb);
//        tv_enterprise_service.setText(enterprise.getService_time());
//        tv_enterprise_tel.setText(enterprise.getPhone());
//        tv_enterprise_mail.setText(enterprise.getEmail());

//        tv_office_tel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tv_enterprise_tel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tv_office_mail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tv_enterprise_mail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tv_office_fb.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        tv_office_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.callPhone(tv_office_tel.getText().toString());
            }
        });
//        tv_enterprise_tel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.callPhone(tv_enterprise_tel.getText().toString());
//            }
//        });
        tv_office_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.intentToEmail((BaseActivity) getActivity(), tv_office_mail.getText().toString());
            }
        });
//        tv_enterprise_mail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                IntentUtils.intentToEmail((BaseActivity) getActivity(), tv_enterprise_mail.getText().toString());
//            }
//        });
        tv_office_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.intentToOutWeb((BaseActivity) getActivity(), tv_office_fb.getText().toString());
            }
        });
    }

    @Override
    public void intentToPhoneCall(String phone) {
        IntentUtils.intentToCall((BaseActivity) getActivity(), phone);
    }
}
