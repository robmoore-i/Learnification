package com.rrm.learnification.learnification.response;

import com.rrm.learnification.intent.ResponseIntent;
import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.LearnificationUpdater;

import org.junit.Test;

import static com.rrm.learnification.notification.AndroidLearnificationResponsePendingIntentBuilder.RESPONSE_TYPE_EXTRA;
import static com.rrm.learnification.notification.AndroidNotificationActionFactory.REPLY_TEXT;
import static com.rrm.learnification.notification.LearnificationResponseType.NEXT;
import static com.rrm.learnification.notification.LearnificationResponseType.SHOW_ME;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AndroidResponseIntentLearnificationResponseTest {
    private final ResponseIntent stubIntent = mock(ResponseIntent.class);

    private final AndroidIntentLearnificationResponse responseIntent = new AndroidIntentLearnificationResponse(stubIntent);

    @Test
    public void whenThereIsRemoteInputItUsesAnAnswerHandler() {
        when(stubIntent.hasRemoteInput()).thenReturn(true);
        LearnificationResponseHandler learnificationResponseHandler = responseIntentHandler();

        assertThat(learnificationResponseHandler, instanceOf(AnswerHandler.class));
    }

    @Test
    public void whenTheResponseTypeIsShowMeItUsesAShowMeHandler() {
        when(stubIntent.getStringExtra(RESPONSE_TYPE_EXTRA)).thenReturn(SHOW_ME.name());
        LearnificationResponseHandler learnificationResponseHandler = responseIntentHandler();

        assertThat(learnificationResponseHandler, instanceOf(ShowMeHandler.class));
    }

    @Test
    public void whenTheResponseTypeIsNextItUsesANextHandler() {
        when(stubIntent.getStringExtra(RESPONSE_TYPE_EXTRA)).thenReturn(NEXT.name());
        LearnificationResponseHandler learnificationResponseHandler = responseIntentHandler();

        assertThat(learnificationResponseHandler, instanceOf(NextHandler.class));
    }

    @Test
    public void whenTheresNoRemoteInputAndTheResponseTypeIsntRecognisedItUsesTheFallthroughHandler() {
        when(stubIntent.getStringExtra(RESPONSE_TYPE_EXTRA)).thenReturn("nonsense");
        LearnificationResponseHandler learnificationResponseHandler = responseIntentHandler();

        assertThat(learnificationResponseHandler, instanceOf(FallthroughHandler.class));
    }

    @Test
    public void actualUserResponseIsNullIfTheresNoRemoteInput() {
        when(stubIntent.hasRemoteInput()).thenReturn(false);

        assertNull(responseIntent.actualUserResponse());
    }

    @Test
    public void actualUserResponseIsNullIfTheRemoteInputTextIsNull() {
        when(stubIntent.getRemoteInputText(REPLY_TEXT)).thenReturn(null);

        assertNull(responseIntent.actualUserResponse());
    }

    @Test
    public void actualUserResponseEqualsTheRemoteInputTextAsAString() {
        when(stubIntent.hasRemoteInput()).thenReturn(true);
        when(stubIntent.getRemoteInputText(REPLY_TEXT)).thenReturn("some text");

        assertThat(responseIntent.actualUserResponse(), equalTo("some text"));
    }

    private LearnificationResponseHandler responseIntentHandler() {
        return responseIntent.handler(mock(AndroidLogger.class), mock(LearnificationScheduler.class), mock(LearnificationResponseContentGenerator.class), mock(LearnificationUpdater.class));
    }
}