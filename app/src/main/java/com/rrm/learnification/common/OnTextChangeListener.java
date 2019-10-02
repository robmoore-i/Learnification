package com.rrm.learnification.common;

public interface OnTextChangeListener {
    void onTextChange(IdentifiedTextSource identifiedTextSource);

    void addTextSource(IdentifiedTextSource identifiedTextSource);
}
