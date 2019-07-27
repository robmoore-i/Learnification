package com.rrm.learnification;

class MainActivityViewInitialiser {
    private final AndroidLogger logger;
    private final LearnificationRepository learnificationRepository;
    private final SettingsRepository settingsRepository;
    private final LearnificationButton learnificationButton;
    private final LearnificationListView learnificationListView;
    private final PeriodicityPicker periodicityPicker;
    private final OnClickCommand learnificationButtonOnClickCommand;

    MainActivityViewInitialiser(AndroidLogger logger, MainActivityView mainActivityView, LearnificationRepository learnificationRepository, SettingsRepository settingsRepository) {
        this.logger = logger;
        this.learnificationRepository = learnificationRepository;
        this.settingsRepository = settingsRepository;

        this.learnificationButton = new LearnificationButton(logger, mainActivityView);
        this.learnificationListView = new LearnificationListView(logger, mainActivityView);
        this.periodicityPicker = new PeriodicityPicker(logger, mainActivityView);
        this.learnificationButtonOnClickCommand = new AddLearningItemOnClickCommand(mainActivityView, learnificationRepository, learnificationListView);
    }

    void initialiseView() {
        learnificationListView.populate(learnificationRepository);

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
