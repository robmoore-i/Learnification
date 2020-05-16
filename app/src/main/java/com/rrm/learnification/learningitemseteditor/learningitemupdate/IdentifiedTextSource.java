package com.rrm.learnification.learningitemseteditor.learningitemupdate;

import com.rrm.learnification.learningitemseteditor.OnTextChangeListener;

public interface IdentifiedTextSource {
    String identity();

    /**
     * @return The latest value of the text source. This should never be null, return an empty string instead.
     */
    String latestText();

    void addTextSink(OnTextChangeListener onTextChangeListener);
}
