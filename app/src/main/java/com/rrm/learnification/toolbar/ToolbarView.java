package com.rrm.learnification.toolbar;

import android.widget.Button;

public interface ToolbarView {
    void updateToolbar(ToolbarViewParameters toolbarViewParameters);

    Button toolbarButton();
}
