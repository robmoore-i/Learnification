package com.rrm.learnification.textinput;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.rrm.learnification.logger.AndroidLogger;

public class AndroidTextWatcher implements IdentifiedTextSource, TextWatcher {
    private final AndroidLogger logger;

    private final String identity;

    private String latestText = "";
    private OnTextChangeListener onTextChangeListener;

    public AndroidTextWatcher(AndroidLogger logger, String identity, EditText editText) {
        this.logger = logger;
        this.identity = identity;
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        latestText = s.toString();
        onTextChangeListener.onTextChange(this);
    }

    private String logTag() {
        return "AndroidTextWatcher(" + identity + ")";
    }

    @Override
    public String identity() {
        return identity;
    }

    @Override
    public String latestText() {
        return latestText;
    }

    @Override
    public void addTextSink(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }
}
