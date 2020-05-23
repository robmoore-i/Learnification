package com.rrm.learnification.learningitemseteditor.toolbar.viewparameters;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.Optional;

public interface ToolbarViewParameters {
    static ToolbarViewParameters latest(AndroidLogger logger, ToolbarViewParameters currentToolbarViewParameters,
                                        LearnificationScheduler learnificationScheduler) {
        if (learnificationScheduler.isLearnificationAvailable()) {
            return new LearnificationReady();
        } else {
            Optional<Integer> optionalSeconds = learnificationScheduler.secondsUntilNextLearnification();
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

    String toolbarTitle();

    String getName();

    void configureFastForwardScheduleButton(ConfigurableButton fastForwardScheduleButton);
}
