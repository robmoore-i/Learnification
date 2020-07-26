package com.rrm.learnification.table;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AndroidTable {
    private final Context androidContext;
    private final TableLayout tableLayout;

    public AndroidTable(Context androidContext, TableLayout tableLayout) {
        this.androidContext = androidContext;
        this.tableLayout = tableLayout;
        tableLayout.setStretchAllColumns(true);
        tableLayout.bringToFront();
    }

    public void addRow(String columnOneText, String columnTwoText) {
        TableRow row = new TableRow(androidContext);
        TextView columnOne = new TextView(androidContext);
        columnOne.setText(columnOneText);
        TextView columnTwo = new TextView(androidContext);
        columnTwo.setText(String.valueOf(columnTwoText));
        row.addView(columnOne);
        row.addView(columnTwo);
        tableLayout.addView(row);
    }
}
