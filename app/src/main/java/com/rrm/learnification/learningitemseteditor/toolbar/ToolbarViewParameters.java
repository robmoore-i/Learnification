package com.rrm.learnification.learningitemseteditor.toolbar;

import android.support.annotation.Nullable;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.publication.LearnificationScheduler;

import java.util.Locale;
import java.util.Objects;

public interface ToolbarViewParameters {
    String toolbarTitle();

    String getName();

    static ToolbarViewParameters empty() {
        return new ToolbarViewParameters() {
            @Override
            public String toolbarTitle() {
                return "";
            }

            @Override
            public String getName() {
                return "";
            }

            @Override
            public void configureFastForwardScheduleButton(ConfigurableButton fastForwardScheduleButton) {
            }

            @Override
            public boolean equals(@Nullable Object obj) {
                return false;
            }
        };
    }

    void configureFastForwardScheduleButton(ConfigurableButton fastForwardScheduleButton);

    abstract class EquatableToolbarViewParameters implements ToolbarViewParameters {
        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj == null) return false;
            if (!(obj instanceof EquatableToolbarViewParameters)) return false;
            EquatableToolbarViewParameters other = (EquatableToolbarViewParameters) obj;
            return this.getName().equals(other.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getName());
        }
    }

    class LearnificationReady extends EquatableToolbarViewParameters {
        @Override
        public String toolbarTitle() {
            return "Learnification ready";
        }

        @Override
        public String getName() {
            return "ready";
        }

        @Override
        public void configureFastForwardScheduleButton(ConfigurableButton fastForwardScheduleButton) {
            fastForwardScheduleButton.disable();
        }
    }

    class LearnificationScheduled extends EquatableToolbarViewParameters {
        private final LearnificationScheduler learnificationScheduler;
        private final int seconds;

        public LearnificationScheduled(LearnificationScheduler learnificationScheduler, int secondsUntilExecution) {
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
    }

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
    }
}
