package com.rrm.learnification.learningitemseteditor.toolbar;

import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

class ScheduleLearnificationOnClickCommand implements OnClickCommand {
    private final LearnificationScheduler learnificationScheduler;

    ScheduleLearnificationOnClickCommand(LearnificationScheduler learnificationScheduler) {
        this.learnificationScheduler = learnificationScheduler;
    }

    @Override
    public void onClick() {
        learnificationScheduler.scheduleImminentJob(LearnificationPublishingService.class);
    }
}
