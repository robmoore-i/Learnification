package com.rrm.learnification.radiogroup;

import android.util.SparseArray;

import java.util.HashMap;

public class RadioGroupChangeListener<T> {
    private final SparseArray<T> options;
    private final HashMap<T, OnCheckedCommand> actions = new HashMap<>();

    public RadioGroupChangeListener(SparseArray<T> options) {
        this.options = options;
    }

    public void setAction(T key, OnCheckedCommand onCheckedCommand) {
        actions.put(key, onCheckedCommand);
    }

    public void onChecked(int checkedId) {
        T key = options.get(checkedId);
        if (key == null) {
            throw new UnrecognisedOptionException("no radio-group option found for id '" + checkedId + "'");
        }
        OnCheckedCommand onCheckedCommand = actions.get(key);
        if (onCheckedCommand == null) {
            throw new UnboundActionException("no action found for key '" + key.toString() + "'");
        }
        onCheckedCommand.onChecked();
    }
}
