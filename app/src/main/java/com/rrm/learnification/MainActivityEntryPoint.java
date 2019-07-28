package com.rrm.learnification;

class MainActivityEntryPoint {
    private final AndroidNotificationFacade notificationFacade;
    private final LearnificationPublisher learnificationPublisher;
    private final LearningItemRepository learningItemRepository;
    private final AppToolbar appToolbar;
    private final LearningItemTextInput learningItemTextInput;
    private final AddLearningItemButton addLearningItemButton;
    private final LearningItemList learningItemList;

    MainActivityEntryPoint(
            AndroidLogger logger,
            MainActivityView mainActivityView,
            AndroidNotificationFacade notificationFacade,
            Randomiser randomiser,
            LearningItemRepository learningItemRepository
    ) {
        this.learningItemRepository = learningItemRepository;
        this.notificationFacade = notificationFacade;

        appToolbar = new AppToolbar(logger, mainActivityView);
        learningItemTextInput = new LearningItemTextInput(mainActivityView);
        addLearningItemButton = new AddLearningItemButton(logger, mainActivityView);
        learningItemList = new LearningItemList(logger, mainActivityView);

        this.learnificationPublisher = new LearnificationPublisher(
                logger,
                new LearnificationTextGenerator(randomiser, learningItemRepository),
                notificationFacade
        );
    }

    void onMainActivityEntry() {
        initialiseView();

        createNotificationChannel();

        learnificationPublisher.publishLearnification();
    }

    private void initialiseView() {
        appToolbar.setTitle("Learnification");

        addLearningItemButton.setOnClickHandler(new AddLearningItemOnClickCommand(learningItemTextInput, learningItemRepository, learningItemList));

        learningItemList.bindTo(learningItemRepository);
        learningItemList.setOnSwipeCommand(new RemoveItemOnSwipeCommand(learningItemRepository));
    }

    private void createNotificationChannel() {
        notificationFacade.createNotificationChannel(AndroidNotificationFacade.CHANNEL_ID);
    }
}
