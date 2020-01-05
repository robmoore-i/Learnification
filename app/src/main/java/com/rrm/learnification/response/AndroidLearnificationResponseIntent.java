package com.rrm.learnification.response;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.RemoteInput;

import com.rrm.learnification.notification.AndroidNotificationFactory;
import com.rrm.learnification.notification.AndroidPendingIntentBuilder;

import static com.rrm.learnification.notification.AndroidPendingIntentBuilder.EXPECTED_USER_RESPONSE_EXTRA;
import static com.rrm.learnification.notification.AndroidPendingIntentBuilder.GIVEN_PROMPT_EXTRA;
import static com.rrm.learnification.notification.LearnificationResponseType.SHOW_ME;

class AndroidLearnificationResponseIntent implements LearnificationResponseIntent {
    private final Intent intent;
    private final Bundle remoteInput;

    AndroidLearnificationResponseIntent(Intent intent) {
        this.intent = intent;
        this.remoteInput = RemoteInput.getResultsFromIntent(intent);
    }

    @Override
    public boolean isShowMeResponse() {
        return SHOW_ME.name().equals(intent.getStringExtra(AndroidPendingIntentBuilder.RESPONSE_TYPE_EXTRA));
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
