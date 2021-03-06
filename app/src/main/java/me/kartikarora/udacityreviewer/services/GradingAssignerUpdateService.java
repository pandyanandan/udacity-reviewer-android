package me.kartikarora.udacityreviewer.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import chipset.potato.Potato;
import me.kartikarora.udacityreviewer.BuildConfig;
import me.kartikarora.udacityreviewer.R;


/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.services
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class GradingAssignerUpdateService extends FirebaseMessagingService {
    private static final String LOG_TAG = GradingAssignerUpdateService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String type = remoteMessage.getData().get("type");
        if (type.equals("assignment")) {
            sendNotification(remoteMessage.getData().get("message"), remoteMessage.getData().get("url"));
        } else if (type.equals("registration")) {
            Potato.potate(getApplicationContext()).Preferences()
                    .putSharedPreference(getString(R.string.pref_udacity_token), remoteMessage.getData().get("token"));
        }
    }

    private void sendNotification(String messageBody, String url) {

        Intent urlIntent = new Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse(url))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingUrlIntent = PendingIntent.getActivity(this, BuildConfig.VERSION_CODE, urlIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_info)
                .setContentTitle("New Review")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .addAction(R.drawable.ic_launch, "View Submission", pendingUrlIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(BuildConfig.VERSION_CODE, notificationBuilder.build());
    }
}
