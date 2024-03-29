package com.rrm.learnification.learnification.response;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;

import com.rrm.learnification.notification.NotificationResponseIntent;

public class LearnificationResponseIntent implements NotificationResponseIntent {
    private final Intent intent;
    private final Bundle remoteInput;

    public LearnificationResponseIntent(Intent intent) {
        this.intent = intent;
        this.remoteInput = RemoteInput.getResultsFromIntent(intent);
    }

    @Override
    public String getStringExtra(String name) {
        return intent.getStringExtra(name);
    }

    @Override
    public int getIntExtra(String name) {
        if (!intent.hasExtra(name)) {
            throw new RuntimeException("LearnificationResponseService input unexpectedly lacked int extra '" + name + "'");
        }
        return intent.getExtras().getInt(name);
    }

    @Override
    public CharSequence getRemoteInputText(String key) {
        return remoteInput.getCharSequence(key);
    }

    @Override
    public boolean hasRemoteInput() {
        return remoteInput != null;
    }
}
