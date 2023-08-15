package com.hamels.daybydayegg.Login.VIew;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.Html;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Contract.FaqContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.FaqPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Faq;

public class TermsOfUseActivity extends BaseActivity implements FaqContract.View{
    public static final String TAG = MainActivity.class.getSimpleName();

    private TextView tv_faq_data;
    private FaqContract.Presenter presenter;
    private ConstraintLayout layoutContent;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        presenter = new FaqPresenter(this, getRepositoryManager(this));
        presenter.getFaqData("1");

        initView();
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.privacy_policy);
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);
        setAppToolbarVisibility(true);

        tv_faq_data = findViewById(R.id.tv_faq_data);
        layoutContent = findViewById(R.id.layout_content);
    }

    @Override
    public void setFaqData(Faq faq) {
        tv_faq_data.setText(Html.fromHtml(faq.getAnswer()));

        Resources res = this.getResources();
        drawable = res.getDrawable(R.drawable.bg_shadow_corner);
        layoutContent.setBackground(drawable);
    }
}