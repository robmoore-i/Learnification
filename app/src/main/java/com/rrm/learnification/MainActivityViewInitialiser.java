package com.rrm.learnification;

class MainActivityViewInitialiser {
    private final AndroidLogger logger;
    private final LearnificationRepository learnificationRepository;
    private final SettingsRepository settingsRepository;
    private final LearnificationButton learnificationButton;
    private final LearnificationListView learnificationListView;
    private final PeriodicityPicker periodicityPicker;
    private final OnClickCommand learnificationButtonOnClickCommand;
    private final AppToolbar appToolbar;

    MainActivityViewInitialiser(
            AndroidLogger logger,
            LearnificationRepository learnificationRepository,
            SettingsRepository settingsRepository,
            PeriodicityPicker periodicityPicker,
            AppToolbar appToolbar,
            OnClickCommand learnificationButtonOnClickCommand,
            LearnificationListView learnificationListView,
            LearnificationButton learnificationButton
    ) {
        this.logger = logger;
        this.learnificationRepository = learnificationRepository;
        this.settingsRepository = settingsRepository;
        this.learnificationButton = learnificationButton;
        this.learnificationListView = learnificationListView;
        this.periodicityPicker = periodicityPicker;
        this.learnificationButtonOnClickCommand = learnificationButtonOnClickCommand;
        this.appToolbar = appToolbar;
    }

    void initialiseView() {
        appToolbar.setTitle("Learnification");

        learnificationListView.setOnSwipeCommand(new RemoveItemOnSwipeCommand(logger, learnificationRepository));
        learnificationListView.bindTo(learnificationRepository);

        learnificationButton.setOnClickHandler(learnificationButtonOnClickCommand);

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
