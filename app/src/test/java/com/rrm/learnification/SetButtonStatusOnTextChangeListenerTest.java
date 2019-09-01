package com.rrm.learnification;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SetButtonStatusOnTextChangeListenerTest {
    private final StubIdentifiedTextSource textA = new StubIdentifiedTextSource("a");
    private final StubIdentifiedTextSource textB = new StubIdentifiedTextSource("b");
    private MockAndroidButton mockButton;
    private SetButtonStatusOnTextChangeListener setButtonStatusOnTextChangeListener;

    @Before
    public void beforeEach() {
        mockButton = new MockAndroidButton();
        setButtonStatusOnTextChangeListener = new SetButtonStatusOnTextChangeListener(mockButton);
    }

    @Test
    public void whenNotifiedTextHasBeenAddedFromAllSubscribersItEnablesTheButton() {
        setButtonStatusOnTextChangeListener.addTextSource(textA);

        textA.sendTextUpdate(setButtonStatusOnTextChangeListener, "a");

        assertTrue(mockButton.active);
    }

    @Test
    public void whenOneSubscriberHasntReportedNonEmptyTextTheButtonIsDisabled() {
        setButtonStatusOnTextChangeListener.addTextSource(textA);
        setButtonStatusOnTextChangeListener.addTextSource(textB);

        textA.sendTextUpdate(setButtonStatusOnTextChangeListener, "a");

        assertFalse(mockButton.active);
    }

    @Test
    public void whenBothOfTwoSubscribersReportNonEmptyTextTheButtonIsEnabled() {
        setButtonStatusOnTextChangeListener.addTextSource(textA);
        setButtonStatusOnTextChangeListener.addTextSource(textB);

        textA.sendTextUpdate(setButtonStatusOnTextChangeListener, "a");
        textB.sendTextUpdate(setButtonStatusOnTextChangeListener, "b");

        assertTrue(mockButton.active);
    }

    @Test
    public void ifAPreviouslyFilledSubscriberBecomesEmptyTheButtonBecomesDisabled() {
        setButtonStatusOnTextChangeListener.addTextSource(textA);
        setButtonStatusOnTextChangeListener.addTextSource(textB);

        textA.sendTextUpdate(setButtonStatusOnTextChangeListener, "a");
        textB.sendTextUpdate(setButtonStatusOnTextChangeListener, "b");
        textA.sendTextUpdate(setButtonStatusOnTextChangeListener, "");

        assertFalse(mockButton.active);
    }

    private static class MockAndroidButton implements ConfigurableButton {
        private Boolean active;

        @Override
        public void addOnClickHandler(OnClickCommand onClickCommand) {
        }

        @Override
        public void enable() {
            active = true;
        }

        @Override
        public void disable() {
            active = false;
        }
    }

    private static class StubIdentifiedTextSource implements IdentifiedTextSource {
        private String identity;
        private String text;

        StubIdentifiedTextSource(String identity) {
            this.identity = identity;
        }

        @Override
        public String identity() {
            return identity;
        }

        @Override
        public String latestText() {
            return text;
        }

        @Override
        public void addTextSink(OnTextChangeListener onTextChangeListener) {
        }

        private void sendTextUpdate(OnTextChangeListener onTextChangeListener, String text) {
            this.text = text;
            onTextChangeListener.onTextChange(this);
        }
    }
}