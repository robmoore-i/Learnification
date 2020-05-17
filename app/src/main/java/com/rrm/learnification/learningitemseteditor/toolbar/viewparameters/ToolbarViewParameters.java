package com.rrm.learnification.learningitemseteditor.toolbar.viewparameters;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.publication.LearnificationScheduler;

import java.util.Optional;

public interface ToolbarViewParameters {
    String toolbarTitle();

    String getName();

    void configureFastForwardScheduleButton(ConfigurableButton fastForwardScheduleButton);

    static ToolbarViewParameters latest(AndroidLogger logger, ToolbarViewParameters currentToolbarViewParameters, LearnificationScheduler learnificationScheduler, Class<?> learnificationPublishingServiceClass) {
        if (learnificationScheduler.learnificationAvailable()) {
            return new LearnificationReady();
        } else {
            Optional<Integer> optionalSeconds = learnificationScheduler.secondsUntilNextLearnification(learnificationPublishingServiceClass);
            if (optionalSeconds.isPresent()) {
                int secondsUntilExecution = optionalSeconds.get();
                if ("scheduled".equals(currentToolbarViewParameters.getName())) {
                    logger.i("ToolbarViewParameters", "next learnification will trigger in " + secondsUntilExecution + " seconds");
                }
                return new LearnificationScheduled(logger, learnificationScheduler, secondsUntilExecution);
            } else {
                return new LearnificationNotScheduled(learnificationScheduler);
            }
        }
    }
}
