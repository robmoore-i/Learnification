package com.rrm.learnification.learningitemseteditor.learningitemupdate;

import android.widget.EditText;

class AndroidTextSource implements TextSource {
    private final EditText editText;

    AndroidTextSource(EditText editText) {
        this.editText = editText;
    }

    @Override
    public String get() {
        return editText.getText().toString();
    }

    @Override
    public boolean isEmpty() {
        if (editText == null) return true;
        return editText.getText() == null;
    }
}
