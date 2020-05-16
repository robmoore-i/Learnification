package com.rrm.learnification.learningitemseteditor;

interface OnTextChangeListener {
    void onTextChange(IdentifiedTextSource identifiedTextSource);

    void addTextSource(IdentifiedTextSource identifiedTextSource);

    void removeTextSource(String textSourceId);
}
