package com.rrm.learnification.settings.radiogroup;

import java.util.HashMap;
import java.util.Map;

public class RadioGroupMappings<T> {
    private final Map<Integer, T> options;
    private final Map<T, Integer> viewIds;
    private final HashMap<T, OnCheckedCommand> actions = new HashMap<>();

    /**
     * @param options A map from radio button view id to T
     * @param viewIds A map from T to radio button view id
     */
    public RadioGroupMappings(Map<Integer, T> options, Map<T, Integer> viewIds) {
        this.options = options;
        this.viewIds = viewIds;
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

    @SuppressWarnings("ConstantConditions")
    public int viewIdOfOption(T value) {
        if (viewIds.containsKey(value)) return viewIds.get(value);
        throw new UnrecognisedOptionException("no radio group view id found for value '" + value.toString() + "'");
    }

    public T optionOfViewId(int viewId) {
        return options.get(viewId);
    }
}
