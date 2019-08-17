package com.rrm.learnification;

import java.util.HashMap;

class SetButtonStatusOnTextChangeListener implements OnTextChangeListener {
    private final AndroidButton androidButton;

    private final HashMap<String, String> texts;

    SetButtonStatusOnTextChangeListener(AndroidButton androidButton) {
        this.androidButton = androidButton;
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
            androidButton.enable();
        } else {
            androidButton.disable();
        }
    }
}
