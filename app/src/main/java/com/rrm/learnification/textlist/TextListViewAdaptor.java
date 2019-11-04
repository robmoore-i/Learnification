package com.rrm.learnification.textlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rrm.learnification.common.AndroidLogger;

import java.util.List;

public abstract class TextListViewAdaptor extends RecyclerView.Adapter<TextListViewAdaptor.TextViewHolder> {
    private final AndroidLogger logger;
    private final String LOG_TAG;

    private final int textViewId;
    private final List<String> textEntries;

    public TextListViewAdaptor(List<String> textEntries, AndroidLogger logger, String LOG_TAG, int textViewId) {
        this.textEntries = textEntries;
        this.logger = logger;
        this.LOG_TAG = LOG_TAG;
        this.textViewId = textViewId;
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
        holder.textView.setText(textEntries.get(position));
    }

    @Override
    public int getItemCount() {
        return textEntries.size();
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(textViewId, parent, false);
        return new TextViewHolder(v);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class TextViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        final TextView textView;

        TextViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }
}
