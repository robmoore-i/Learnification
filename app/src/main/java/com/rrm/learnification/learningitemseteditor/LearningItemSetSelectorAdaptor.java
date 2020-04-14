package com.rrm.learnification.learningitemseteditor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.List;

public class LearningItemSetSelectorAdaptor extends ArrayAdapter<String> {
    private static final String LOG_TAG = "LearningItemSetSelectorAdaptor";

    final String addNewSetOptionText = "Add new group";

    private final AndroidLogger logger;

    private final List<String> learningItemSetNamesReference;

    LearningItemSetSelectorAdaptor(AndroidLogger logger, Context context, List<String> learningItemSetNames) {
        super(context, R.layout.learning_item_set_selector_entry, R.id.learning_item_set_selector_text_entry, learningItemSetNames);
        this.logger = logger;
        this.learningItemSetNamesReference = learningItemSetNames;
        appendAddNewSetOptionToSpinnerList(learningItemSetNamesReference);
        logger.i(LOG_TAG, "initial learning-item-set-names are " + learningItemSetNames.toString());
    }

    @Override
    public int getCount() {
        return learningItemSetNamesReference.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        logger.v(LOG_TAG, "getting view at position " + position + " when count is " + getCount());
        return super.getView(position, convertView, parent);
    }

    void renameLearningItemSet(String target, String replacement) {
        logger.i(LOG_TAG, "replacing '" + target + "' with '" + replacement + "' in spinner list of " + learningItemSetNamesReference.toString());
        learningItemSetNamesReference.remove(target);
        if (!learningItemSetNamesReference.contains(replacement)) {
            learningItemSetNamesReference.add(replacement);
        }
        appendAddNewSetOptionToSpinnerList(learningItemSetNamesReference);
        logger.i(LOG_TAG, "spinner list is now " + learningItemSetNamesReference.toString());
        notifyDataSetChanged();
    }

    private void appendAddNewSetOptionToSpinnerList(List<String> spinnerOptions) {
        String optionText = "Add new group";
        spinnerOptions.remove(optionText);
        spinnerOptions.add(optionText);
    }

    String createNewLearningItemSet() {
        String randomNewLearningItemSetName = "new set 1";
        learningItemSetNamesReference.add(randomNewLearningItemSetName);
        return randomNewLearningItemSetName;
    }
}
