package com.rrm.learnification.toolbar;

import com.rrm.learnification.publication.LearnificationScheduler;

import java.util.Locale;

public interface ToolbarViewParameters {
    String toolbarTitle();

    String getName();

    void configureFastForwardScheduleButton(FastForwardScheduleButton fastForwardScheduleButton);

    class LearnificationReady implements ToolbarViewParameters {
        @Override
        public String toolbarTitle() {
            return "Learnification ready";
        }

        @Override
        public String getName() {
            return "ready";
        }

        @Override
        public void configureFastForwardScheduleButton(FastForwardScheduleButton fastForwardScheduleButton) {
            fastForwardScheduleButton.disable();
        }
    }

    class LearnificationScheduled implements ToolbarViewParameters {
        private final LearnificationScheduler learnificationScheduler;
        private final int seconds;

        LearnificationScheduled(LearnificationScheduler learnificationScheduler, int secondsUntilExecution) {
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
        public void configureFastForwardScheduleButton(FastForwardScheduleButton fastForwardScheduleButton) {
            fastForwardScheduleButton.clearOnClickHandlers();
            fastForwardScheduleButton.enable();
            fastForwardScheduleButton.addOnClickHandler(new TriggerNextLearnificationOnClickCommand(learnificationScheduler));
        }

        private String formatSecondsIntoPresentableTime(int s) {
            return String.format(Locale.getDefault(), "%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
        }
    }

    class LearnificationNotScheduled implements ToolbarViewParameters {
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
        public void configureFastForwardScheduleButton(FastForwardScheduleButton fastForwardScheduleButton) {
            fastForwardScheduleButton.clearOnClickHandlers();
            fastForwardScheduleButton.enable();
            fastForwardScheduleButton.addOnClickHandler(new ScheduleLearnificationOnClickCommand(learnificationScheduler));
        }
    }
}
