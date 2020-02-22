package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.textlist.EditableTextListViewAdaptor;

import java.util.List;

class LearningItemListViewAdaptor extends EditableTextListViewAdaptor {
    LearningItemListViewAdaptor(AndroidLogger logger, List<String> textEntries) {
        super(logger, "LearningItemListViewAdaptor", textEntries, R.layout.learning_item_editable_text_entry);
    }
}
