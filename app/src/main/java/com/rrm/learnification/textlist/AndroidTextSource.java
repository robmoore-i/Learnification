package com.rrm.learnification.textlist;

import android.widget.EditText;

public class AndroidTextSource implements TextSource {
    private final EditText editText;

    public AndroidTextSource(EditText editText) {
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
