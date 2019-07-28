package com.rrm.learnification;

class MainActivityViewInitialiser {
    private final AndroidLogger logger;
    private final LearningItemRepository learningItemRepository;
    private final SettingsRepository settingsRepository;
    private final AppToolbar appToolbar;
    private final LearningItemTextInput learningItemTextInput;
    private final AddLearningItemButton addLearningItemButton;
    private final PeriodicityPicker periodicityPicker;
    private final LearningItemListView learningItemListView;

    MainActivityViewInitialiser(
            AndroidLogger logger,
            LearningItemRepository learningItemRepository,
            SettingsRepository settingsRepository,
            AppToolbar appToolbar, LearningItemTextInput learningItemTextInput, AddLearningItemButton addLearningItemButton, PeriodicityPicker periodicityPicker,
            LearningItemListView learningItemListView
    ) {
        this.logger = logger;
        this.learningItemRepository = learningItemRepository;
        this.settingsRepository = settingsRepository;
        this.appToolbar = appToolbar;
        this.learningItemTextInput = learningItemTextInput;
        this.addLearningItemButton = addLearningItemButton;
        this.periodicityPicker = periodicityPicker;
        this.learningItemListView = learningItemListView;
    }

    void initialiseView() {
        appToolbar.setTitle("Learnification");

        addLearningItemButton.setOnClickHandler(new AddLearningItemOnClickCommand(learningItemTextInput, learningItemRepository, learningItemListView));

        periodicityPicker.setInputRangeInMinutes(5, 90);
        periodicityPicker.setOnValuePickedListener(new StorePeriodicityOnValuePickedCommand(logger, settingsRepository));
        periodicityPicker.setToValue(settingsRepository.getInitialPeriodicityPickerValue());
        periodicityPicker.setChoiceFormatter();

        learningItemListView.bindTo(learningItemRepository);
        learningItemListView.setOnSwipeCommand(new RemoveItemOnSwipeCommand(learningItemRepository));
    }
}
