package com.rrm.learnification.learnificationresponse.response;

import android.support.v4.app.NotificationManagerCompat;

class NotificationCanceller {
    private final NotificationManagerCompat notificationManagerCompat;

    NotificationCanceller(NotificationManagerCompat notificationManagerCompat) {
        this.notificationManagerCompat = notificationManagerCompat;
    }

    void cancel(int notificationId) {
        notificationManagerCompat.cancel(notificationId);
    }
}
