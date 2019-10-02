package com.rrm.learnification.common;

public interface IdentifiedTextSource {
    String identity();

    String latestText();

    void addTextSink(OnTextChangeListener onTextChangeListener);
}
