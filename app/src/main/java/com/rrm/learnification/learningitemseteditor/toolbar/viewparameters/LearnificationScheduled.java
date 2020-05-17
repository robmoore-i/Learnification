package com.rrm.learnification.learningitemseteditor.toolbar.viewparameters;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

import java.util.Locale;

class LearnificationScheduled extends EquatableToolbarViewParameters {
    private static final String LOG_TAG = "LearnificationScheduled";

    private final LearnificationScheduler learnificationScheduler;
    private final int seconds;

    LearnificationScheduled(AndroidLogger logger, LearnificationScheduler learnificationScheduler, int secondsUntilExecution) {
        this.learnificationScheduler = learnificationScheduler;
        this.seconds = secondsUntilExecution;
    }

    @Override
    public String toolbarTitle() {
        return "Learnification in " + formatSecondsIntoPresentableTime(seconds);
    }

    @Override
    public String getName() {
        return "scheduled";
    }

    @Override
    public void configureFastForwardScheduleButton(ConfigurableButton fastForwardScheduleButton) {
        fastForwardScheduleButton.clearOnClickHandlers();
        fastForwardScheduleButton.enable();
        fastForwardScheduleButton.addOnClickHandler(new TriggerNextLearnificationOnClickCommand(learnificationScheduler));
    }

    private String formatSecondsIntoPresentableTime(int s) {
        return String.format(Locale.getDefault(), "%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
    }

    private static class TriggerNextLearnificationOnClickCommand implements OnClickCommand {
        private final LearnificationScheduler learnificationScheduler;

        private TriggerNextLearnificationOnClickCommand(LearnificationScheduler learnificationScheduler) {
            this.learnificationScheduler = learnificationScheduler;
        }

        @Override
        public void onClick() {
            learnificationScheduler.triggerNext(LearnificationPublishingService.class);
        }
    }
}
