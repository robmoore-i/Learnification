package com.rrm.learnification.toolbar;

import java.util.Locale;

public interface ToolbarViewParameters {
    String toolbarTitle();

    String getName();

    class LearnificationReady implements ToolbarViewParameters {
        @Override
        public String toolbarTitle() {
            return "Learnification ready";
        }

        @Override
        public String getName() {
            return "ready";
        }
    }

    class LearnificationScheduled implements ToolbarViewParameters {
        private final int seconds;

        LearnificationScheduled(int secondsUntilExecution) {
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

        private String formatSecondsIntoPresentableTime(int s) {
            return String.format(Locale.getDefault(), "%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
        }
    }

    class LearnificationNotScheduled implements ToolbarViewParameters {
        @Override
        public String toolbarTitle() {
            return "Learnification";
        }

        @Override
        public String getName() {
            return "not scheduled";
        }
    }
}
