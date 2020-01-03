package com.rrm.learnification.textinput;

public interface OnTextChangeListener {
    void onTextChange(IdentifiedTextSource identifiedTextSource);

    void addTextSource(IdentifiedTextSource identifiedTextSource);

    OnTextChangeListener doNothing = new OnTextChangeListener() {
        @Override
        public void onTextChange(IdentifiedTextSource identifiedTextSource) {
        }

        @Override
        public void addTextSource(IdentifiedTextSource identifiedTextSource) {
        }
    };
}
