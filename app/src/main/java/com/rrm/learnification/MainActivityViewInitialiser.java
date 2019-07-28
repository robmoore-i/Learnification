package com.rrm.learnification;

class MainActivityViewInitialiser {
    private final AndroidLogger logger;
    private final LearningItemRepository learningItemRepository;
    private final SettingsRepository settingsRepository;
    private final AppToolbar appToolbar;
    private final LearningItemTextInput learningItemTextInput;
    private final AddLearningItemButton addLearningItemButton;
    private final PeriodicityPicker periodicityPicker;
    private final LearningItemList learningItemList;

    MainActivityViewInitialiser(
            AndroidLogger logger,
            LearningItemRepository learningItemRepository,
            SettingsRepository settingsRepository,
            AppToolbar appToolbar, LearningItemTextInput learningItemTextInput, AddLearningItemButton addLearningItemButton, PeriodicityPicker periodicityPicker,
            LearningItemList learningItemList
    ) {
        this.logger = logger;
        this.learningItemRepository = learningItemRepository;
        this.settingsRepository = settingsRepository;
        this.appToolbar = appToolbar;
        this.learningItemTextInput = learningItemTextInput;
        this.addLearningItemButton = addLearningItemButton;
        this.periodicityPicker = periodicityPicker;
        this.learningItemList = learningItemList;
    }

    void initialiseView() {
        appToolbar.setTitle("Learnification");

        addLearningItemButton.setOnClickHandler(new AddLearningItemOnClickCommand(learningItemTextInput, learningItemRepository, learningItemList));

        periodicityPicker.setInputRangeInMinutes(5, 90);
        periodicityPicker.setOnValuePickedListener(new StorePeriodicityOnValuePickedCommand(logger, settingsRepository));
        periodicityPicker.setToValue(settingsRepository.getInitialPeriodicityPickerValue());
        periodicityPicker.setChoiceFormatter();

        learningItemList.bindTo(learningItemRepository);
        learningItemList.setOnSwipeCommand(new RemoveItemOnSwipeCommand(learningItemRepository));
    }
}
