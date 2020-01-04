package com.rrm.learnification.textinput;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.ItemRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class SetButtonStatusOnTextChangeListener implements OnTextChangeListener {
    public static final Function<HashMap<String, String>, Boolean> noneEmpty = texts -> texts.values().stream().map(String::trim).noneMatch(""::equals);
    private static final String LOG_TAG = "SetButtonStatusOnTextChangeListener";
    private final ConfigurableButton configurableButton;
    private final AndroidLogger logger;
    private final Function<HashMap<String, String>, Boolean> textsValidation;
    private final HashMap<String, String> texts = new HashMap<>();

    /**
     * @param logger                        Logger
     * @param configurableButton            ConfigurableButton
     * @param textsValidation               This is a function which accepts a mapping from text source id to
     *                                      text. The text is the text within one of the listened-to text sources.
     *                                      So the values of the map are the text entries you'll likely want to
     * @param reevaluateButtonStatusOnClick If set to true, the button is given an onClickHandler which causes the
     *                                      status of the button itself to be reevaluated. This onClickHandler is
     *                                      always executed last in the list of onClickHandlers.
     */
    public SetButtonStatusOnTextChangeListener(AndroidLogger logger, ConfigurableButton configurableButton, Function<HashMap<String, String>, Boolean> textsValidation, boolean reevaluateButtonStatusOnClick) {
        this.logger = logger;
        this.configurableButton = configurableButton;
        this.textsValidation = textsValidation;
        if (reevaluateButtonStatusOnClick)
            configurableButton.addLastExecutedOnClickHandler(this::setButtonStatusBasedOnTexts);
    }

    public static Function<HashMap<String, String>, Boolean> unpersistedValidLearningItemSingleTextEntries(AndroidLogger logger, ItemRepository<LearningItem> learningItemRepository) {
        return new Function<HashMap<String, String>, Boolean>() {
            private final String LOG_TAG = SetButtonStatusOnTextChangeListener.LOG_TAG + ".unpersistedValidLearningItemSingleTextEntries";

            @Override
            public Boolean apply(HashMap<String, String> texts) {
                Collection<String> textEntries = texts.values();
                return areAllTextEntriesValidLearningItems(textEntries) && !allCandidateTextEntriesAreAlreadyStored(textEntries);
            }

            private boolean allCandidateTextEntriesAreAlreadyStored(Collection<String> textEntries) {
                List<String> stored = learningItemRepository.items().stream().map(LearningItem::asSingleString).collect(Collectors.toList());
                logger.v(LOG_TAG, "stored text entries are '" + stored.toString() + "'");
                logger.v(LOG_TAG, "written text entries are '" + collectionToString(textEntries) + "'");
                boolean result = stored.containsAll(textEntries);
                logger.v(LOG_TAG, "all candidate text entries are " + (result ? "" : "not") + " already stored");
                return result;
            }

            private boolean areAllTextEntriesValidLearningItems(Collection<String> textEntries) {
                logger.v(LOG_TAG, "checking that all text entries are valid learning items. they are '" + collectionToString(textEntries) + "'");
                return textEntries.stream().map(String::trim).allMatch(entry -> {
                    String[] split = entry.split("-");
                    if (split.length != 2) return false;
                    return !"".equals(split[0].trim()) && !"".equals(split[1].trim());
                });
            }

            private String collectionToString(Collection<String> textEntries) {
                return asList(textEntries.toArray(new String[0])).toString();
            }
        };
    }

    @Override
    public void addTextSource(IdentifiedTextSource identifiedTextSource) {
        logger.v(LOG_TAG, "adding text source '" + identifiedTextSource.identity() + "' with current text '" + identifiedTextSource.latestText() + "'");
        identifiedTextSource.addTextSink(this);
        texts.put(identifiedTextSource.identity(), "");
    }

    @Override
    public void onTextChange(IdentifiedTextSource identifiedTextSource) {
        texts.put(identifiedTextSource.identity(), identifiedTextSource.latestText());
        logger.v(LOG_TAG, "text sources read '" + texts.toString() + "'");
        setButtonStatusBasedOnTexts();
    }

    @Override
    public void removeTextSource(String textSourceId) {
        texts.remove(textSourceId);
        setButtonStatus(configurableButton.enabledInitially());
    }

    private void setButtonStatusBasedOnTexts() {
        boolean buttonShouldBeEnabled = true;
        if (!textsValidation.apply(texts)) {
            buttonShouldBeEnabled = false;
        }
        logger.v(LOG_TAG, "setting button status to '" + buttonShouldBeEnabled + "', based on texts");
        setButtonStatus(buttonShouldBeEnabled);
    }

    private void setButtonStatus(boolean shouldBeEnabled) {
        logger.v(LOG_TAG, "setting button status to " + shouldBeEnabled);
        if (shouldBeEnabled) {
            configurableButton.enable();
        } else {
            configurableButton.disable();
        }
    }
}
