package com.rrm.learnification.dailyreport.creation;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.rrm.learnification.R;
import com.rrm.learnification.learnification.creation.LearnificationNotificationChannel;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.IdentifiedNotification;
import com.rrm.learnification.notification.NotificationIdGenerator;
import com.rrm.learnification.notification.NotificationType;

public class DailyReportFactory {
    private static final String LOG_TAG = "DailyReportFactory";
    private final AndroidLogger logger;
    private final Context packageContext;
    private final NotificationIdGenerator notificationIdGenerator;
    private final DailyReportTextGenerator dailyReportTextGenerator;

    public DailyReportFactory(AndroidLogger logger, Context packageContext,
                              NotificationIdGenerator notificationIdGenerator, DailyReportTextGenerator dailyReportTextGenerator) {
        this.logger = logger;
        this.packageContext = packageContext;
        this.notificationIdGenerator = notificationIdGenerator;
        this.dailyReportTextGenerator = dailyReportTextGenerator;
    }

    public IdentifiedNotification dailyReport() {
        int notificationId = notificationIdGenerator.next();
        logger.i(LOG_TAG, "creating daily report with notification id " + notificationId);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(packageContext, LearnificationNotificationChannel.CHANNEL_ID)
                .setContentTitle("Daily Report")
                .setContentText(dailyReportTextGenerator.getText())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notificationContentIntent()) // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_a_notification)
                .setLargeIcon(BitmapFactory.decodeResource(packageContext.getResources(), R.mipmap.ic_launcher_a_notification));
        return new IdentifiedNotification(notificationId, notificationBuilder, notificationExtras());
    }

    private PendingIntent notificationContentIntent() {
        Intent intent = new Intent(packageContext, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(packageContext, 0, intent, 0);
    }

    private Bundle notificationExtras() {
        Bundle bundle = new Bundle();
        bundle.putString(NotificationType.NOTIFICATION_TYPE_EXTRA_NAME, NotificationType.DAILY_REPORT);
        return bundle;
    }
}
