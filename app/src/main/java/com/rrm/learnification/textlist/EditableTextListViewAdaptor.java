package com.rrm.learnification.textlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.textinput.AndroidTextWatcher;
import com.rrm.learnification.textinput.OnTextChangeListener;

import java.util.List;

public abstract class EditableTextListViewAdaptor extends RecyclerView.Adapter<EditableTextListViewAdaptor.ViewHolder> {
    private final AndroidLogger logger;
    private final String LOG_TAG;

    private final int viewHolderId;
    private final List<String> textEntries;

    private OnTextChangeListener onEntryTextChangeListener = OnTextChangeListener.doNothing;
    private ListViewItemSaver listItemViewSaver = ListViewItemSaver.noSave;

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
        return new ViewHolder(logger, LOG_TAG, v, onEntryTextChangeListener, listItemViewSaver);
    }

    public void setEntryUpdateHandlers(OnTextChangeListener onEntryTextChangeListener, ListViewItemSaver listViewItemSaver) {
        this.onEntryTextChangeListener = onEntryTextChangeListener;
        this.listItemViewSaver = listViewItemSaver;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final String textSourceId = "focused-edit-text";

        private final AndroidLogger logger;
        private final String LOG_TAG;

        private final EditText listItemView;
        private final OnTextChangeListener onTextChangeListener;

        ViewHolder(AndroidLogger logger, String PARENT_LOG_TAG, EditText listItemView, OnTextChangeListener onTextChangeListener, ListViewItemSaver listViewItemSaver) {
            super(listItemView);
            this.logger = logger;
            this.LOG_TAG = PARENT_LOG_TAG + ".ViewHolder";
            this.listItemView = listItemView;
            this.onTextChangeListener = onTextChangeListener;

            configureListItemSaver(listViewItemSaver);
            configureOnTextChangeListener();
        }

        private void configureListItemSaver(ListViewItemSaver listViewItemSaver) {
            listItemView.setOnFocusChangeListener((v, hasFocus) -> {
                String viewText = ((EditText) v).getText().toString();
                if (hasFocus) {
                    logger.v(LOG_TAG, "focus acquired on list item with text '" + viewText + "'");
                    // Set update button to update the learning item based on viewText with the text of listItemView
                    listViewItemSaver.saveText(new AndroidTextSource(listItemView), viewText);
                } else {
                    logger.v(LOG_TAG, "focus lost on list item with text '" + viewText + "'");
                    // Reload text content from storage
                    setText(listViewItemSaver.savedText());
                    // Reset anything listening to the text of the no-longer-focused view
                    onTextChangeListener.removeTextSource(textSourceId);
                }
            });
        }

        private void configureOnTextChangeListener() {
            onTextChangeListener.addTextSource(new AndroidTextWatcher(textSourceId, listItemView));
        }

        private void setText(CharSequence text) {
            listItemView.setText(text);
        }
    }
}
