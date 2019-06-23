package com.rrm.learnification;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class AndroidPackageContext {
    private final Context packageContext;

    public AndroidPackageContext(Context packageContext) {
        this.packageContext = packageContext;
    }

    String getRemoteInputReplyLabel() {
        return packageContext.getResources().getString(R.string.reply_label);
    }

    String getReplyActionLabel() {
        return packageContext.getString(R.string.reply_label);
    }

    Bitmap getNotificationLargeIcon() {
        return BitmapFactory.decodeResource(packageContext.getApplicationContext().getResources(), R.mipmap.ic_launcher);
    }

    PendingIntent notificationContentIntent() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(packageContext, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(packageContext, 0, intent, 0);
    }

    String getNotificationChannelName() {
        return packageContext.getString(R.string.channel_name);
    }

    String getNotificationChannelDescription() {
        return packageContext.getString(R.string.channel_description);
    }

    NotificationManager getNotificationManager() {
        return packageContext.getSystemService(NotificationManager.class);
    }
}
