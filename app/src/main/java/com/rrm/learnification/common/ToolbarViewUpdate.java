package com.rrm.learnification.common;

import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

import java.util.Locale;

public class ToolbarViewUpdate {
    private static final String LOG_TAG = "ToolbarViewUpdate";

    private final AndroidLogger logger;
    private final LearnificationScheduler learnificationScheduler;

    public ToolbarViewUpdate(AndroidLogger logger, LearnificationScheduler learnificationScheduler) {
        this.logger = logger;
        this.learnificationScheduler = learnificationScheduler;
    }

    public void update(ToolbarView view) {
        String title;
        if (learnificationScheduler.learnificationAvailable()) {
            title = "Learnification sent & ready";
        } else {
            title = learnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class)
                    .map(seconds -> {
                        logger.v(LOG_TAG, "next learnification will trigger in " + seconds + " seconds");
                        return "Learnification in " + formatSecondsIntoPresentableTime(seconds);
                    })
                    .orElse("Learnification none scheduled");
        }
        logger.v(LOG_TAG, "updating activity title to '" + title + "'");
        view.updateActivityTitle(title);

    }

    private String formatSecondsIntoPresentableTime(int s) {
        return String.format(Locale.getDefault(), "%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
    }
}
