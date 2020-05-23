package com.rrm.learnification.learnificationresponse.response;

import android.content.Intent;

import com.rrm.learnification.notification.IdentifiedNotification;

import static com.rrm.learnification.learnificationresponse.creation.LearnificationResponseNotificationFactory.USER_REPORTS_THEY_ARE_CORRECT_EXTRA;

class LearnificationResultIntent {
    private final Intent intent;

    LearnificationResultIntent(Intent intent) {
        this.intent = intent;
    }

    boolean correctResponse() {
        if (!intent.hasExtra(USER_REPORTS_THEY_ARE_CORRECT_EXTRA)) {
            throw new RuntimeException("learnification response unexpectedly did not have the extra '" + USER_REPORTS_THEY_ARE_CORRECT_EXTRA + "'");
        }
        return intent.getExtras().getBoolean(USER_REPORTS_THEY_ARE_CORRECT_EXTRA);
    }

    public int notificationId() {
        if (!intent.hasExtra(IdentifiedNotification.ID_EXTRA)) {
            throw new RuntimeException("learnification response unexpectedly did not have the extra '" + IdentifiedNotification.ID_EXTRA + "'");
        }
        return intent.getExtras().getInt(IdentifiedNotification.ID_EXTRA);
    }
}
