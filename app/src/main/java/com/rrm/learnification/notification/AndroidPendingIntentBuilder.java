package com.rrm.learnification.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.rrm.learnification.response.LearnificationResponseService;

public class AndroidPendingIntentBuilder {
    public static final String EXPECTED_USER_RESPONSE_EXTRA = "expectedUserResponse";
    public static final String SHOW_ME_FLAG_EXTRA = "showMeFlag";
    public static final String GIVEN_PROMPT_EXTRA = "givenPrompt";

    private final Context packageContext;
    private final Intent intent;

    private AndroidPendingIntentBuilder(Context packageContext, boolean isShowMeResponse) {
        this.packageContext = packageContext;
        intent = new Intent(packageContext, LearnificationResponseService.class);
        intent.putExtra(SHOW_ME_FLAG_EXTRA, isShowMeResponse);
    }

    static AndroidPendingIntentBuilder showMeIntent(Context packageContext) {
        return new AndroidPendingIntentBuilder(packageContext, true);
    }

    static AndroidPendingIntentBuilder learnificationIntent(Context packageContext) {
        return new AndroidPendingIntentBuilder(packageContext, false);
    }

    AndroidPendingIntentBuilder withExpectedUserResponse(String expectedUserResponse) {
        intent.putExtra(EXPECTED_USER_RESPONSE_EXTRA, expectedUserResponse);
        return this;
    }

    AndroidPendingIntentBuilder withLearningItemPrompt(String learningItemPrompt) {
        intent.putExtra(GIVEN_PROMPT_EXTRA, learningItemPrompt);
        return this;
    }

    PendingIntent build() {
        return PendingIntent.getService(
                packageContext,
                PendingIntentRequestCodeGenerator.getInstance().nextRequestCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }
}
