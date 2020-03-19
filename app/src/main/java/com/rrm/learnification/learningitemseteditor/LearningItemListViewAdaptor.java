package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.R;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.textlist.EditableTextListViewAdaptor;

import java.util.List;
import java.util.stream.Collectors;

class LearningItemListViewAdaptor extends EditableTextListViewAdaptor {
    LearningItemListViewAdaptor(AndroidLogger logger, List<LearningItemText> textEntries) {
        super(logger, "LearningItemListViewAdaptor",
                textEntries.stream().map(LearningItemText::toString).collect(Collectors.toList()),
                R.layout.learning_item_editable_text_entry);
    }
}
