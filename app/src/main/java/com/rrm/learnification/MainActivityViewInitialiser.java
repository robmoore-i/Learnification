package com.rrm.learnification;

class MainActivityViewInitialiser {
    private final AndroidLogger logger;
    private final LearningItemRepository learningItemRepository;
    private final SettingsRepository settingsRepository;
    private final AddLearningItemButton addLearningItemButton;
    private final LearningItemListView learningItemListView;
    private final PeriodicityPicker periodicityPicker;
    private final OnClickCommand learnificationButtonOnClickCommand;
    private final AppToolbar appToolbar;

    MainActivityViewInitialiser(
            AndroidLogger logger,
            LearningItemRepository learningItemRepository,
            SettingsRepository settingsRepository,
            PeriodicityPicker periodicityPicker,
            AppToolbar appToolbar,
            OnClickCommand learnificationButtonOnClickCommand,
            LearningItemListView learningItemListView,
            AddLearningItemButton addLearningItemButton
    ) {
        this.logger = logger;
        this.learningItemRepository = learningItemRepository;
        this.settingsRepository = settingsRepository;
        this.addLearningItemButton = addLearningItemButton;
        this.learningItemListView = learningItemListView;
        this.periodicityPicker = periodicityPicker;
        this.learnificationButtonOnClickCommand = learnificationButtonOnClickCommand;
        this.appToolbar = appToolbar;
    }

    void initialiseView() {
        appToolbar.setTitle("Learnification");

        learningItemListView.setOnSwipeCommand(new RemoveItemOnSwipeCommand(learningItemRepository));
        learningItemListView.bindTo(learningItemRepository);

        addLearningItemButton.setOnClickHandler(learnificationButtonOnClickCommand);

        periodicityPicker.setInputRangeInMinutes(5, 90);
        periodicityPicker.setOnValuePickedListener(new StorePeriodicityOnValuePickedCommand(logger, settingsRepository));
        periodicityPicker.setToValue(getInitialPeriodicityPickerValue());
        periodicityPicker.setChoiceFormatter();
    }

    private int getInitialPeriodicityPickerValue() {
        int valueReadFromSettings = settingsRepository.readPeriodicitySeconds() / 60;
        if (valueReadFromSettings == 0) {
            return 5;
        } else {
            return valueReadFromSettings;
        }
    }
}
