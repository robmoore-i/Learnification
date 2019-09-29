package com.rrm.learnification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.RemoteInput;

class AndroidLearnificationResponseIntent implements LearnificationResponseIntent {
    private final Intent intent;
    private final Bundle remoteInput;

    AndroidLearnificationResponseIntent(Intent intent) {
        this.intent = intent;
        this.remoteInput = RemoteInput.getResultsFromIntent(intent);
    }

    @Override
    public boolean isSkipped() {
        return intent.getBooleanExtra(AndroidNotificationFactory.SKIPPED_FLAG_EXTRA, true);
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

    @NonNull
    @Override
    public String toString() {
        return "AndroidLearnificationResponseIntent{" +
                "isSkipped=" + isSkipped() + "," +
                "hasRemoteInput=" + hasRemoteInput() + "," +
                "actualUserResponse=" + actualUserResponse() + "," +
                "expectedUserResponse=" + expectedUserResponse() + "}";
    }
}
