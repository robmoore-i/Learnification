package com.rrm.learnification;

interface IdentifiedTextSource {
    String identity();

    String latestText();

    void addTextSink(OnTextChangeListener onTextChangeListener);
}
