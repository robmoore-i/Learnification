package com.rrm.learnification.learningitemseteditor;

import android.os.Bundle;

public class LearningItemSetEditorActivityBundle {
    private static final String LEARNING_ITEM_SET_NAME_KEY = "learningItemSetName_key";

    final String learningItemSetName;

    public LearningItemSetEditorActivityBundle(String learningItemSetName) {
        this.learningItemSetName = learningItemSetName;
    }

    static LearningItemSetEditorActivityBundle fromBundle(Bundle startupIntentExtras) {
        if (startupIntentExtras != null) {
            return new LearningItemSetEditorActivityBundle(startupIntentExtras.getString(LEARNING_ITEM_SET_NAME_KEY));
        } else {
            return new LearningItemSetEditorActivityBundle("default");
        }
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(LEARNING_ITEM_SET_NAME_KEY, learningItemSetName);
        return bundle;
    }
}
