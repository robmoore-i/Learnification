package com.rrm.learnification.textinput;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.button.OnClickCommand;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static com.rrm.learnification.textinput.SetButtonStatusOnTextChangeListener.noneEmpty;
import static com.rrm.learnification.textinput.SetButtonStatusOnTextChangeListener.validLearningItemSingleTextEntries;
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
        setButtonStatusOnTextChangeListener = new SetButtonStatusOnTextChangeListener(mockButton, noneEmpty);
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

    @Test
    public void emptyRightValueIsInvalidSingleTextEntryLearningItem() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "hello - ");
        assertFalse(validLearningItemSingleTextEntries.apply(invalid));
    }

    @Test
    public void emptyLeftValueIsInvalidSingleTextEntryLearningItem() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "- hello");
        assertFalse(validLearningItemSingleTextEntries.apply(invalid));
    }

    @Test
    public void nonsenseIsInvalidSingleTextEntryLearningItem() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "asdf");
        assertFalse(validLearningItemSingleTextEntries.apply(invalid));
    }

    @Test
    public void twoNonsensesAreInvalidSingleTextEntryLearningItems() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "blah");
        invalid.put("id2", "something else");
        assertFalse(validLearningItemSingleTextEntries.apply(invalid));
    }

    @Test
    public void aNonsenseAndAGoodunAreInvalidSingleTextEntryLearningItems() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "a - b");
        invalid.put("id2", "something else");
        assertFalse(validLearningItemSingleTextEntries.apply(invalid));
    }

    @Test
    public void aGoodunIsValidSingleTextEntryLearningItem() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "a - b");
        assertTrue(validLearningItemSingleTextEntries.apply(invalid));
    }

    @Test
    public void twoGoodunsAreValidSingleTextEntryLearningItems() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "a - b");
        invalid.put("id2", "hello - mate");
        assertTrue(validLearningItemSingleTextEntries.apply(invalid));
    }

    private static class MockAndroidButton implements ConfigurableButton {
        private Boolean active;

        @Override
        public void addOnClickHandler(OnClickCommand onClickCommand) {
        }

        @Override
        public void clearOnClickHandlers() {
        }

        @Override
        public void enable() {
            active = true;
        }

        @Override
        public void disable() {
            active = false;
        }

        @Override
        public void click() {
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