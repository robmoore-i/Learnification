package com.rrm.learnification.learnificationresponse.response;

import android.content.Intent;

import com.rrm.learnification.learnificationresponse.creation.LearnificationResponseNotificationFactory;
import com.rrm.learnification.learnificationresultstorage.LearnificationPrompt;
import com.rrm.learnification.learnificationresultstorage.LearnificationResult;
import com.rrm.learnification.learnificationresultstorage.LearnificationResultEvaluation;
import com.rrm.learnification.notification.IdentifiedNotification;

import java.time.LocalDateTime;

import static com.rrm.learnification.learnificationresponse.creation.LearnificationResponseNotificationFactory.USER_REPORTS_THEY_ARE_CORRECT_EXTRA;

class LearnificationResultIntent {
    private final Intent intent;

    LearnificationResultIntent(Intent intent) {
        this.intent = intent;
    }

    int notificationId() {
        if (!intent.hasExtra(IdentifiedNotification.ID_EXTRA)) {
            throw new RuntimeException("learnification response unexpectedly did not have the extra '" + IdentifiedNotification.ID_EXTRA + "'");
        }
        return intent.getExtras().getInt(IdentifiedNotification.ID_EXTRA);
    }

    LearnificationResult result(LocalDateTime timeSubmitted) {
        return new LearnificationResult(timeSubmitted, evaluation(), learnificationPrompt());
    }

    private LearnificationResultEvaluation evaluation() {
        if (isCorrectResponse()) {
            return LearnificationResultEvaluation.CORRECT;
        } else {
            return LearnificationResultEvaluation.INCORRECT;
        }
    }

    private boolean isCorrectResponse() {
        if (!intent.hasExtra(USER_REPORTS_THEY_ARE_CORRECT_EXTRA)) {
            throw new RuntimeException("learnification response unexpectedly did not have the extra '" + USER_REPORTS_THEY_ARE_CORRECT_EXTRA + "'");
        }
        return intent.getExtras().getBoolean(USER_REPORTS_THEY_ARE_CORRECT_EXTRA);
    }

    private LearnificationPrompt learnificationPrompt() {
        return new LearnificationPrompt(intent.getStringExtra(LearnificationResponseNotificationFactory.GIVEN_PROMPT_EXTRA),
                intent.getStringExtra(LearnificationResponseNotificationFactory.EXPECTED_USER_RESPONSE_EXTRA));
    }
}
