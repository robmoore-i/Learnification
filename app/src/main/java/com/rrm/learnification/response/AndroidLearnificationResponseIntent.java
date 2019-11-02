package com.rrm.learnification.response;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.RemoteInput;

import com.rrm.learnification.notification.AndroidNotificationFactory;

import static com.rrm.learnification.notification.AndroidPendingIntentBuilder.EXPECTED_USER_RESPONSE_EXTRA;
import static com.rrm.learnification.notification.AndroidPendingIntentBuilder.GIVEN_PROMPT_EXTRA;
import static com.rrm.learnification.notification.AndroidPendingIntentBuilder.SHOW_ME_FLAG_EXTRA;

class AndroidLearnificationResponseIntent implements LearnificationResponseIntent {
    private final Intent intent;
    private final Bundle remoteInput;

    AndroidLearnificationResponseIntent(Intent intent) {
        this.intent = intent;
        this.remoteInput = RemoteInput.getResultsFromIntent(intent);
    }

    @Override
    public boolean isShowMeResponse() {
        return intent.getBooleanExtra(SHOW_ME_FLAG_EXTRA, true);
    }

    @Override
    public boolean hasRemoteInput() {
        return remoteInput != null;
    }

    @Override
    public String actualUserResponse() {
        if (hasRemoteInput()) {
            //noinspection ConstantConditions
            return remoteInput.getCharSequence(AndroidNotificationFactory.REPLY_TEXT).toString();
        } else {
            return null;
        }
    }

    @Override
    public String expectedUserResponse() {
        return intent.getStringExtra(EXPECTED_USER_RESPONSE_EXTRA);
    }

    @Override
    public String givenPrompt() {
        return intent.getStringExtra(GIVEN_PROMPT_EXTRA);
    }

    @NonNull
    @Override
    public String toString() {
        return "AndroidLearnificationResponseIntent{" +
                "isShowMeResponse=" + isShowMeResponse() + "," +
                "hasRemoteInput=" + hasRemoteInput() + "," +
                "actualUserResponse=" + actualUserResponse() + "," +
                "expectedUserResponse=" + expectedUserResponse() + "," +
                "givenPrompt=" + givenPrompt() + "}";
    }
}
