package com.hamels.daybydayegg.Login.VIew;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.Html;
import android.text.Spannable;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Contract.FaqContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.FaqPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Faq;
import com.hamels.daybydayegg.Utils.PicassoImageGetter;

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
        PicassoImageGetter imageGetter = new PicassoImageGetter(this, tv_faq_data);
        Spannable html;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            html = (Spannable) Html.fromHtml(faq.getAnswer(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
        } else {
            html = (Spannable) Html.fromHtml(faq.getAnswer(), imageGetter, null);
        }

        tv_faq_data.setText(html);

        //tv_faq_data.setText(Html.fromHtml(faq.getAnswer()));

        Resources res = this.getResources();
        drawable = res.getDrawable(R.drawable.bg_shadow_corner);
        layoutContent.setBackground(drawable);
    }
}