package net.msharma.news.andnews.utils;

import android.content.Context;

/**
 * Tasks to execute by services.
 */
public class NewsTaskUtils {

    public static final String ACTION_NEWS_NOTIFICATION_CANCEL = "news_notification_cancel";
    public static final String ACTION_NEWS_REFRESH_SYNC = "news_refresh_sync";

    public static void executeTask(Context context, String action) {
        if ( action.equals(ACTION_NEWS_NOTIFICATION_CANCEL) ) {
            cancelNewsNotification(context);
        } else if ( action.equals(ACTION_NEWS_REFRESH_SYNC) ) {
            syncNewsRefresh(context);
        }
    }

    // To cancel/dismiss news notification.
    private static void cancelNewsNotification(Context context) {
        NotificationUtils.clearAllNotifications(context);
    }

    // To sync database with news refresh from api.
    private static void syncNewsRefresh(Context context) {
        NotificationUtils.showNewsNotification(context);
    }

}