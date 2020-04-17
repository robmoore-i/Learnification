package com.rrm.learnification.learningitemseteditor;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rrm.learnification.R;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.PersistentLearningItemRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LearningItemListViewAdaptor extends RecyclerView.Adapter<LearningItemListViewAdaptor.ViewHolder> {
    private static final String LOG_TAG = "EditableTextListViewAdaptor";
    private final AndroidLogger logger;

    private final List<String> textEntries;
    private final LearningItemStash learningItemStash;
    private final PersistentLearningItemRepository learningItemRepository;

    LearningItemListViewAdaptor(AndroidLogger logger, LearningItemStash learningItemStash, PersistentLearningItemRepository learningItemRepository) {
        this.logger = logger;
        this.learningItemStash = learningItemStash;
        this.textEntries = learningItemRepository.textEntries().stream().map(LearningItemText::toString).collect(Collectors.toList());
        this.learningItemRepository = learningItemRepository;
    }

    void add(String textEntry) {
        logger.i(LOG_TAG, "adding a text entry to the list '" + textEntry + "'");
        textEntries.add(0, textEntry);
        this.notifyDataSetChanged();
    }

    void remove(int index) {
        logger.i(LOG_TAG, "removing a text entry from the list at index " + index);
        textEntries.remove(index);
        this.notifyDataSetChanged();
    }

    void replace(String targetText, String replacementText) {
        textEntries.replaceAll((textEntry) -> {
            if (textEntry.equals(targetText)) return replacementText;
            return textEntry;
        });
    }

    boolean containsTextEntries(Collection<String> textEntries) {
        logger.i(LOG_TAG, "checking if the displayed items '" + this.textEntries.toString() + "' contains all the text entries '" + textEntries.toString() + "'");
        return this.textEntries.containsAll(textEntries);
    }

    void refresh() {
        learningItemRepository.refresh();
        textEntries.clear();
        textEntries.addAll(learningItemRepository.textEntries().stream().map(LearningItemText::toString).collect(Collectors.toList()));
        this.notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setText(textEntries.get(position));
    }

    @Override
    public int getItemCount() {
        return textEntries.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EditText v = (EditText) LayoutInflater.from(parent.getContext()).inflate(R.layout.learning_item_editable_text_entry, parent, false);
        return new ViewHolder(v, learningItemStash);
    }

    String itemAtPosition(int listViewIndex) {
        return textEntries.get(listViewIndex);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final EditText listItemView;
        private final LearningItemStash learningItemStash;

        ViewHolder(EditText listItemView, LearningItemStash learningItemStash) {
            super(listItemView);
            this.listItemView = listItemView;
            this.learningItemStash = learningItemStash;
            configureListItemSaver();
        }

        private void configureListItemSaver() {
            String textSourceId = "focused-edit-text";
            listItemView.setOnFocusChangeListener((v, hasFocus) -> {
                String viewText = ((EditText) v).getText().toString();
                if (hasFocus) {
                    learningItemStash.stash(listItemView, viewText, textSourceId);
                } else {
                    learningItemStash.pop(listItemView, viewText, textSourceId);
                }
            });
        }

        private void setText(CharSequence text) {
            listItemView.setText(text);
        }
    }
}
