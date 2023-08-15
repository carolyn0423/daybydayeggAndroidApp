package com.hamels.daybydayegg.Init;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hamels.daybydayegg.Base.BaseActivity;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Utils.IntentUtils;
import com.hamels.daybydayegg.Utils.SharedUtils;

public class SplashActivity extends BaseActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "getInstanceId failed", task.getException());
                            startInitApp();
                            return;
                        }
                        String token = task.getResult().getToken();
                        SharedUtils.getInstance().setFirebaseToken(SplashActivity.this, token);
                        startInitApp();
                    }
                });

        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
    }

    private void startInitApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //  20230731 Leslie 不顯示開啟提醒畫面
                IntentUtils.intentToMain(SplashActivity.this, true, EOrderApplication.CUSTOMER_ID,false);

//                if (SharedUtils.getInstance().getUserFirstOpenApp(SplashActivity.this)) {
//                    SharedUtils.getInstance().setUserOpenApp(SplashActivity.this);
//                    IntentUtils.intentToNotificationControl(SplashActivity.this);
//                } else {
//                    IntentUtils.intentToMain(SplashActivity.this, true, EOrderApplication.CUSTOMER_ID,false);
//                }
                finish();

            }
        }, 2000);
    }
}
