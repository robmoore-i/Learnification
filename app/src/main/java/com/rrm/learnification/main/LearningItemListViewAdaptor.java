package com.rrm.learnification.main;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.textlist.TextListViewAdaptor;

import java.util.List;

class LearningItemListViewAdaptor extends TextListViewAdaptor {
    LearningItemListViewAdaptor(AndroidLogger logger, List<String> textEntries) {
        super(textEntries, logger, "LearningItemListViewAdaptor", R.layout.learning_item_editable_text_entry);
    }
}
