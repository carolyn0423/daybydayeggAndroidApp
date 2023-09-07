package com.hamels.daybydayegg.MemberCenter.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Contract.FaqContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.FaqPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Faq;
import com.hamels.daybydayegg.Utils.PicassoImageGetter;

public class FaqFragment extends BaseFragment implements FaqContract.View{
    public static final String TAG = FaqFragment.class.getSimpleName();

    private static FaqFragment fragment;
    private FaqContract.Presenter presenter;
    private String faq_id;
    private static final String FAQ_ID = "faq_id";

    private TextView tv_faq_data;

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
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        if (getArguments() != null) {
            faq_id = getArguments().getString(FAQ_ID, "");
            initView(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FaqPresenter(this, getRepositoryManager(getContext()));
        presenter.getFaqData(faq_id);
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
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        tv_faq_data = view.findViewById(R.id.tv_faq_data);
    }

    @Override
    public void setFaqData(Faq faq) {
        PicassoImageGetter imageGetter = new PicassoImageGetter(getContext(), tv_faq_data);
        Spannable html;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            html = (Spannable) Html.fromHtml(faq.getAnswer(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
        } else {
            html = (Spannable) Html.fromHtml(faq.getAnswer(), imageGetter, null);
        }

        tv_faq_data.setText(html);

        //tv_faq_data.setText(Html.fromHtml(faq.getAnswer()));
    }
}
