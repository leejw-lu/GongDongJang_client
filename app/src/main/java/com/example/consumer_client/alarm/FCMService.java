package com.example.consumer_client.alarm;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.consumer_client.MainActivity;
import com.example.consumer_client.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FCMService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "test";
    private static final CharSequence CHANNEL_NAME = "1";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //token을 서버로 전송
        Log.d("fcm",token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
        //Log.i("############# msg body: ", (remoteMessage.getNotification().getBody()));
        //Log.i("############# msg title: ", (remoteMessage.getNotification().getTitle()));
        //Log.i("############# msg data: ", (remoteMessage.getNotification().get()));
//        if (msg.getData().isEmpty()) {
//            showNotificationMessage(msg.getNotification().getTitle(), msg.getNotification().getBody());  // Notification으로 받을 때
//        } else {
//            showDataMessage(msg.getData().get("title"), msg.getData().get("content"));  // Data로 받을 때
//        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        }else {
            builder = new NotificationCompat.Builder(getApplicationContext());
        }

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        //푸시를 클릭했을때 이동//
        Intent intent = new Intent(this, AlarmList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("title",title);
        intent.putExtra("body",body);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.img_gongdongjang_logo)
                .setContentIntent(pendingIntent);
                //.setSmallIcon(R.drawable.ic_launcher_background);

        Notification notification = builder.build();
        notificationManager.notify(1, notification);

    }

}
