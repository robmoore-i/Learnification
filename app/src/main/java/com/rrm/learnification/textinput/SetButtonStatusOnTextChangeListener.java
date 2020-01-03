package com.rrm.learnification.textinput;

import com.rrm.learnification.button.ConfigurableButton;

import java.util.HashMap;
import java.util.function.Function;

public class SetButtonStatusOnTextChangeListener implements OnTextChangeListener {
    private final ConfigurableButton configurableButton;

    private final HashMap<String, String> texts;
    public static final Function<HashMap<String, String>, Boolean> noneEmpty = texts -> texts.values().stream().noneMatch(""::equals);
    public static final Function<HashMap<String, String>, Boolean> validLearningItemSingleTextEntries = texts -> texts.values().stream().allMatch(entry -> {
        String[] split = entry.split("-");
        if (split.length != 2) return false;
        return !"".equals(split[0].trim()) && !"".equals(split[1].trim());
    });
    private Function<HashMap<String, String>, Boolean> textsValidation;

    @Override
    public void addTextSource(IdentifiedTextSource identifiedTextSource) {
        identifiedTextSource.addTextSink(this);
        texts.put(identifiedTextSource.identity(), "");
    }

    private void setButtonStatus(boolean shouldBeEnabled) {
        if (shouldBeEnabled) {
            configurableButton.enable();
        } else {
            configurableButton.disable();
        }
    }

    public SetButtonStatusOnTextChangeListener(ConfigurableButton configurableButton, Function<HashMap<String, String>, Boolean> textsValidation) {
        this.configurableButton = configurableButton;
        this.texts = new HashMap<>();
        this.textsValidation = textsValidation;
    }

    @Override
    public void onTextChange(IdentifiedTextSource identifiedTextSource) {
        boolean buttonShouldBeEnabled = true;
        texts.put(identifiedTextSource.identity(), identifiedTextSource.latestText());
        if (!textsValidation.apply(texts)) {
            buttonShouldBeEnabled = false;
        }
        setButtonStatus(buttonShouldBeEnabled);
    }
}
