package com.rrm.learnification.learningitemseteditor;

import android.widget.EditText;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.textinput.AndroidTextWatcher;
import com.rrm.learnification.textinput.OnTextChangeListener;
import com.rrm.learnification.textlist.AndroidTextSource;

public class LearningItemStash {
    private static final String LOG_TAG = "LearningItemListCache";
    private final OnTextChangeListener onTextChangeListener;
    private final UpdatableLearningItemTextDisplayStash updatableLearningItemDisplayCache;
    private AndroidLogger logger;

    LearningItemStash(AndroidLogger logger, OnTextChangeListener onTextChangeListener, UpdatableLearningItemTextDisplayStash updatableLearningItemDisplayCache) {
        this.logger = logger;
        this.onTextChangeListener = onTextChangeListener;
        this.updatableLearningItemDisplayCache = updatableLearningItemDisplayCache;
    }

    public void stash(EditText listItemView, String viewText, String textSourceId) {
        logger.i(LOG_TAG, "focus acquired on list item with text '" + viewText + "'");
        // Set update button to update the learning item based on viewText with the text of listItemView
        updatableLearningItemDisplayCache.saveText(new AndroidTextSource(listItemView), viewText);
        // Set the currently focused text source as the active one
        onTextChangeListener.addTextSource(new AndroidTextWatcher(textSourceId, listItemView));
    }

    public void pop(EditText listItemView, String viewText, String textSourceId) {
        logger.i(LOG_TAG, "focus lost on list item with text '" + viewText + "'");
        // Reload text content from storage
        listItemView.setText(updatableLearningItemDisplayCache.savedText());
        // Reset anything listening to the text of the no-longer-focused view
        onTextChangeListener.removeTextSource(textSourceId);
    }
}
