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
        LearnificationListView learnificationListView = new LearnificationListView(logger, mainActivityView);

        this.mainActivityViewInitialiser = new MainActivityViewInitialiser(
                logger,
                learnificationRepository,
                new SettingsRepository(logger, fileStorageAdaptor),
                new PeriodicityPicker(logger, mainActivityView),
                new AppToolbar(logger, mainActivityView),
                new AddLearningItemOnClickCommand(mainActivityView, learnificationRepository, learnificationListView),
                learnificationListView,
                new LearnificationButton(logger, mainActivityView)
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
