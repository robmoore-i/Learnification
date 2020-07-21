package com.rrm.learnification.learnification.response;

import com.rrm.learnification.learnification.creation.LearnificationFactory;
import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learnification.response.handler.AnswerHandler;
import com.rrm.learnification.learnification.response.handler.FallthroughHandler;
import com.rrm.learnification.learnification.response.handler.LearnificationResponseHandler;
import com.rrm.learnification.learnification.response.handler.NextHandler;
import com.rrm.learnification.learnification.response.handler.ShowMeHandler;
import com.rrm.learnification.learnificationresponse.publication.LearnificationUpdater;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.NotificationResponseIntent;

import org.junit.Test;

import static com.rrm.learnification.learnification.creation.LearnificationResponseType.NEXT;
import static com.rrm.learnification.learnification.creation.LearnificationResponseType.SHOW_ME;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LearnificationResponseTest {
    private final NotificationResponseIntent stubIntent = mock(NotificationResponseIntent.class);

    private final LearnificationResponse responseIntent = new LearnificationResponse(stubIntent);

    @Test
    public void whenThereIsRemoteInputItUsesAnAnswerHandler() {
        when(stubIntent.hasRemoteInput()).thenReturn(true);
        LearnificationResponseHandler learnificationResponseHandler = responseIntentHandler();

        assertThat(learnificationResponseHandler, instanceOf(AnswerHandler.class));
    }

    @Test
    public void whenTheResponseTypeIsShowMeItUsesAShowMeHandler() {
        when(stubIntent.getStringExtra(LearnificationFactory.RESPONSE_TYPE_EXTRA)).thenReturn(SHOW_ME.name());
        LearnificationResponseHandler learnificationResponseHandler = responseIntentHandler();

        assertThat(learnificationResponseHandler, instanceOf(ShowMeHandler.class));
    }

    @Test
    public void whenTheResponseTypeIsNextItUsesANextHandler() {
        when(stubIntent.getStringExtra(LearnificationFactory.RESPONSE_TYPE_EXTRA)).thenReturn(NEXT.name());
        LearnificationResponseHandler learnificationResponseHandler = responseIntentHandler();

        assertThat(learnificationResponseHandler, instanceOf(NextHandler.class));
    }

    @Test
    public void whenTheresNoRemoteInputAndTheResponseTypeIsntRecognisedItUsesTheFallthroughHandler() {
        when(stubIntent.getStringExtra(LearnificationFactory.RESPONSE_TYPE_EXTRA)).thenReturn("nonsense");
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
        when(stubIntent.getRemoteInputText(LearnificationFactory.REPLY_TEXT)).thenReturn(null);

        assertNull(responseIntent.actualUserResponse());
    }

    @Test
    public void actualUserResponseEqualsTheRemoteInputTextAsAString() {
        when(stubIntent.hasRemoteInput()).thenReturn(true);
        when(stubIntent.getRemoteInputText(LearnificationFactory.REPLY_TEXT)).thenReturn("some text");

        assertThat(responseIntent.actualUserResponse(), equalTo("some text"));
    }

    private LearnificationResponseHandler responseIntentHandler() {
        return responseIntent.handler(mock(AndroidLogger.class), mock(LearnificationResponseContentGenerator.class), mock(LearnificationScheduler.class),
                mock(LearnificationUpdater.class));
    }
}