package com.example.natourapp.services.notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.natourapp.Activities.segnalazione.SegnalazioneItinerarioActivity;
import com.example.natourapp.Activities.segnalazione.risposta_segnalazione.RispostaRicevutaActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationFirebaseService extends FirebaseMessagingService
{
    public static final String NOTIFICATION_CHANNEL_ID = "";
    public static final String NOTIFICATION_CHANNEL_NAME = "";
    public static final String NOTIFICATION_CHANNEL_DESCRIPTION = "";
    public static final int NOTIFICATION_ID = 1;
    @SuppressLint("StaticFieldLeak")
    private static Context ctx;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        String notificationContentTitle = "";
        String notificationContentBody = "";
        String dataNotificationSegnalazioneId = "";
        if (notification != null) {
            notificationContentTitle = notification.getTitle();
            notificationContentBody = notification.getBody();
            dataNotificationSegnalazioneId = remoteMessage.getData().get("segnalazione_id");
        }

        if(notificationContentTitle!=null && dataNotificationSegnalazioneId!=null) {
            if (notificationContentTitle.equals("Segnalazione")) {
                showNotificationSegnalazione(notificationContentTitle, notificationContentBody, Integer.parseInt(dataNotificationSegnalazioneId));
            }
            if (notificationContentTitle.equals("Risposta Segnalazione") ) {
                Log.d("risposta", "try");
                showNotificationRispostaSegnalazione(notificationContentTitle, notificationContentBody, Integer.parseInt(dataNotificationSegnalazioneId));
            }
        }
    }

    private void showNotificationSegnalazione(String title,String body,int segnalazioneId) {

        Intent intent = new Intent(ctx, SegnalazioneItinerarioActivity.class);
        intent.putExtra("segnalazione_id",segnalazioneId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationManager mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                ctx,
                NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setSound(defaultSoundUri);

        notificationBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void showNotificationRispostaSegnalazione(String title,String body,int segnalazioneId){
        Log.d("TAG", "showNotificationRispostaSegnalazione: ");
        Intent intent = new Intent(ctx, RispostaRicevutaActivity.class);
        intent.putExtra("segnalazione_id",segnalazioneId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationManager mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                ctx,
                NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setSound(defaultSoundUri);

        notificationBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
    public static void createChannelAndHandleNotifications(Context context) {
        ctx = context;
        NotificationChannel channel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION);
        channel.setShowBadge(true);

        NotificationManager mNotificationManager = context.getSystemService(NotificationManager.class);
        mNotificationManager.createNotificationChannel(channel);
    }
}
