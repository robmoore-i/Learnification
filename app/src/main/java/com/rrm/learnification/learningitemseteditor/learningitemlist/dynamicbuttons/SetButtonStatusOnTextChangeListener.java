package com.rrm.learnification.learningitemseteditor.learningitemlist.dynamicbuttons;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.button.PostPressEvaluatingButton;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.IdentifiedTextSource;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.UpdatableTextEntryList;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;

import static java.util.Arrays.asList;

public class SetButtonStatusOnTextChangeListener implements OnTextChangeListener {
    private static final String LOG_TAG = "SetButtonStatusOnTextChangeListener";

    private final ConfigurableButton button;
    private final AndroidLogger logger;
    private final HashMap<String, String> texts = new HashMap<>();

    // By default, a text entry list is valid as long as none of the constituent entries are empty.
    private Function<HashMap<String, String>, Boolean> textsValidation = entries -> entries.values().stream().map(String::trim).noneMatch(""::equals);


    public SetButtonStatusOnTextChangeListener(AndroidLogger logger, PostPressEvaluatingButton button) {
        this.logger = logger;
        this.button = button;
        // Reevaluate button status onclick
        button.addLastExecutedOnClickHandler(this::setButtonStatusBasedOnTexts);
    }

    public static Function<HashMap<String, String>, Boolean> textsValidationForDisplayedLearningItems(AndroidLogger logger,
                                                                                                      UpdatableTextEntryList textEntryList) {
        return new Function<HashMap<String, String>, Boolean>() {
            private final String LOG_TAG = SetButtonStatusOnTextChangeListener.LOG_TAG + ".unpersistedValidLearningItemSingleTextEntries";

            @Override
            public Boolean apply(HashMap<String, String> texts) {
                Collection<String> textEntries = texts.values();
                return areAllTextEntriesValidLearningItems(textEntries) && !allCandidateTextEntriesAreAlreadyPresent(textEntries);
            }

            private boolean allCandidateTextEntriesAreAlreadyPresent(Collection<String> textEntries) {
                boolean result = textEntryList.containsTextEntries(textEntries);
                logger.v(LOG_TAG, "all candidate text entries are " + (result ? "" : "not") + " already stored");
                return result;
            }

            private boolean areAllTextEntriesValidLearningItems(Collection<String> textEntries) {
                logger.v(LOG_TAG, "checking that all text entries are valid learning items. they are '" + collectionToString(textEntries) + "'");
                return textEntries.stream().map(String::trim).allMatch(entry -> {
                    String[] split = entry.split("-");
                    if (split.length != 2) return false;
                    return !"".equals(split[0].trim()) && !"".equals(split[1].trim()) // Not empty
                            && split[0].endsWith(" ") && split[1].startsWith(" ");    // Not immediately adjacent to the central hyphen
                });
            }

            private String collectionToString(Collection<String> textEntries) {
                return asList(textEntries.toArray(new String[0])).toString();
            }
        };
    }

    /**
     * @param textsValidation This is a function which accepts a mapping from text source id to
     *                        text. The text is the text within one of the listened-to text sources.
     *                        So the values of the map are the text entries you'll likely want to
     *                        check for validity.
     */
    public void useTextValidation(Function<HashMap<String, String>, Boolean> textsValidation) {
        this.textsValidation = textsValidation;
    }

    @Override
    public void onTextChange(IdentifiedTextSource identifiedTextSource) {
        texts.put(identifiedTextSource.identity(), identifiedTextSource.latestText());
        logger.v(LOG_TAG, "text sources read '" + texts.toString() + "'");
        setButtonStatusBasedOnTexts();
    }

    @Override
    public void addTextSource(IdentifiedTextSource identifiedTextSource) {
        logger.i(LOG_TAG, "adding text source '" + identifiedTextSource.identity() + "' with current text '" + identifiedTextSource.latestText() + "'");
        identifiedTextSource.addTextSink(this);
        texts.put(identifiedTextSource.identity(), identifiedTextSource.latestText());
    }

    @Override
    public void removeTextSource(String textSourceId) {
        logger.i(LOG_TAG, "removing text source '" + textSourceId + "'");
        texts.remove(textSourceId);
        setButtonStatusBasedOnTexts();
    }

    private void setButtonStatus(boolean shouldBeEnabled) {
        logger.v(LOG_TAG, "setting button status to " + shouldBeEnabled);
        if (shouldBeEnabled) {
            button.enable();
        } else {
            button.disable();
        }
    }

    private void setButtonStatusBasedOnTexts() {
        boolean buttonShouldBeEnabled = true;
        boolean textsAreValid = textsValidation.apply(texts);
        logger.v(LOG_TAG, "that the button should be enabled is deemed " + textsAreValid + " for texts '" + texts.toString() + "'");
        if (!textsAreValid) {
            buttonShouldBeEnabled = false;
        }
        setButtonStatus(buttonShouldBeEnabled);
    }
}
