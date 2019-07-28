package com.rrm.learnification;

class MainActivityEntryPoint {
    private final MainActivityViewInitialiser mainActivityViewInitialiser;
    private final NotificationChannelInitialiser notificationChannelInitialiser;
    private final LearnificationPublisher learnificationPublisher;

    MainActivityEntryPoint(
            AndroidLogger logger,
            MainActivityView mainActivityView,
            AndroidNotificationFacade androidNotificationFacade,
            FileStorageAdaptor fileStorageAdaptor,
            Randomiser randomiser
    ) {

        LearningItemRepository learningItemRepository = new PersistentLearningItemRepository(logger, new FromFileLearningItemStorage(logger, fileStorageAdaptor));

        this.mainActivityViewInitialiser = new MainActivityViewInitialiser(
                logger,
                learningItemRepository,
                new SettingsRepository(logger, fileStorageAdaptor),
                new AppToolbar(logger, mainActivityView),
                new LearningItemTextInput(mainActivityView),
                new AddLearningItemButton(logger, mainActivityView),
                new PeriodicityPicker(logger, mainActivityView),
                new LearningItemList(logger, mainActivityView)
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
