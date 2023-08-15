package com.hamels.daybydayegg.Login.VIew;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.Presenter.VerifyCodePresenter;
import com.hamels.daybydayegg.Login.Contract.VerifyCodeContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Utils.IntentUtils;
import com.hamels.daybydayegg.Utils.ViewUtils;

import java.util.ArrayList;

public class VerifyCodeActivity extends BaseActivity implements VerifyCodeContract.View {
    public static final String TAG = VerifyCodeActivity.class.getSimpleName();

    private TextView tvHint, tvResend;
    private EditText etFirst, etSecond, etThird, etFourth;
    private ImageButton btnSend;
    private ArrayList<EditText> etList;
    private VerifyCodeContract.Presenter verifyPresenter;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        initView();
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.sms_verify);
        setBackButtonVisibility(false);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);
        setAppToolbarVisibility(true);

        tvHint = findViewById(R.id.tv_hint);
        tvHint.setVisibility(View.GONE);

        tvResend = findViewById(R.id.tv_resend);
        ViewUtils.addUnderLine(tvResend);
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                verifyPresenter.resendSms();
            }
        });

        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPresenter.checkVerifyCodeAndFinish(etFirst.getText().toString() + etSecond.getText().toString()
                        + etThird.getText().toString() + etFourth.getText().toString());
            }
        });

        etList = new ArrayList<>();
        etFirst = findViewById(R.id.et_first);
        etFirst.addTextChangedListener(new AutoFocusWatchLayout(etFirst));
        etList.add(etFirst);

        etSecond = findViewById(R.id.et_second);
        etSecond.addTextChangedListener(new AutoFocusWatchLayout(etSecond));
        etList.add(etSecond);

        etThird = findViewById(R.id.et_third);
        etThird.addTextChangedListener(new AutoFocusWatchLayout(etThird));
        etList.add(etThird);

        etFourth = findViewById(R.id.et_fourth);
        etFourth.addTextChangedListener(new AutoFocusWatchLayout(etFourth));
        etList.add(etFourth);

        verifyPresenter = new VerifyCodePresenter(this, getRepositoryManager(this));

        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean(VerifyCodeActivity.TAG)) {
            verifyPresenter.resendSms();
        }

        startTimer();
    }

    private void startTimer() {
        tvResend.setEnabled(false); // 禁用按钮

        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                tvResend.setTextColor(Color.GRAY);
                tvResend.setText("重新發送(" + secondsRemaining + "秒)");
            }

            @Override
            public void onFinish() {
                tvResend.setEnabled(true); // 启用按钮
                tvResend.setTextColor(Color.BLACK);
                tvResend.setText("重新發送");
            }
        };

        timer.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
    @Override
    public void showResendHint(String hint) {
        tvHint.setVisibility(View.VISIBLE);
        tvHint.setTextColor(getResources().getColor(R.color.orangeText));
        tvHint.setText(hint);
//        tvHint.setText(R.string.verify_code_resend);
    }

    @Override
    public void showWrongVerifyHint() {
        tvHint.setVisibility(View.VISIBLE);
        tvHint.setTextColor(getResources().getColor(R.color.redHint));
        tvHint.setText(R.string.verify_code_wrong);
    }

    @Override
    public void showWrongVerifyHint(String hint) {
        tvHint.setVisibility(View.VISIBLE);
        tvHint.setTextColor(getResources().getColor(R.color.redHint));
        tvHint.setText(hint);
    }

    @Override
    public void showWrongVerifyHint(final boolean isSuccess, String hint) {
        String errorString = getResources().getString(R.string.regist_no_pos_member_id);

        // pos sync error
        if(hint.equals(errorString)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_hint)
                    .setMessage(hint)
                    // 稍後回來: 導回首頁
                    .setNegativeButton(R.string.give_up,  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            verifyPresenter.logout();
                        }
                    })
                    // 重試驗證: 重新驗證簡訊+打註冊api給pos
                    .setPositiveButton(R.string.retry_verify, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            verifyPresenter.resendSms();
                        }
                    })
                    .show();
        }else{
            new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(hint).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(isSuccess){
                        verifyPresenter.goLogin();
                    }
                }
            }).show();
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void finishActivity() {
        IntentUtils.intentToMain(this, true, EOrderApplication.CUSTOMER_ID,false);
    }

    class AutoFocusWatchLayout implements TextWatcher {
        private EditText currentEditText;

        public AutoFocusWatchLayout(EditText currentEditText) {
            this.currentEditText = currentEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                for (int i = 0; i < etList.size() - 1; i++) {
                    if (etList.get(i).equals(currentEditText)) {
                        etList.get(i + 1).requestFocus();
                    }
                }
            }
        }
    }
}
