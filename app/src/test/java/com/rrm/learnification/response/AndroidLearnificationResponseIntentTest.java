package com.rrm.learnification.response;

import android.os.Bundle;

import com.rrm.learnification.intent.AndroidIntent;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationScheduler;

import org.junit.Before;
import org.junit.Test;

import static com.rrm.learnification.notification.AndroidNotificationActionFactory.REPLY_TEXT;
import static com.rrm.learnification.notification.AndroidPendingIntentBuilder.RESPONSE_TYPE_EXTRA;
import static com.rrm.learnification.notification.LearnificationResponseType.NEXT;
import static com.rrm.learnification.notification.LearnificationResponseType.SHOW_ME;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class AndroidLearnificationResponseIntentTest {
    private final AndroidIntent stubIntent = mock(AndroidIntent.class);
    private final Bundle stubBundle = mock(Bundle.class);

    private AndroidLearnificationResponseIntent responseIntent;

    @Before
    public void beforeEach() {
        reset(stubBundle, stubIntent);
    }

    @Test
    public void whenThereIsRemoteInputItUsesAnAnswerHandler() {
        when(stubIntent.getRemoteInputBundle()).thenReturn(stubBundle);
        responseIntent = new AndroidLearnificationResponseIntent(stubIntent);
        LearnificationResponseHandler learnificationResponseHandler = responseIntentHandler();

        assertThat(learnificationResponseHandler, instanceOf(AnswerHandler.class));
    }

    @Test
    public void whenTheResponseTypeIsShowMeItUsesAShowMeHandler() {
        when(stubIntent.getStringExtra(RESPONSE_TYPE_EXTRA)).thenReturn(SHOW_ME.name());
        responseIntent = new AndroidLearnificationResponseIntent(stubIntent);
        LearnificationResponseHandler learnificationResponseHandler = responseIntentHandler();

        assertThat(learnificationResponseHandler, instanceOf(ShowMeHandler.class));
    }

    @Test
    public void whenTheResponseTypeIsNextItUsesANextHandler() {
        when(stubIntent.getStringExtra(RESPONSE_TYPE_EXTRA)).thenReturn(NEXT.name());
        responseIntent = new AndroidLearnificationResponseIntent(stubIntent);
        LearnificationResponseHandler learnificationResponseHandler = responseIntentHandler();

        assertThat(learnificationResponseHandler, instanceOf(NextHandler.class));
    }

    @Test
    public void whenTheresNoRemoteInputAndTheResponseTypeIsntRecognisedItUsesTheFallthroughHandler() {
        when(stubIntent.getStringExtra(RESPONSE_TYPE_EXTRA)).thenReturn("nonsense");
        responseIntent = new AndroidLearnificationResponseIntent(stubIntent);
        LearnificationResponseHandler learnificationResponseHandler = responseIntentHandler();

        assertThat(learnificationResponseHandler, instanceOf(FallthroughHandler.class));
    }

    @Test
    public void actualUserResponseIsNullIfTheresNoRemoteInput() {
        when(stubIntent.getRemoteInputBundle()).thenReturn(null);
        responseIntent = new AndroidLearnificationResponseIntent(stubIntent);

        assertNull(responseIntent.actualUserResponse());
    }

    @Test
    public void actualUserResponseIsNullIfTheRemoteInputTextIsNull() {
        when(stubBundle.getCharSequence(REPLY_TEXT)).thenReturn(null);
        when(stubIntent.getRemoteInputBundle()).thenReturn(stubBundle);
        responseIntent = new AndroidLearnificationResponseIntent(stubIntent);

        assertNull(responseIntent.actualUserResponse());
    }

    @Test
    public void actualUserResponseEqualsTheRemoteInputTextAsAString() {
        when(stubBundle.getCharSequence(REPLY_TEXT)).thenReturn("some text");
        when(stubIntent.getRemoteInputBundle()).thenReturn(stubBundle);
        responseIntent = new AndroidLearnificationResponseIntent(stubIntent);

        assertThat(responseIntent.actualUserResponse(), equalTo("some text"));
    }

    private LearnificationResponseHandler responseIntentHandler() {
        return responseIntent.handler(mock(AndroidLogger.class), mock(LearnificationScheduler.class), mock(LearnificationResponseContentGenerator.class), mock(ResponseNotificationCorrespondent.class));
    }
}