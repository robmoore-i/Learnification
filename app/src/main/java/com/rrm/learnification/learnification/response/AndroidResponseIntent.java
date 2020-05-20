package com.rrm.learnification.learnification.response;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;

class AndroidResponseIntent implements ResponseIntent {
    private final Intent intent;
    private final Bundle remoteInput;

    AndroidResponseIntent(Intent intent) {
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
