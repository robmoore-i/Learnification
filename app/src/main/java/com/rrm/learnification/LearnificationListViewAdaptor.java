package com.rrm.learnification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LearnificationListViewAdaptor extends RecyclerView.Adapter<LearnificationListViewAdaptor.TextViewHolder> {
    private final List<String> elements;

    LearnificationListViewAdaptor(List<String> elements) {
        this.elements = elements;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.learnification_text_entry, parent, false);
        return new TextViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        // - read element from your data set at this position
        // - replace the contents of the view with that element
        holder.textView.setText(elements.get(position));
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return elements.size();
    }

    void add(String textEntry) {
        elements.add(textEntry);
        this.notifyDataSetChanged();
    }

    void remove(int index) {
        elements.remove(index);
        this.notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class TextViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;

        TextViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }
};
