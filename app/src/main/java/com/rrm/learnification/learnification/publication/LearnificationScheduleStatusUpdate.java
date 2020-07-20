package com.rrm.learnification.learnification.publication;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.learningitemseteditor.learnificationtoolbar.ToolbarUpdateListener;
import com.rrm.learnification.learningitemseteditor.learnificationtoolbar.ToolbarViewUpdate;
import com.rrm.learnification.learningitemseteditor.learnificationtoolbar.viewparameters.EmptyToolbarViewParameters;
import com.rrm.learnification.learningitemseteditor.learnificationtoolbar.viewparameters.ToolbarViewParameters;
import com.rrm.learnification.logger.AndroidLogger;

public class LearnificationScheduleStatusUpdate implements ToolbarViewUpdate {
    private static final String LOG_TAG = "LearnificationScheduleStatusUpdate";

    private final AndroidLogger logger;
    private final LearnificationScheduler learnificationScheduler;
    private final ConfigurableButton fastForwardScheduleButton;

    private ToolbarViewParameters toolbarViewParameters = new EmptyToolbarViewParameters();

    public LearnificationScheduleStatusUpdate(AndroidLogger logger, LearnificationScheduler learnificationScheduler,
                                              ConfigurableButton fastForwardScheduleButton) {
        this.logger = logger;
        this.learnificationScheduler = learnificationScheduler;
        this.fastForwardScheduleButton = fastForwardScheduleButton;
    }

    @Override
    public void update(ToolbarUpdateListener toolbarUpdateListener) {
        ToolbarViewParameters toolbarViewParameters = ToolbarViewParameters.latest(logger, this.toolbarViewParameters, learnificationScheduler);
        logChangeInToolbarViewParameters(toolbarViewParameters);
        toolbarUpdateListener.updateToolbar(toolbarViewParameters);
        toolbarViewParameters.configureFastForwardScheduleButton(fastForwardScheduleButton);
    }

    private void logChangeInToolbarViewParameters(ToolbarViewParameters toolbarViewParameters) {
        if (!this.toolbarViewParameters.equals(toolbarViewParameters)) {
            this.toolbarViewParameters = toolbarViewParameters;
            logger.i(LOG_TAG, "updating activity toolbar using learnification status '" + toolbarViewParameters.getName() + "'");
        }
    }
}
