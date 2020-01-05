package com.rrm.learnification.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;

public class AndroidIntent {
    private final Intent intent;

    public AndroidIntent(Intent intent) {
        this.intent = intent;
    }

    public Bundle getRemoteInputBundle() {
        return RemoteInput.getResultsFromIntent(intent);
    }

    public String getStringExtra(String name) {
        return intent.getStringExtra(name);
    }
}
