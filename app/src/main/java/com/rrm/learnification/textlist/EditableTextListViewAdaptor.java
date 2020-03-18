package com.rrm.learnification.textlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rrm.learnification.learningitemseteditor.LearningItemStash;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.textinput.OnTextChangeListener;

import java.util.List;

public abstract class EditableTextListViewAdaptor extends RecyclerView.Adapter<EditableTextListViewAdaptor.ViewHolder> {
    private final AndroidLogger logger;
    private final String LOG_TAG;

    private final int viewHolderId;
    private final List<String> textEntries;

    private OnTextChangeListener onEntryTextChangeListener = OnTextChangeListener.doNothing;
    private LearningItemDisplayStash listItemViewSaver = LearningItemDisplayStash.noSave;
    private LearningItemStash learningItemStash;

    public EditableTextListViewAdaptor(AndroidLogger logger, String LOG_TAG, List<String> textEntries, int viewHolderId) {
        this.textEntries = textEntries;
        this.logger = logger;
        this.LOG_TAG = LOG_TAG;
        this.viewHolderId = viewHolderId;
    }

    public void add(String textEntry) {
        logger.v(LOG_TAG, "adding a text entry to the list '" + textEntry + "'");
        textEntries.add(0, textEntry);
        this.notifyDataSetChanged();
    }

    void remove(int index) {
        logger.v(LOG_TAG, "removing a text entry from the list at index " + index);
        textEntries.remove(index);
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
        EditText v = (EditText) LayoutInflater.from(parent.getContext()).inflate(viewHolderId, parent, false);
        return new ViewHolder(v, learningItemStash);
    }

    public void useStash(LearningItemStash learningItemStash) {
        this.learningItemStash = learningItemStash;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final String textSourceId = "focused-edit-text";

        private final EditText listItemView;
        private final LearningItemStash learningItemStash;

        ViewHolder(EditText listItemView, LearningItemStash learningItemStash) {
            super(listItemView);
            this.listItemView = listItemView;
            this.learningItemStash = learningItemStash;
            configureListItemSaver();
        }

        private void configureListItemSaver() {
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
