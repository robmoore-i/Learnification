package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.logger.AndroidLogger;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

import static com.rrm.learnification.learningitemseteditor.SetButtonStatusOnTextChangeListener.textsValidationForDisplayedLearningItems;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SetButtonStatusOnTextChangeListenerTest {
    private final AndroidLogger dummyLogger = mock(AndroidLogger.class);
    private final StubIdentifiedTextSource textA = new StubIdentifiedTextSource("a");
    private final StubIdentifiedTextSource textB = new StubIdentifiedTextSource("b");

    private MockAndroidButton mockButton;
    private SetButtonStatusOnTextChangeListener setButtonStatusOnTextChangeListener;

    @Before
    public void beforeEach() {
        mockButton = new MockAndroidButton();
        setButtonStatusOnTextChangeListener = new SetButtonStatusOnTextChangeListener(dummyLogger, mockButton);
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

    private Function<HashMap<String, String>, Boolean> updatedLearningItemValidatorWhereNewLearningItemIsntInList() {
        TextEntryList stubTextEntryList = mock(TextEntryList.class);
        when(stubTextEntryList.containsTextEntries(any())).thenReturn(false);
        return textsValidationForDisplayedLearningItems(dummyLogger, stubTextEntryList);
    }

    @Test
    public void emptyRightValueIsInvalidSingleTextEntryLearningItem() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "hello - ");
        assertFalse(updatedLearningItemValidatorWhereNewLearningItemIsntInList().apply(invalid));
    }

    @Test
    public void emptyLeftValueIsInvalidSingleTextEntryLearningItem() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "- hello");
        assertFalse(updatedLearningItemValidatorWhereNewLearningItemIsntInList().apply(invalid));
    }

    @Test
    public void nonsenseIsInvalidSingleTextEntryLearningItem() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "asdf");
        assertFalse(updatedLearningItemValidatorWhereNewLearningItemIsntInList().apply(invalid));
    }

    @Test
    public void twoNonsensesAreInvalidSingleTextEntryLearningItems() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "blah");
        invalid.put("id2", "something else");
        assertFalse(updatedLearningItemValidatorWhereNewLearningItemIsntInList().apply(invalid));
    }

    @Test
    public void aNonsenseAndAGoodunAreInvalidSingleTextEntryLearningItems() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "a - b");
        invalid.put("id2", "something else");
        assertFalse(updatedLearningItemValidatorWhereNewLearningItemIsntInList().apply(invalid));
    }

    @Test
    public void aGoodunIsValidSingleTextEntryLearningItem() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "a - b");
        assertTrue(updatedLearningItemValidatorWhereNewLearningItemIsntInList().apply(invalid));
    }

    @Test
    public void learningItemTextValidationRespectsTheIsolationOfTheHyphen() {
        Function<HashMap<String, String>, Boolean> validate = textsValidationForDisplayedLearningItems(dummyLogger, textEntries -> false);
        assertFalse(validate.apply(new HashMap<>(Collections.singletonMap("text-id", "abra -kadabra"))));
        assertFalse(validate.apply(new HashMap<>(Collections.singletonMap("text-id", "abra- kadabra"))));
        assertTrue(validate.apply(new HashMap<>(Collections.singletonMap("text-id", "abra - kadabra"))));
    }

    @Test
    public void twoGoodunsAreValidSingleTextEntryLearningItems() {
        HashMap<String, String> invalid = new HashMap<>();
        invalid.put("id", "a - b");
        invalid.put("id2", "hello - mate");
        assertTrue(updatedLearningItemValidatorWhereNewLearningItemIsntInList().apply(invalid));
    }

    private static class MockAndroidButton implements ConfigurableButton {
        private Boolean active;

        @Override
        public void addOnClickHandler(OnClickCommand onClickCommand) {
        }

        @Override
        public void addLastExecutedOnClickHandler(OnClickCommand onClickCommand) {
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
        private final String identity;

        private String text = "";

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