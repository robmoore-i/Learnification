package com.rrm.learnification.common;

import java.util.HashMap;

public class SetButtonStatusOnTextChangeListener implements OnTextChangeListener {
    private final ConfigurableButton configurableButton;

    private final HashMap<String, String> texts;

    public SetButtonStatusOnTextChangeListener(ConfigurableButton configurableButton) {
        this.configurableButton = configurableButton;
        this.texts = new HashMap<>();
    }

    @Override
    public void onTextChange(IdentifiedTextSource identifiedTextSource) {
        boolean buttonShouldBeEnabled = true;
        texts.put(identifiedTextSource.identity(), identifiedTextSource.latestText());
        if (texts.values().stream().anyMatch(""::equals)) {
            buttonShouldBeEnabled = false;
        }
        setButtonStatus(buttonShouldBeEnabled);
    }

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
}
