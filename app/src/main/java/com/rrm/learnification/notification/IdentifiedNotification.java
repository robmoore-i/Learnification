package com.rrm.learnification.notification;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class IdentifiedNotification {
    public static final String ID_EXTRA = "notificationId";

    private final int id;
    private final Notification notification;

    public IdentifiedNotification(int notificationId, NotificationCompat.Builder notificationBuilder, Bundle notificationExtras) {
        notificationExtras.putInt(IdentifiedNotification.ID_EXTRA, notificationId);
        notificationBuilder.addExtras(notificationExtras);
        this.notification = notificationBuilder.build();
        this.id = notificationId;
    }

    public Notification notification() {
        return notification;
    }

    public int id() {
        return id;
    }
}
