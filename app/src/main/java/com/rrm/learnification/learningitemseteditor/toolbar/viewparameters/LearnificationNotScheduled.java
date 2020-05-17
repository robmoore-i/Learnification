package com.rrm.learnification.learningitemseteditor.toolbar.viewparameters;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

class LearnificationNotScheduled extends EquatableToolbarViewParameters {
    private final LearnificationScheduler learnificationScheduler;

    LearnificationNotScheduled(LearnificationScheduler learnificationScheduler) {
        this.learnificationScheduler = learnificationScheduler;
    }

    @Override
    public String toolbarTitle() {
        return "Learnification";
    }

    @Override
    public String getName() {
        return "not scheduled";
    }

    @Override
    public void configureFastForwardScheduleButton(ConfigurableButton fastForwardScheduleButton) {
        fastForwardScheduleButton.clearOnClickHandlers();
        fastForwardScheduleButton.enable();
        fastForwardScheduleButton.addOnClickHandler(new ScheduleLearnificationOnClickCommand(learnificationScheduler));
    }

    private static class ScheduleLearnificationOnClickCommand implements OnClickCommand {
        private final LearnificationScheduler learnificationScheduler;

        private ScheduleLearnificationOnClickCommand(LearnificationScheduler learnificationScheduler) {
            this.learnificationScheduler = learnificationScheduler;
        }

        @Override
        public void onClick() {
            learnificationScheduler.scheduleImminentJob(LearnificationPublishingService.class);
        }
    }
}
