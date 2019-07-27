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

        LearningItemRepository learningItemRepository = new PersistentLearningItemRepository(logger, new FromFileLearningItemStorage(logger, fileStorageAdaptor));
        LearningItemListView learningItemListView = new LearningItemListView(logger, mainActivityView);

        this.mainActivityViewInitialiser = new MainActivityViewInitialiser(
                logger,
                learningItemRepository,
                new SettingsRepository(logger, fileStorageAdaptor),
                new PeriodicityPicker(logger, mainActivityView),
                new AppToolbar(logger, mainActivityView),
                new AddLearningItemOnClickCommand(mainActivityView, learningItemRepository, learningItemListView),
                learningItemListView,
                new AddLearningItemButton(logger, mainActivityView)
        );

        this.notificationChannelInitialiser = new NotificationChannelInitialiser(
                androidNotificationFacade
        );

        this.learnificationPublisher = new LearnificationPublisher(
                logger,
                new LearnificationTextGenerator(randomiser, learningItemRepository),
                androidNotificationFacade
        );
    }

    void onMainActivityEntry() {
        mainActivityViewInitialiser.initialiseView();

        notificationChannelInitialiser.createNotificationChannel();

        learnificationPublisher.publishLearnification();
    }
}
