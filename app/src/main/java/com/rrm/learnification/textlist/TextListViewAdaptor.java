package com.rrm.learnification.textlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rrm.learnification.logger.AndroidLogger;

import java.util.List;

public abstract class TextListViewAdaptor extends RecyclerView.Adapter<TextListViewAdaptor.TextViewHolder> {
    private final AndroidLogger logger;
    private final String LOG_TAG;

    private final int viewHolderId;
    private final List<String> textEntries;

    public TextListViewAdaptor(List<String> textEntries, AndroidLogger logger, String LOG_TAG, int viewHolderId) {
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
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.listItemView.setText(textEntries.get(position));
    }

    @Override
    public int getItemCount() {
        return textEntries.size();
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EditText v = (EditText) LayoutInflater.from(parent.getContext()).inflate(viewHolderId, parent, false);
        v.setEnabled(false);
        return new TextViewHolder(logger, LOG_TAG, v);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class TextViewHolder extends RecyclerView.ViewHolder {
        private final EditText listItemView;

        TextViewHolder(AndroidLogger logger, String PARENT_LOG_TAG, EditText listItemView) {
            super(listItemView);
            String LOG_TAG = PARENT_LOG_TAG + ".TextViewHolder";
            this.listItemView = listItemView;

            this.listItemView.setOnLongClickListener(v -> {
                logger.v(LOG_TAG, "long press detected for view with text '" + ((EditText) v).getText().toString() + "'");
                v.setEnabled(true);
                return false;
            });

            this.listItemView.setOnClickListener(v -> logger.v(LOG_TAG, "click detected for view with text '" + ((EditText) v).getText().toString() + "'"));
        }
    }
}
