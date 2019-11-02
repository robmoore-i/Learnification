package com.rrm.learnification.toolbar;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.ConfigurableButton;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

import java.util.Optional;

public class ByLearnificationScheduleStatus implements ToolbarViewUpdate {
    private static final String LOG_TAG = "ByLearnificationScheduleStatus";

    private final AndroidLogger logger;
    private final LearnificationScheduler learnificationScheduler;
    private final ConfigurableButton fastForwardScheduleButton;

    private ToolbarViewParameters toolbarViewParameters = ToolbarViewParameters.empty();

    public ByLearnificationScheduleStatus(AndroidLogger logger, LearnificationScheduler learnificationScheduler, ConfigurableButton fastForwardScheduleButton) {
        this.logger = logger;
        this.learnificationScheduler = learnificationScheduler;
        this.fastForwardScheduleButton = fastForwardScheduleButton;
    }

    @Override
    public void update(ToolbarView view) {
        ToolbarViewParameters toolbarViewParameters = getToolbarViewParametersFromLearnificationScheduleStatus();
        logChangeInToolbarViewParameters(toolbarViewParameters);
        view.updateToolbar(toolbarViewParameters);
        toolbarViewParameters.configureFastForwardScheduleButton(fastForwardScheduleButton);
    }

    private void logChangeInToolbarViewParameters(ToolbarViewParameters toolbarViewParameters) {
        if (!this.toolbarViewParameters.equals(toolbarViewParameters) || "scheduled".equals(toolbarViewParameters.getName())) {
            this.toolbarViewParameters = toolbarViewParameters;
            logger.v(LOG_TAG, "updating activity toolbar using learnification status '" + toolbarViewParameters.getName() + "'");
        }
    }

    private ToolbarViewParameters getToolbarViewParametersFromLearnificationScheduleStatus() {
        if (learnificationScheduler.learnificationAvailable()) {
            return new ToolbarViewParameters.LearnificationReady();
        } else {
            Optional<Integer> optionalSeconds = learnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class);
            if (optionalSeconds.isPresent()) {
                int seconds = optionalSeconds.get();
                logger.v(LOG_TAG, "next learnification will trigger in " + seconds + " seconds");
                return new ToolbarViewParameters.LearnificationScheduled(learnificationScheduler, seconds);
            } else {
                return new ToolbarViewParameters.LearnificationNotScheduled(learnificationScheduler);
            }
        }
    }
}
