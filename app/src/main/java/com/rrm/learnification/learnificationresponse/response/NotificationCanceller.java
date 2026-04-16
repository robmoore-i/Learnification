package com.rrm.learnification.learnificationresponse.response;

import androidx.core.app.NotificationManagerCompat;

class NotificationCanceller {
    private final NotificationManagerCompat notificationManagerCompat;

    NotificationCanceller(NotificationManagerCompat notificationManagerCompat) {
        this.notificationManagerCompat = notificationManagerCompat;
    }

    void cancel(int notificationId) {
        notificationManagerCompat.cancel(notificationId);
    }
}
