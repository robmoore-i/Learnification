package com.rrm.learnification;

interface OnTextChangeListener {
    void onTextChange(IdentifiedTextSource identifiedTextSource);

    void addTextSource(IdentifiedTextSource identifiedTextSource);
}
