package com.rrm.learnification.logdump;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.List;

class LogLineListViewAdaptor extends RecyclerView.Adapter<LogLineListViewAdaptor.ViewHolder> {
    private static final String LOG_TAG = "LogLineListViewAdaptor";
    private final AndroidLogger logger;

    private final List<String> textEntries;

    LogLineListViewAdaptor(AndroidLogger logger, List<String> textEntries) {
        this.logger = logger;
        this.textEntries = textEntries;
    }

    @Override
    public void onBindViewHolder(@NonNull LogLineListViewAdaptor.ViewHolder holder, int position) {
        holder.setText(textEntries.get(position));
    }

    @Override
    public int getItemCount() {
        return textEntries.size();
    }

    @NonNull
    @Override
    public LogLineListViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.log_list_text_entry, parent, false);
        return new LogLineListViewAdaptor.ViewHolder(v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView listItemView;

        ViewHolder(TextView listItemView) {
            super(listItemView);
            this.listItemView = listItemView;
        }

        private void setText(String text) {
            listItemView.setText(text);
        }
    }
}
