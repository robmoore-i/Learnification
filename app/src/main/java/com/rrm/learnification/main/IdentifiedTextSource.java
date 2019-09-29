package com.rrm.learnification.main;

public interface IdentifiedTextSource {
    String identity();

    String latestText();

    void addTextSink(OnTextChangeListener onTextChangeListener);
}
