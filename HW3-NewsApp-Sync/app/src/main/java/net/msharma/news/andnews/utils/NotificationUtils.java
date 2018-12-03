package net.msharma.news.andnews.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.content.ContextCompat;
import net.msharma.news.andnews.MainActivity;
import net.msharma.news.andnews.R;
import net.msharma.news.andnews.jobservices.NewsIntentService;
import java.util.Objects;

public class NotificationUtils {

    private static final String NEWS_SYNC_NOTIFICATION_CHANNEL_ID = "news_notification_channel";
    private static final int NEWS_SYNC_NOTIFICATION_ID = 1202;
    private static final int NEWS_SYNC_PENDING_INTENT_ID = 1601;
    private static final int ACTION_CANCEL_PENDING_INTENT_ID = 7860;

    static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancelAll();
    }

    static void showNewsNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel( NEWS_SYNC_NOTIFICATION_CHANNEL_ID, context.getString(R.string.news_notification_channel), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NEWS_SYNC_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setSmallIcon(R.drawable.ic_chrome_reader_mode_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_chrome_reader_mode_black_24dp))
                .setContentTitle(context.getString(R.string.news_notification_title))
                .setContentText(context.getString(R.string.news_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.news_notification_body)))
                .setDefaults(Notification.DEFAULT_SOUND)
                .setCategory(Notification.CATEGORY_EVENT)
                .setContentIntent(contentIntent(context))
                .addAction(cancelNewsNotificationAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        Objects.requireNonNull(notificationManager).notify(NEWS_SYNC_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static Action cancelNewsNotificationAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, NewsIntentService.class);
        ignoreReminderIntent.setAction(NewsTaskUtils.ACTION_NEWS_NOTIFICATION_CANCEL);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService( context, ACTION_CANCEL_PENDING_INTENT_ID, ignoreReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new Action(R.drawable.ic_not_interested_black_24dp,"Close", ignoreReminderPendingIntent);
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity( context, NEWS_SYNC_PENDING_INTENT_ID, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}