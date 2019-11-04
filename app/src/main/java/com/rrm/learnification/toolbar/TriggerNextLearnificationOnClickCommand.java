package com.rrm.learnification.toolbar;

import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

class TriggerNextLearnificationOnClickCommand implements OnClickCommand {
    private final LearnificationScheduler learnificationScheduler;

    TriggerNextLearnificationOnClickCommand(LearnificationScheduler learnificationScheduler) {
        this.learnificationScheduler = learnificationScheduler;
    }

    @Override
    public void onClick() {
        learnificationScheduler.triggerNext(LearnificationPublishingService.class);
    }
}
