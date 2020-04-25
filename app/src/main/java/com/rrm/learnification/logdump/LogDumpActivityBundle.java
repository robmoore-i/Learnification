package com.rrm.learnification.logdump;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LogDumpActivityBundle {
    private static final String LOGS_KEY = "logs_key";

    final ArrayList<String> logs;

    public LogDumpActivityBundle(List<String> logs) {
        this.logs = new ArrayList<>(logs);
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
