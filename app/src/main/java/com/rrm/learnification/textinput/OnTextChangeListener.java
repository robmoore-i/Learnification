package com.rrm.learnification.textinput;

public interface OnTextChangeListener {
    void onTextChange(IdentifiedTextSource identifiedTextSource);

    void addTextSource(IdentifiedTextSource identifiedTextSource);

    void removeTextSource(String textSourceId);
}
