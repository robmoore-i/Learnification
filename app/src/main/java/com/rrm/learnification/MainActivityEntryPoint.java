package com.rrm.learnification;

class MainActivityEntryPoint {
    private final MainActivityViewInitialiser mainActivityViewInitialiser;
    private final NotificationChannelInitialiser notificationChannelInitialiser;
    private final LearnificationPublisher learnificationPublisher;

    MainActivityEntryPoint(
            AndroidLogger logger,
            AndroidMainActivityView mainActivityView,
            AndroidNotificationFacade androidNotificationFacade,
            FileStorageAdaptor fileStorageAdaptor,
            Randomiser randomiser
    ) {

        LearnificationRepository learnificationRepository = new PersistentLearnificationRepository(logger, new FromFileLearnificationStorage(logger, fileStorageAdaptor));

        this.mainActivityViewInitialiser = new MainActivityViewInitialiser(
                logger,
                mainActivityView,
                learnificationRepository,
                new SettingsRepository(logger, fileStorageAdaptor)
        );

        this.notificationChannelInitialiser = new NotificationChannelInitialiser(
                androidNotificationFacade
        );

        this.learnificationPublisher = new LearnificationPublisher(
                logger,
                new LearnificationTextGenerator(randomiser, learnificationRepository),
                androidNotificationFacade
        );
    }

    void onMainActivityEntry() {
        mainActivityViewInitialiser.initialiseView();

        notificationChannelInitialiser.createNotificationChannel();

        learnificationPublisher.publishLearnification();
    }
}
