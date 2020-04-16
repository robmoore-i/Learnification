package com.rrm.learnification.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;

public class AndroidResponseIntent implements ResponseIntent {
    private final Intent intent;
    private final Bundle remoteInput;

    public AndroidResponseIntent(Intent intent) {
        this.intent = intent;
        this.remoteInput = RemoteInput.getResultsFromIntent(intent);
    }

    @Override
    public String getStringExtra(String name) {
        return intent.getStringExtra(name);
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
