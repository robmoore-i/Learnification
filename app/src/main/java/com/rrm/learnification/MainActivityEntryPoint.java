package com.rrm.learnification;

import android.support.v4.app.NotificationManagerCompat;

class MainActivityEntryPoint {
    private final MainActivity activity;
    private final AndroidLogger logger;
    private final LearnificationRepository learnificationRepository;
    private final AndroidLearnificationFactory androidLearnificationFactory;
    private final LearnificationTextGenerator learnificationTextGenerator;
    private final NotificationIdGenerator notificationIdGenerator;
    private final AndroidNotificationManager notificationManager;
    private final LearnificationButton learnificationButton;
    private final MainActivityView mainActivityView;
    private final LearnificationListView learnificationListView;
    private final PeriodicityPicker periodicityPicker;

    MainActivityEntryPoint(MainActivity activity) {
        this.activity = activity;
        this.logger = new AndroidLogger();
        this.learnificationRepository = new PersistentLearnificationRepository(logger, new FromFileLearnificationStorage(logger, new AndroidInternalStorageAdaptor(logger, activity)));
        this.androidLearnificationFactory = new AndroidLearnificationFactory(logger, new AndroidNotificationFactory(this.activity));
        this.learnificationTextGenerator = new LearnificationTextGenerator(new JavaRandomiser(), learnificationRepository);
        this.notificationIdGenerator = NotificationIdGenerator.getInstance();
        this.notificationManager = new AndroidNotificationManager(NotificationManagerCompat.from(activity));
        this.mainActivityView = new AndroidMainActivityView(activity);
        this.learnificationButton = new LearnificationButton(logger, mainActivityView);
        this.learnificationListView = new LearnificationListView(logger, mainActivityView);
        this.periodicityPicker = new PeriodicityPicker(logger, mainActivityView);
    }

    void onMainActivityEntry() {
        learnificationListView.populate(learnificationRepository);

        learnificationButton.setOnClickHandler(new AddLearningItemOnClickCommand(mainActivityView, learnificationRepository, learnificationListView));

        periodicityPicker.setInputRangeInMinutes(5, 90);
        periodicityPicker.setOnChangeListener(new StorePeriodicityOnChangeCommand(logger));
        periodicityPicker.setChoiceFormatter();

        createNotificationChannel();

        publishInitialLearnification();
    }

    private void publishInitialLearnification() {
        AndroidLearnificationPublisher androidLearnificationPublisher = new AndroidLearnificationPublisher(
                logger,
                androidLearnificationFactory,
                notificationIdGenerator,
                learnificationTextGenerator,
                notificationManager
        );

        androidLearnificationPublisher.createLearnification();
    }

    private void createNotificationChannel() {
        AndroidNotificationContext androidNotificationContext = new AndroidNotificationContext(this.activity.getApplicationContext());
        NotificationChannelInitialiser notificationChannelInitialiser = new NotificationChannelInitialiser(logger, androidNotificationContext);
        notificationChannelInitialiser.createNotificationChannel();
    }
}
