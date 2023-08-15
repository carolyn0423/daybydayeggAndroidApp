package com.hamels.daybydayegg.Init;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Utils.IntentUtils;

public class NotificationControlActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = NotificationControlActivity.class.getSimpleName();

    private TextView btnSetting;
    private ImageButton btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_control);
        initView();
        showAlert();
    }

    private void initView() {
        btnSetting = findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(this);

        btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(this);
    }

    private void showAlert() {
        new AlertDialog.Builder(this).setTitle(R.string.notification_control_title).setMessage(R.string.notification_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IntentUtils.intentToSystemPermissionSetting(NotificationControlActivity.this);
                    }
                }).setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_setting){
            IntentUtils.intentToSystemPermissionSetting(this);

        }else if (id == R.id.btn_close){
            IntentUtils.intentToMain(this, true, EOrderApplication.CUSTOMER_ID,false);
            finish();
        }
    }
}
