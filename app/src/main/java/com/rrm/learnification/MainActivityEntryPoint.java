package com.rrm.learnification;

import android.support.v4.app.NotificationManagerCompat;

class MainActivityEntryPoint {
    private final MainActivity activity;
    private final AndroidLogger androidLogger;
    private final LearnificationRepository learnificationRepository;
    private final AndroidLearnificationFactory androidLearnificationFactory;
    private final LearnificationTextGenerator learnificationTextGenerator;
    private final NotificationIdGenerator notificationIdGenerator;
    private final NotificationManagerCompat notificationManager;
    private final LearnificationButton learnificationButton;
    private final MainActivityView mainActivityView;
    private final LearnificationListView learnificationListView;

    MainActivityEntryPoint(MainActivity activity) {
        this.activity = activity;
        this.androidLogger = new AndroidLogger();
        final AndroidStorage androidStorage = new AndroidStorage(activity, androidLogger);
        this.learnificationRepository = PersistentLearnificationRepository.loadInstance(androidLogger, new FromFileLearnificationStorage(androidLogger, androidStorage));
        this.androidLearnificationFactory = new AndroidLearnificationFactory(new AndroidLearnificationFactoryContext(this.activity), MainActivity.CHANNEL_ID, androidLogger);
        this.learnificationTextGenerator = new LearnificationTextGenerator(new JavaRandomiser(), learnificationRepository);
        this.notificationIdGenerator = NotificationIdGenerator.getInstance();
        this.notificationManager = NotificationManagerCompat.from(activity);
        this.mainActivityView = new AndroidMainActivityView(activity);
        this.learnificationButton = new LearnificationButton(androidLogger, mainActivityView);
        this.learnificationListView = new LearnificationListView(androidLogger, mainActivityView);
    }

    void onMainActivityEntry() {
        learnificationListView.populate(learnificationRepository);

        learnificationButton.setOnClickHandler(new AddLearningItemOnClickCommand(mainActivityView, learnificationRepository, learnificationListView));

        createNotificationChannel();

        publishInitialLearnification();
    }

    private void publishInitialLearnification() {
        AndroidLearnificationPublisher androidLearnificationPublisher = new AndroidLearnificationPublisher(
                androidLearnificationFactory,
                notificationIdGenerator,
                learnificationTextGenerator,
                notificationManager
        );

        androidLearnificationPublisher.createLearnification();
    }

    private void createNotificationChannel() {
        AndroidNotificationContext androidNotificationContext = new AndroidNotificationContext(this.activity.getApplicationContext());
        NotificationChannelInitialiser notificationChannelInitialiser = new NotificationChannelInitialiser(androidNotificationContext, androidLogger);
        notificationChannelInitialiser.createNotificationChannel(MainActivity.CHANNEL_ID);
    }
}
