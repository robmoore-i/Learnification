package com.rrm.learnification.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.rrm.learnification.learnification.response.LearnificationResponseService;

public class AndroidLearnificationResponsePendingIntentBuilder {
    public static final String EXPECTED_USER_RESPONSE_EXTRA = "expectedUserResponse";
    public static final String GIVEN_PROMPT_EXTRA = "givenPrompt";
    public static final String RESPONSE_TYPE_EXTRA = "responseType";

    private final Context packageContext;
    private final Intent intent;
    private final PendingIntentIdGenerator pendingIntentIdGenerator;

    AndroidLearnificationResponsePendingIntentBuilder(Context packageContext, String learningItemPrompt, String expectedUserResponse, LearnificationResponseType learnificationResponseType, PendingIntentIdGenerator pendingIntentIdGenerator) {
        this.packageContext = packageContext;
        this.pendingIntentIdGenerator = pendingIntentIdGenerator;
        intent = new Intent(packageContext, LearnificationResponseService.class);
        intent.putExtra(RESPONSE_TYPE_EXTRA, learnificationResponseType.name());
        intent.putExtra(EXPECTED_USER_RESPONSE_EXTRA, expectedUserResponse);
        intent.putExtra(GIVEN_PROMPT_EXTRA, learningItemPrompt);
    }

    PendingIntent build() {
        return PendingIntent.getService(
                packageContext,
                pendingIntentIdGenerator.next(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }
}
