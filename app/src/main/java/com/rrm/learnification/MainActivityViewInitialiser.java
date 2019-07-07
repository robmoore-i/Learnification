package com.rrm.learnification;

class MainActivityViewInitialiser {
    private final AndroidLogger logger;
    private final MainActivityView mainActivityView;
    private final LearnificationRepository learnificationRepository;
    private final SettingsRepository settingsRepository;
    private final LearnificationButton learnificationButton;
    private final LearnificationListView learnificationListView;
    private final PeriodicityPicker periodicityPicker;

    MainActivityViewInitialiser(AndroidLogger logger, MainActivityView mainActivityView, LearnificationRepository learnificationRepository, SettingsRepository settingsRepository) {
        this.logger = logger;
        this.mainActivityView = mainActivityView;
        this.learnificationRepository = learnificationRepository;
        this.settingsRepository = settingsRepository;

        this.learnificationButton = new LearnificationButton(logger, mainActivityView);
        this.learnificationListView = new LearnificationListView(logger, mainActivityView);
        this.periodicityPicker = new PeriodicityPicker(logger, mainActivityView);
    }

    void initialiseView() {
        learnificationListView.populate(learnificationRepository);

        learnificationButton.setOnClickHandler(new AddLearningItemOnClickCommand(mainActivityView, learnificationRepository, learnificationListView));

        periodicityPicker.setInputRangeInMinutes(5, 90);
        periodicityPicker.setOnValuePickedListener(new StorePeriodicityOnValuePickedCommand(logger, settingsRepository));
        periodicityPicker.setChoiceFormatter();
    }
}
