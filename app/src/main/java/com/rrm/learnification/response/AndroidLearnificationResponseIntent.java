package com.rrm.learnification.response;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.RemoteInput;

import com.rrm.learnification.notification.AndroidNotificationFactory;

class AndroidLearnificationResponseIntent implements LearnificationResponseIntent {
    private final Intent intent;
    private final Bundle remoteInput;

    AndroidLearnificationResponseIntent(Intent intent) {
        this.intent = intent;
        this.remoteInput = RemoteInput.getResultsFromIntent(intent);
    }

    @Override
    public boolean isShowMeResponse() {
        return intent.getBooleanExtra(AndroidNotificationFactory.SHOW_ME_FLAG_EXTRA, true);
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
        return intent.getStringExtra(AndroidNotificationFactory.EXPECTED_USER_RESPONSE_EXTRA);
    }

    @Override
    public String givenPrompt() {
        return intent.getStringExtra(AndroidNotificationFactory.GIVEN_PROMPT_EXTRA);
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
