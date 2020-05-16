package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.learningitemseteditor.learningitemupdate.IdentifiedTextSource;

public interface OnTextChangeListener {
    void onTextChange(IdentifiedTextSource identifiedTextSource);

    void addTextSource(IdentifiedTextSource identifiedTextSource);

    void removeTextSource(String textSourceId);
}
