package com.rrm.learnification.toolbar;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

import java.util.Optional;

public class LearnificationScheduleStatusUpdate implements ToolbarViewUpdate {
    private static final String LOG_TAG = "LearnificationScheduleStatusUpdate";

    private final AndroidLogger logger;
    private final LearnificationScheduler learnificationScheduler;
    private final ConfigurableButton fastForwardScheduleButton;

    private ToolbarViewParameters toolbarViewParameters = ToolbarViewParameters.empty();

    public LearnificationScheduleStatusUpdate(AndroidLogger logger, LearnificationScheduler learnificationScheduler, ConfigurableButton fastForwardScheduleButton) {
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
        if (!this.toolbarViewParameters.equals(toolbarViewParameters)) {
            this.toolbarViewParameters = toolbarViewParameters;
            logger.i(LOG_TAG, "updating activity toolbar using learnification status '" + toolbarViewParameters.getName() + "'");
        }
    }

    private ToolbarViewParameters getToolbarViewParametersFromLearnificationScheduleStatus() {
        if (learnificationScheduler.learnificationAvailable()) {
            return new ToolbarViewParameters.LearnificationReady();
        } else {
            Optional<Integer> optionalSeconds = learnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class);
            if (optionalSeconds.isPresent()) {
                int seconds = optionalSeconds.get();
                if (!"scheduled".equals(toolbarViewParameters.getName())) {
                    logger.i(LOG_TAG, "next learnification will trigger in " + seconds + " seconds");
                }
                return new ToolbarViewParameters.LearnificationScheduled(learnificationScheduler, seconds);
            } else {
                return new ToolbarViewParameters.LearnificationNotScheduled(learnificationScheduler);
            }
        }
    }
}
