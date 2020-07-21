package com.rrm.learnification.dailyreport.publication;

public class DailyReportScheduler {
    private final TestableDailyReportScheduler testableDailyReportScheduler;
    private final Class<DailyReportPublishingService> dailyReportPublicationClass = DailyReportPublishingService.class;

    public DailyReportScheduler(TestableDailyReportScheduler testableDailyReportScheduler) {
        this.testableDailyReportScheduler = testableDailyReportScheduler;
    }

    public void scheduleJob() {
        testableDailyReportScheduler.scheduleJob(dailyReportPublicationClass);
    }
}
