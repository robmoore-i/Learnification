package com.rrm.learnification.logdump;

import android.os.Bundle;

import java.util.ArrayList;

public class LogDumpActivityBundle {
    private static final String LOGS_KEY = "logs_key";

    final ArrayList<String> logs;

    public LogDumpActivityBundle(ArrayList<String> logs) {
        this.logs = logs;
    }

    static LogDumpActivityBundle fromBundle(Bundle startupIntentExtras) {
        if (startupIntentExtras != null) {
            return new LogDumpActivityBundle(startupIntentExtras.getStringArrayList(LOGS_KEY));
        } else {
            return new LogDumpActivityBundle(new ArrayList<>());
        }
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(LOGS_KEY, logs);
        return bundle;
    }
}
