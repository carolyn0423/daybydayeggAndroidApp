package com.hamels.daybydayegg.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.R;

public class FcmService extends FirebaseMessagingService {
    public static final String TAG = FcmService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "onMessageReceived" );
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            //remoteMessage.getData().get("badge");
            //uiRefresh();
        }

    }
    private void showNotification(String title,String message){
        if(!title.equals("WRITE_OFF_MESSAGE")) {
            String channelId = getResources().getString(R.string.default_notification_channel_id);
            String channelName = "ChannelName";
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("NOTIFY_EXTRA", "NOTIFY");
            //        Intent intent=new Intent("NOTIFY_EXTRA");
            //PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    NotificationChannel mChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                    mChannel.enableVibration(true); //震??置
                    mChannel.setSound(defaultSoundUri, null); //?置提示音，IMPORTANCE_DEFAULT及以上才?有?音
                    notificationManager.createNotificationChannel(mChannel);
                    Notification.Builder builder =
                            new Notification.Builder(this)
                                    .setSmallIcon(R.drawable.ic_fcm_logo)
                                    .setColor(getResources().getColor(R.color.main))
                                    .setContentTitle(title)
                                    .setContentText(message)
                                    .setContentIntent(pi)
                                    .setAutoCancel(true)
                                    .setSound(defaultSoundUri)
                                    .setChannelId(channelId);
                    notificationManager.notify(0, builder.build());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_fcm_logo)
                        .setColor(getResources().getColor(R.color.main))
                        .setContentTitle(title)
                        .setContentText(message)
                        .setContentIntent(pi)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setChannelId(channelId);
            }
        }else {
            // 发送本地广播，通知 MainActivity 处理推送通知
            Intent intent2 = new Intent("WRITE_OFF_MESSAGE");
            intent2.putExtra("body", message);
            sendBroadcast(intent2);
        }
    }
/*
    private void uiRefresh(){
        LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
        Intent intent = new Intent(INFO_UPDATE_FILTER);
        intent.putExtra("BADGE_EXTRA", "BADGE_REFRESH");
        broadcaster.sendBroadcast(intent);
    }

 */
}
