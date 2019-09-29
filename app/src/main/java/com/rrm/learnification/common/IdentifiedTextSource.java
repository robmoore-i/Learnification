package com.rrm.learnification.common;

interface IdentifiedTextSource {
    String identity();

    String latestText();

    void addTextSink(OnTextChangeListener onTextChangeListener);
}
