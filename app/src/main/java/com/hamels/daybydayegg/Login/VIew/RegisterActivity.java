package com.hamels.daybydayegg.Login.VIew;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.Login.Presenter.RegisterPresenter;
import com.hamels.daybydayegg.Login.Contract.RegisterContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Utils.IntentUtils;
import com.hamels.daybydayegg.Utils.ViewUtils;
import com.hamels.daybydayegg.Widget.SpinnerDatePickerDialog;

import java.util.Calendar;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    public static final String TAG = RegisterActivity.class.getSimpleName();

    private ScrollView scrollView;
    private TextView tvErrorMessage, tvBirth;
    private EditText etName, etPhone, etPassword, etRePassword, etInvitationCode;
    private RadioGroup radioGender;
    private CheckBox cbTermsOfUse;
    private ConstraintLayout btnRegister;

    private SpannableString spannableString;
    private RegisterContract.Presenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.register);
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);
        setAppToolbarVisibility(true);

        scrollView = findViewById(R.id.scrollview);
        tvErrorMessage = findViewById(R.id.tv_error_message);
        etName = findViewById(R.id.et_name);
        tvBirth = findViewById(R.id.tv_birthday);
        tvBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        etRePassword = findViewById(R.id.et_repassword);
        etInvitationCode = findViewById(R.id.et_invitation_code);
        radioGender = findViewById(R.id.group_gender);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPresenter.checkInputValue(etName.getText().toString()
                        , radioGender.getCheckedRadioButtonId()
                        , tvBirth.getText().toString()
                        , etPhone.getText().toString()
                        , etPassword.getText().toString()
                        , etRePassword.getText().toString()
                        , etInvitationCode.getText().toString()
                        , cbTermsOfUse.isChecked());
            }
        });

        String cbWording = getString(R.string.register_agree_terms_of_use);
        spannableString = new SpannableString(cbWording);
        spannableString.setSpan(new CheckboxClickableSpan(), cbWording.indexOf(getString(R.string.member_terms_of_use)), cbWording.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        cbTermsOfUse = findViewById(R.id.cb_terms_of_use);
        cbTermsOfUse.setOnCheckedChangeListener(null);
        cbTermsOfUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbTermsOfUse.setText(spannableString);
            }
        });
        cbTermsOfUse.setText(spannableString);
        cbTermsOfUse.setMovementMethod(LinkMovementMethod.getInstance());

        registerPresenter = new RegisterPresenter(this, getRepositoryManager(this));
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        DatePickerDialog datePickerDialog;
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            datePickerDialog = new SpinnerDatePickerDialog(this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    tvBirth.setText(i + "-" + (i1 + 1) + "-" + i2);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            datePickerDialog = new DatePickerDialog(this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    tvBirth.setText(i + "-" + (i1 + 1) + "-" + i2);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }

        ViewUtils.colorizeDatePicker(datePickerDialog.getDatePicker());
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public void showErrorMessage(int stringRes) {
        scrollView.smoothScrollTo(0, 0);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(stringRes);
    }

    @Override
    public void showErrorMessage(String message) {
        scrollView.smoothScrollTo(0, 0);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(message);
    }

    @Override
    public void intentToTermsOfUse() {
//        IntentUtils.intentToWebView(this, R.string.privacy_policy, EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_TERMS_URL);
        IntentUtils.intentToTermsOfUse(this);
    }

    @Override
    public void intentToVerifyCode() {
        tvErrorMessage.setVisibility(View.GONE);
        IntentUtils.intentToVerifyCode(this, true);
    }

    class CheckboxClickableSpan extends ClickableSpan {

        @Override
        public void onClick(@NonNull View widget) {
            widget.cancelPendingInputEvents();
            intentToTermsOfUse();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setFakeBoldText(true);
            ds.setColor(cbTermsOfUse.isChecked() ? getResources().getColor(R.color.orangeText) : Color.BLACK);
            ds.setUnderlineText(true);
        }
    }
}
