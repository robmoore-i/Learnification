package com.rrm.learnification.textinput;

public interface IdentifiedTextSource {
    String identity();

    String latestText();

    void addTextSink(OnTextChangeListener onTextChangeListener);
}
