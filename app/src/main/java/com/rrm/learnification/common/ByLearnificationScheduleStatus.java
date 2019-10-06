package com.rrm.learnification.common;

import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

public class ByLearnificationScheduleStatus implements ToolbarViewUpdate {
    private static final String LOG_TAG = "ByLearnificationScheduleStatus";

    private final AndroidLogger logger;
    private final LearnificationScheduler learnificationScheduler;

    public ByLearnificationScheduleStatus(AndroidLogger logger, LearnificationScheduler learnificationScheduler) {
        this.logger = logger;
        this.learnificationScheduler = learnificationScheduler;
    }

    @Override
    public void update(ToolbarView view) {
        ToolbarViewParameters toolbarViewParameters;
        if (learnificationScheduler.learnificationAvailable()) {
            toolbarViewParameters = new ToolbarViewParameters.LearnificationReady();
        } else {
            toolbarViewParameters = learnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class)
                    .map(seconds -> {
                        logger.v(LOG_TAG, "next learnification will trigger in " + seconds + " seconds");
                        return (ToolbarViewParameters) new ToolbarViewParameters.LearnificationScheduled(seconds);
                    })
                    .orElse(new ToolbarViewParameters.LearnificationNotScheduled());
        }
        logger.v(LOG_TAG, "updating activity toolbar using learnification status '" + toolbarViewParameters.getName() + "'");
        view.updateToolbar(toolbarViewParameters);
    }
}
